package com.fulvo.backend.services;

import com.fulvo.backend.models.User;
import com.fulvo.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Crear un nuevo usuario
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Actualizar un usuario existente
    public User updateUser(User user) {
        return userRepository.save(user);  // Usamos save también para actualizar
    }

    // Eliminar un usuario por ID
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    // Eliminar todos los usuarios
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
