package com.bankmonitoring.tm_alerting.config;

import com.bankmonitoring.tm_alerting.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String token = null;

        // 1) Header Authorization: Bearer xxx
        var authHeader = req.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        // 2) Cookie siti_token
        if (token == null && req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if ("siti_token".equals(c.getName())) { token = c.getValue(); break; }
            }
        }

        if (StringUtils.hasText(token)) {
            try {
                Claims claims = jwtService.parse(token).getBody();
                String email = (String) claims.get("email");
                String role = (String) claims.get("role");
                var auth = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ignored) {
                // cho qua -> request thành anonymous, các endpoint protected sẽ 401
            }
        }

        chain.doFilter(req, res);
    }
}