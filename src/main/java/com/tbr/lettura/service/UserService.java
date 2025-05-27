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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
    

    public boolean registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        String password = user.getPassword();
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";
        if (password == null || !password.matches(passwordPattern)) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }
}