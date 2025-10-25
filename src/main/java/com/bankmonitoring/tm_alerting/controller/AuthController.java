package com.bankmonitoring.tm_alerting.controller;

import com.bankmonitoring.tm_alerting.controller.dto.AuthDtos.*;
import com.bankmonitoring.tm_alerting.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
            var ck = new Cookie("siti_token", auth.getToken());
            ck.setPath("/");
            ck.setHttpOnly(true);
            ck.setMaxAge(60 * 60 * 12); // 12h
            res.addCookie(ck);
            // Ẩn token trong body nếu đã set cookie
            auth.setToken(null);
        }
        return ResponseEntity.ok(auth);
    }
}