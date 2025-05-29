package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
}