package com.tbr.lettura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tbr.lettura.model.Libro;

/**
 * Repository per l'entità Libro.
 * Fornisce metodi per accedere e gestire i libri nel database.
 * Estende JpaRepository per operazioni CRUD su Libro.
 */
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
}