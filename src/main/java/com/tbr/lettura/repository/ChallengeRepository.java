package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Challenge;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    
}
