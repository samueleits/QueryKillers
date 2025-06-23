package com.tbr.lettura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.UserRepository;

/**
 * Service che gestisce la logica relativa agli utenti.
 * Fornisce metodi per registrare nuovi utenti e gestire le operazioni sugli utenti.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean emailExists(String email) {
        System.out.println("Controllo se esiste l'email: " + email);
        return userRepository.existsByEmail(email);
    }

    public boolean isPasswordValid(String password) {
        System.out.println("Controllo se è valida la password: " + password);
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";
        return password != null && password.matches(passwordPattern);
    }

    public void registerUser(Users user) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        userRepository.save(user);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    
}