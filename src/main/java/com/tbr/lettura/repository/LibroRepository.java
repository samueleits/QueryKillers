package com.tbr.lettura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tbr.lettura.model.Libro;

/**
 * Repository per l'entità Libro.
 * Fornisce metodi per accedere e gestire i libri nel database.
 * Estende JpaRepository per operazioni CRUD su Libro.
 */
public interface LibroRepository extends JpaRepository<Libro, Integer> {
/**
    @Query("""
    SELECT 
        b
    FROM 
        Book b
        JOIN UserBook ub ON b.id = ub.book.id
    WHERE 
        ub.isRead = true
    GROUP BY 
        b.id
    ORDER BY 
        COUNT(ub.user.id) DESC;
    """)
List<Book> findMostReadBooks(Pageable pageable);
*/
     List<Libro> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
        String title, String author, String genre);
}