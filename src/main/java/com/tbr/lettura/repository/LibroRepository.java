package com.tbr.lettura.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tbr.lettura.model.Libro;

/**
 * Repository per l'entità Libro.
 * Fornisce metodi per accedere e gestire i libri nel database.
 * Estende JpaRepository per operazioni CRUD su Libro.
 */
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    @Query("""
    SELECT 
        l
    FROM 
        Libro l
        JOIN LibroUser lu ON l.id = lu.book.id
    WHERE 
        lu.isRead = true
    GROUP BY 
        l.id
    ORDER BY 
        COUNT(lu.user.id) DESC
    """)
    List<Libro> findMostReadBooks(Pageable pageable);

    List<Libro> findAllByOrderByIdDesc(Pageable pageable);

    List<Libro> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
        String title, String author, String genre);
}