package com.tbr.lettura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroUserRepository;
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

        @Autowired
    private LibroUserRepository libroUserRepository;

    /**
     * Controlla se un utente esiste nel database in base all'email.
     *
     * @param email l'email dell'utente da controllare
     * @return true se l'email esiste, false altrimenti
     */
    public boolean emailExists(String email) {
        System.out.println("Controllo se esiste l'email: " + email);
        return userRepository.existsByEmail(email);
    }

    /**
     * Controlla se un utente esiste nel database in base al nome utente.
     *
     * @param username il nome utente da controllare
     * @return true se il nome utente esiste, false altrimenti
     */
    public boolean isPasswordValid(String password) {
        System.out.println("Controllo se è valida la password: " + password);
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{12,}$";
        return password != null && password.matches(passwordPattern);
    }

    /**
     * Registra un nuovo utente nel database.
     *
     * @param user l'oggetto Users da registrare
     */
    public void registerUser(Users user) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        userRepository.save(user);
    }

    /**
     * Trova un utente in base all'email.
     *
     * @param email l'email dell'utente da cercare
     * @return l'oggetto Users corrispondente all'email, o null se non trovato
     */
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Trova un utente in base al nome utente.
     *
     * @param username il nome utente da cercare
     * @return l'oggetto Users corrispondente al nome utente, o null se non trovato
     */
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    
    }

    /**
     * Trova un utente in base all'ID.
     *
     * @param id l'ID dell'utente da cercare
     * @return l'oggetto Users corrispondente all'ID, o null se non trovato
     */
    public Users findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Calcola il livello dell'animale in base al numero di libri letti dall'utente.
     *
     * @param user l'utente per cui calcolare il livello
     * @return il livello dell'animale come stringa
     */
    public String calcolaLivelloAnimale(Users user) {
    long count = libroUserRepository.countByUserAndIsRead(user, true);

    if (count <= 4) return "Bruco";
    if (count <= 9) return "Topo";
    if (count <= 14) return "Coniglio";
    if (count <= 22) return "Volpe";
    if (count <= 31) return "Lupo";
    if (count <= 39) return "Orso";
    if (count <= 47) return "Gufo";
    if (count <= 50) return "Drago";
    return "Entità Mistica";
}

}