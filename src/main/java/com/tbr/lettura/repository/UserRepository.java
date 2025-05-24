package com.tbr.lettura.repository;

import com.tbr.lettura.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // puoi aggiungere query custom qui
}