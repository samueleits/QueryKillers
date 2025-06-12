package com.tbr.lettura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Classe che rappresenta un libro.
 * Un libro ha un id, un titolo, un autore, un genere, un anno di pubblicazione e una descrizione.
 * L'id viene generato automaticamente.
 */
@Entity // Indica che questa classe è un'entità JPA
@Table(name = "books") // Specifica il nome della tabella nel database
public class Libro {
    @Id // Indica che questo campo è la chiave primaria dell'entità
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica che l'ID sarà generato automaticamente dal database
    private int id;

    private String title;
    private String author;
    private String genre;
    private Integer year;
    private String description;

    /**
     * Costruttore vuoto della classe Libro.
     */
    public Libro() {}

    /**
     * Restituisce l'id del libro.
     *
     * @return id del libro
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica l'id del libro.
     *
     * @param id nuovo id del libro
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce il titolo del libro.
     *
     * @return titolo del libro
     */
    public String getTitle() {
        return title;
    }

    /**
     * Modifica il titolo del libro.
     *
     * @param title nuovo titolo del libro
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce l'autore del libro.
     *
     * @return autore del libro
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Modifica l'autore del libro.
     *
     * @param author nuovo autore del libro
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Restituisce il genere del libro.
     *
     * @return genere del libro
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Modifica il genere del libro.
     *
     * @param genre nuovo genere del libro
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Restituisce l'anno di pubblicazione del libro.
     *
     * @return anno di pubblicazione del libro
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Modifica l'anno di pubblicazione del libro.
     *
     * @param year nuovo anno di pubblicazione del libro
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Restituisce la descrizione del libro.
     *
     * @return descrizione del libro
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifica la descrizione del libro.
     *
     * @param description nuova descrizione del libro
     */
    public void setDescription(String description) {
        this.description = description;
    }
}