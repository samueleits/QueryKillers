package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.LibroUser;

/**
 * Repository per l'entità LibroUser.
 * Fornisce metodi per accedere e gestire le associazioni tra utenti e libri nel database.
 * Estende JpaRepository per operazioni CRUD su LibroUser.
 */
public interface LibroUserRepository extends JpaRepository<LibroUser, Integer> {

}