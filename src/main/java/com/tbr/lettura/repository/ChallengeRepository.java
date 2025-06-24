package com.tbr.lettura.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Challenge;

/**
 * Repository per l'entità Challenge.
 * Fornisce metodi per accedere e gestire le sfide nel database.
 * Estende JpaRepository per operazioni CRUD su Challenge.
 */
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
 

    /**
     * Trova una sfida per il suo nome.
     *
     * @param name nome della sfida da cercare
     * @return la sfida con il nome specificato, oppure null se non trovata
     */
    Challenge findByName(String name);

    
    
  
    
}