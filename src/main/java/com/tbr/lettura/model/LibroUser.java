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

@Entity
@Table(name = "user_books")
public class LibroUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // Riferimento all'oggetto Users

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Libro book; // Riferimento all'oggetto Libro

    private boolean isRead;
    private LocalDate read_date;

    public LibroUser() {}

    public LibroUser(Users user, Libro book, boolean isRead, LocalDate read_date) {
        this.user = user;
        this.book = book;
        this.isRead = isRead;
        this.read_date = read_date;
    }

    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
    public Libro getBook() { return book; }
    public void setBook(Libro book) { this.book = book; }
    // Per boolean, i getter sono spesso isMetodo()
    public boolean isRead() { return isRead; } // Cambiato da getIsRead() a isRead() per convenzione Java
    public void setRead(boolean isRead) { this.isRead = isRead; }
    public LocalDate getRead_date() { return read_date; }
    public void setRead_date(LocalDate read_date) { this.read_date = read_date; }
}