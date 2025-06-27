package com.tbr.lettura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.Users;

/**
 * Repository per l'entità LibroUser.
 * Fornisce metodi per accedere e gestire le associazioni tra utenti e libri nel database.
 * Estende JpaRepository per operazioni CRUD su LibroUser.
 */
public interface LibroUserRepository extends JpaRepository<LibroUser, Integer> {
    Optional<LibroUser> findByUserAndBook(Users user, Libro book);
    List<LibroUser> findByUser(Users user);
    List<LibroUser> findByUserAndIsRead(Users user, boolean isRead);
    List<LibroUser> findByUserAndBook_TitleContainingIgnoreCase(Users user, String query); // Corretto
    List<LibroUser> findByUserAndBook_AuthorContainingIgnoreCase(Users user, String query); // Corretto
    List<LibroUser> findByUserAndBook_GenreContainingIgnoreCase(Users user, String query); // Corretto
    long countByUserAndIsRead(Users user, boolean isRead);
    Optional<LibroUser> findByUserIdAndBookId(int userId, int bookId);

    
// @Query("SELECT COUNT(lu) FROM libroUser lu WHERE lu.user.id = :userId AND lu.isRead = true")
// int countLibriLettiByUserId(@Param("userId") int userId);


}


