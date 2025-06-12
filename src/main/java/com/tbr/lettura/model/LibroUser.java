package com.tbr.lettura.model;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;

/**
 * Classe che rappresenta l'associazione tra un utente e un libro.
 * Contiene l'id dell'associazione, l'id dell'utente, l'id del libro,
 * se il libro è stato letto e la data in cui è stato letto.
 */
@Entity // Indica che questa classe è un'entità JPA
@Table(name = "user_books") // Specifica il nome della tabella nel database
public class LibroUser {
    @Id // Indica che questo campo è la chiave primaria dell'entità
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica che l'ID sarà generato automaticamente dal database
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // Riferimento all'oggetto Users

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Libro book; // Riferimento all'oggetto Libro

    private boolean isRead;
    private LocalDate read_date;

    /**
     * Restituisce l'id dell'associazione.
     *
     * @return id dell'associazione
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica l'id dell'associazione.
     *
     * @param id nuovo id dell'associazione
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'id dell'utente.
     *
     * @return id dell'utente
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Modifica l'id dell'utente.
     *
     * @param userId nuovo id dell'utente
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Restituisce l'id del libro.
     *
     * @return id del libro
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Modifica l'id del libro.
     *
     * @param bookId nuovo id del libro
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Restituisce true se il libro è stato letto, false altrimenti.
     *
     * @return true se il libro è stato letto, false altrimenti
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Modifica lo stato di lettura del libro.
     *
     * @param isRead true se il libro è stato letto, false altrimenti
     */
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * Restituisce la data in cui il libro è stato letto.
     *
     * @return data di lettura del libro
     */
    public LocalDate getRead_date() {
        return read_date;
    }

    /**
     * Modifica la data in cui il libro è stato letto.
     *
     * @param read_date nuova data di lettura del libro
     */
    public void setRead_date(LocalDate read_date) {
        this.read_date = read_date;
    }
}
