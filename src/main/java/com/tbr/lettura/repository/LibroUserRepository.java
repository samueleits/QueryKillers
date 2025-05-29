package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.LibroUser;

public interface LibroUserRepository extends JpaRepository<LibroUser, Integer> {

}
