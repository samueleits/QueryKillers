package com.tbr.lettura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    
    
    /**
     * Registers a new user with the given data.
     *
     * @param user The user to register. Must have a non-null and non-empty email and password that matches the given
     *             pattern.
     *
     *             The password must be at least 12 characters long and must contain at least one uppercase letter and one
     *             special character.
     *
     * @return true if the user was registered successfully, false if the email address is already in use or the password
     *         does not match the given pattern.
     */
    public boolean registerUser(Users user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        String password_hash = user.getPassword_hash();
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";
        if (password_hash == null || !password_hash.matches(passwordPattern)) {
            return false;
        }
        user.setPassword_hash(passwordEncoder.encode(password_hash));
        userRepository.save(user);
        return true;
    }
}