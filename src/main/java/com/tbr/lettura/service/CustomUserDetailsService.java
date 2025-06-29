package com.tbr.lettura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    /**
     * Carica un utente dal database in base all'email.
     * 
     * @param email l'email dell'utente da caricare
     * @return UserDetails contenente le informazioni dell'utente
     * @throws UsernameNotFoundException se l'utente non viene trovato
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("Utente non trovato");
        return User.withUsername(user.getEmail())
                   .password(user.getPassword_hash())
                   .roles("USER")
                   .build();
    }
}