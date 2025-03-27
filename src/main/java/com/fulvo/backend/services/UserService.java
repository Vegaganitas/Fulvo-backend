package com.fulvo.backend.services;

import com.fulvo.backend.dto.user.UpdatePasswordRequest;
import com.fulvo.backend.dto.user.UserResponse;
import com.fulvo.backend.models.User;
import com.fulvo.backend.repositories.UserRepository;
import com.fulvo.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User getUser(){
        String email = jwtService.getCurrentUserEmail();
        User user = userRepository.findByUsername(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return user;
    }

    public void updatePassword (UpdatePasswordRequest request){
        User user = getUser();
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new IllegalArgumentException("Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserResponse getUserProfile() {
        User user = getUser();

        return UserResponse.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .email(user.getUsername())
                .phone(user.getPhone())
        .build();
    }
}
