package com.fulvo.backend.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    String username;
    String password;
}
