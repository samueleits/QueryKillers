package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tbr.lettura.model.Challenge;

/**
 * Repository per l'entità Challenge.
 * Fornisce metodi per accedere e gestire le sfide nel database.
 * Estende JpaRepository per operazioni CRUD su Challenge.
 */
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
    
}