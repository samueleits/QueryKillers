package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    boolean existsByEmail(String email);
}