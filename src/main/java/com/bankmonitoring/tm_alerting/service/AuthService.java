package com.bankmonitoring.tm_alerting.service;

import com.bankmonitoring.tm_alerting.controller.dto.AuthDtos.*;
import com.bankmonitoring.tm_alerting.model.Role;
import com.bankmonitoring.tm_alerting.model.UserAccount;
import com.bankmonitoring.tm_alerting.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAccountRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        var role = Role.valueOf(req.getRole() == null ? "USER" : req.getRole().toUpperCase());
        var user = UserAccount.builder()
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build();
        repo.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        var user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        var token = jwtService.generate(user.getEmail(), user.getRole().name(), 1000L * 60 * 60 * 12); // 12h
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}