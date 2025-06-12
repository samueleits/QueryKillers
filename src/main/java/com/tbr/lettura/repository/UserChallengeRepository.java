package com.tbr.lettura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.UserChallenge;

/**
 * Repository per l'entità UserChallenge.
 * Fornisce metodi per accedere e gestire le associazioni tra utenti e sfide nel database.
 * Estende JpaRepository per operazioni CRUD su UserChallenge.
 * Include metodi personalizzati per trovare associazioni tramite userId e challengeId.
 */
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Integer> {
    /**
     * Restituisce la lista delle associazioni per un dato utente.
     *
     * @param userId id dell'utente
     * @return lista di UserChallenge per l'utente specificato
     */
    List<UserChallenge> findByUserId(Integer userId);

    /**
     * Restituisce l'associazione tra un utente e una sfida specifica.
     *
     * @param userId id dell'utente
     * @param challengeId id della sfida
     * @return UserChallenge corrispondente, oppure null se non esiste
     */
    UserChallenge findByUserIdAndChallengeId(Integer userId, Integer challengeId);
}