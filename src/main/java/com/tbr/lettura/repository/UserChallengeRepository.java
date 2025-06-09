package com.tbr.lettura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.UserChallenge;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {
    List<UserChallenge> findByUserId(Integer userId);
    UserChallenge findByUserIdAndChallengeId(Integer userId, Integer challengeId);
}