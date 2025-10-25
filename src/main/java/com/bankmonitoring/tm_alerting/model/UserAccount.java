package com.bankmonitoring.tm_alerting.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
@Document(collection = "users")
public class UserAccount {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;

    private Role role;
}
