package com.tbr.lettura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.Users;

public interface LibroUserRepository extends JpaRepository<LibroUser, Integer> {
    Optional<LibroUser> findByUserAndBook(Users user, Libro book);
    List<LibroUser> findByUserAndIsRead(Users user, boolean isRead);
}


