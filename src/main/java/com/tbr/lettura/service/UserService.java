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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Cifratura delle password

    /**
     * Registra un nuovo utente con i dati forniti.
     *
     * @param user Utente da registrare. Deve avere email e password non null e non vuote.
     *             La password deve essere lunga almeno 12 caratteri, contenere almeno una lettera maiuscola e un carattere speciale.
     * @return true se l'utente è stato registrato con successo, false se l'email è già in uso o la password non rispetta i criteri.
     */
    public boolean registerUser(Users user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        String password_hash = user.getPassword_hash();
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$"; // Pattern per password: almeno 12 caratteri, almeno una maiuscola e un carattere speciale
        if (password_hash == null || !password_hash.matches(passwordPattern)) { // Controllo della validità della password
            return false;
        }
        user.setPassword_hash(passwordEncoder.encode(password_hash)); // Cifra la password prima di salvarla
        userRepository.save(user);
        return true;
    }
}