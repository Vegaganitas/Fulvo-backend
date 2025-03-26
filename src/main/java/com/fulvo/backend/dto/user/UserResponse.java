package com.fulvo.backend.dto.user;

import com.fulvo.backend.models.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
}
