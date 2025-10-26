package com.bankmonitoring.tm_alerting.controller;

import com.bankmonitoring.tm_alerting.controller.dto.AuthDtos.*;
import com.bankmonitoring.tm_alerting.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req, HttpServletResponse res) {
        var auth = authService.login(req);
        if (req.isUseCookie()) {
            ResponseCookie cookie = ResponseCookie.from("siti_token", auth.getToken())
                    .path("/")
                    .httpOnly(true)
                    .maxAge(60 * 60 * 12) // 12h
                    .sameSite("None") // Quan trọng cho cross-domain
                    .secure(true)     // Bắt buộc khi SameSite=None
                    .build();
            res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            // Ẩn token trong body nếu đã set cookie
            auth.setToken(null);
        }
        return ResponseEntity.ok(auth);
    }
}