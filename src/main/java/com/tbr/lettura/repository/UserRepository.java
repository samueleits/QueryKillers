package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Users;

/**
 * Repository per l'entità Users.
 * Fornisce metodi per accedere e gestire gli utenti nel database.
 * Estende JpaRepository per operazioni CRUD su Users.
 * Include un metodo per verificare l'esistenza di un utente tramite email.
 */
public interface UserRepository extends JpaRepository<Users, Integer> {
    /**
     * Verifica se esiste un utente con la specifica email.
     *
     * @param email email da verificare
     * @return true se esiste un utente con quell'email, false altrimenti
     */
    boolean existsByEmail(String email);
}