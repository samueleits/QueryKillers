package com.tbr.lettura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Challenge;
import com.tbr.lettura.model.UserChallenge;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.ChallengeRepository;
import com.tbr.lettura.repository.UserChallengeRepository;

@Service
public class ChallengeService {
    @Autowired
    private UserChallengeRepository userChallengeRepo;

    @Autowired
    private ChallengeRepository challengeRepo;

    public List<UserChallenge> getUserChallenges(int userId) {
        return userChallengeRepo.findByUserId(userId);
    }

    public void addChallengeToUser(int userId, int challengeId) {
        if (userChallengeRepo.findByUserIdAndChallengeId(userId, challengeId) == null) {
            UserChallenge uc = new UserChallenge();
            Users user = new Users();
            user.setId(userId);
            uc.setUser(user);

            Challenge challenge = challengeRepo.findById(challengeId).orElse(null);
            if (challenge == null) {
                // gestisci errore: challenge non trovata
                return;
            }
            uc.setChallenge(challenge);

            userChallengeRepo.save(uc);
        }
    }

    public void incrementScore(int userId, int challengeId, int increment) {
        UserChallenge uc = userChallengeRepo.findByUserIdAndChallengeId(userId, challengeId);
        if (uc != null) {
            uc.setScore(uc.getScore() + increment);
            userChallengeRepo.save(uc);
        }
    }
}