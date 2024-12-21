package com.fulvo.backend.controllers;

import com.fulvo.backend.models.User;
import com.fulvo.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id).orElse(null);
    }

    // Crear un nuevo usuario
    @Transactional
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Eliminar un usuario por ID
    @Transactional
    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable int id) {
        User user = userService.getUserById(id).orElse(null);
        if (user != null) {
            userService.deleteUser(id);
        }
        return user;
    }

    // Eliminar todos los usuarios
    @Transactional
    @DeleteMapping("/all")
    public ResponseEntity<List<User>> deleteAllUsers() {
        List<User> users = userService.getAllUsers();
        userService.deleteAllUsers();
        return ResponseEntity.ok(users);
    }

}