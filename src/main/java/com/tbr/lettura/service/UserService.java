package com.tbr.lettura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.User;
import com.tbr.lettura.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean registerUser(User user) {
        // Check if the email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return false; // Email already in use
        }

        String password = user.getPassword();
        // Regex: almeno 12 caratteri, almeno una maiuscola, almeno un carattere speciale
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";
        if (password == null || !password.matches(passwordPattern)) {
            return false; // Password is invalid
        }

        user.setPassword(passwordEncoder.encode(password)); // Encrypt the password
        userRepository.save(user); // Save the user to the database
        return true; // Registration successful
    }
}