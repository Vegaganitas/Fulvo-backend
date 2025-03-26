package com.fulvo.backend.controller;

import com.fulvo.backend.dto.user.UpdatePasswordRequest;
import com.fulvo.backend.dto.user.UserResponse;
import com.fulvo.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "profile")
    public ResponseEntity<UserResponse> profile (){
        UserResponse userResponse = userService.getUserProfile();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request){
        userService.updatePassword(request);
        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }



}
