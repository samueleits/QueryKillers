package com.tbr.lettura.service;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private UserService userService;

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
        UserChallenge uc = new UserChallenge();
        Users user = userService.findById(userId); 
        Optional<Challenge> challengeOpt = challengeRepo.findById(challengeId);
        if (user == null || challengeOpt.isEmpty()) return;
        uc.setUser(user);
        uc.setChallenge(challengeOpt.get());
        uc.setScore(0); // opzionale
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

    public List<Challenge> getAllChallenges() {
        return challengeRepo.findAll();
    }

    public Optional<Challenge> getChallengeById(int id) {
        return challengeRepo.findById(id);
    }

    public Challenge saveChallenge(Challenge challenge) {
        return challengeRepo.save(challenge);
    }

    public List<Challenge> getChallengesForUser(int userId) {
    List<UserChallenge> userChallenges = userChallengeRepo.findByUserId(userId);
    return userChallenges.stream()
        .map(UserChallenge::getChallenge)
        .toList();
}
}