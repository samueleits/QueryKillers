package com.tbr.lettura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Challenge;
import com.tbr.lettura.model.UserChallenge;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.ChallengeRepository;
import com.tbr.lettura.repository.UserChallengeRepository;

/**
 * Service che gestisce la logica relativa alle sfide e alle associazioni utente-sfida.
 * Fornisce metodi per ottenere le sfide di un utente, aggiungere una sfida a un utente
 * e incrementare il punteggio di una sfida per un utente.
 */
@Service 
public class ChallengeService {
    @Autowired
    private UserChallengeRepository userChallengeRepo;

    @Autowired
    private ChallengeRepository challengeRepo;

    /**
     * Restituisce la lista delle sfide associate a un utente.
     *
     * @param userId id dell'utente
     * @return lista di UserChallenge per l'utente specificato
     */
    public List<UserChallenge> getUserChallenges(int userId) {
        return userChallengeRepo.findByUserId(userId);
    }

    /**
     * Aggiunge una sfida a un utente, se non già presente.
     *
     * @param userId id dell'utente
     * @param challengeId id della sfida
     */
    public void addChallengeToUser(int userId, int challengeId) {
        if (userChallengeRepo.findByUserIdAndChallengeId(userId, challengeId) == null) {
            UserChallenge uc = new UserChallenge(); // Crea una nuova associazione utente-sfida
            Users user = new Users(); // Crea un nuovo oggetto Users per l'associazione
            user.setId(userId); // Imposta l'id dell'utente
            uc.setUser(user); // Associa l'utente all'oggetto UserChallenge

            Challenge challenge = challengeRepo.findById(challengeId).orElse(null); // Recupera la sfida dal repository
            // Se la sfida non esiste, gestire l'errore (ad esempio, lanciare un'eccezione o loggare un messaggio)
            if (challenge == null) {
                // devo capire come gestire l'errore
                return;
            }
            uc.setChallenge(challenge);

            userChallengeRepo.save(uc);
        }
    }

    /**
     * Incrementa il punteggio di una sfida per un utente.
     *
     * @param userId id dell'utente
     * @param challengeId id della sfida
     * @param increment valore da aggiungere al punteggio
     */
    public void incrementScore(int userId, int challengeId, int increment) {
        UserChallenge uc = userChallengeRepo.findByUserIdAndChallengeId(userId, challengeId);
        if (uc != null) {
            uc.setScore(uc.getScore() + increment);
            userChallengeRepo.save(uc);
        }
    }
}