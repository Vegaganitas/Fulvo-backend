package com.fulvo.backend.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    String username;
    String password;
    String firstName;
    String lastName;
    Integer phone;
}
