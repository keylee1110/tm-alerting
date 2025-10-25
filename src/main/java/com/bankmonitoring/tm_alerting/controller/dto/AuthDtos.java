package com.bankmonitoring.tm_alerting.controller.dto;

import lombok.*;

public class AuthDtos {
    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class RegisterRequest {
        private String email;
        private String password;
        private String role;  // "USER" | "ADMIN"
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
        private boolean useCookie; // nếu true sẽ set cookie siti_token
    }

    @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
    public static class AuthResponse {
        private String token;   // null nếu set bằng cookie
        private String email;
        private String role;
    }
}
