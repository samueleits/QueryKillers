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
    private String cover;

    /**
     * Costruttore vuoto della classe Libro.
     */
    public Libro() {}
    /*metodo che restituisce l'id del libro / 
     * method that returns the id of the book
     */

    /**
    * Retrieves the id of the book.
    * 
    * @return the id of the book.
    */
    public int getId() {
         return id; 
        }
    /*metodo che modifica l'id del libro / 
     * method that modifies the id of the book
     */
    
    /**
     * Modifies the id of the book.
     * 
     * @param id the new id of the book
     */
    public void setId(int id) {
         this.id = id; 
        }
    
    /*metodo che restituisce il titolo del libro / 
     * method that returns the title of the book
    */ 
       
    /**
    * Retrieves the title of the book.
    * 
    * @return the title of the book.
    */
    public String getTitle() { 
        return title; 
    }
    /*metodo che modifica il titolo del libro / 
     * method that modifies the title of the book
    */
    
    /**
     * Modifies the title of the book.
     * 
     * @param title the new title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /*metodo che restituisce l'autore del libro / 
     * method that returns the author of the book
    */
    
    /**
     * Retrieves the author of the book.
     * 
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }
    /*metodo che modifica l'autore del libro / 
     * method that modifies the author of the book
    */
    /**
     * Modifies the author of the book.
     * 
     * @param author the new author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /*metodo che restituisce il genere del libro / 
     * method that returns the genre of the book
    */
    /**
     * Retrieves the genre of the book.
     * 
     * @return the genre of the book
     */
    public String getGenre() {
        return genre;
    }
    /*metodo che modifica il genere del libro / 
     * method that modifies the genre of the book
    */
    /**
     * Modifies the genre of the book.
     * 
     * @param genre the new genre of the book
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /*metodo che restituisce l'anno di pubblicazione del libro / 
     * method that returns the year of publication of the book
    */
    
    /**
     * Retrieves the year of publication of the book.
     * 
     * @return the year of publication of the book
     */
    public Integer getYear() {
        return year;
    }
    /*metodo che modifica l'anno di pubblicazione del libro / 
     * method that modifies the year of publication of the book
    */
    /**
     * Modifies the year of publication of the book.
     * 
     * @param year the new year of publication of the book
     */
    public void setYear(Integer year) {
        this.year = year;
    }
    /*metodo che restituisce la descrizione del libro /
     * method that returns the description of the book
    */
    
    /**
     * Retrieves the description of the book.
     * 
     * @return the description of the book
     */
    public String getDescription() {
        return description;
    }
    /*metodo che modifica la descrizione del libro / 
     * method that modifies the description of the book
    */
    /**
     * Modifies the description of the book.
     * 
     * @param description the new description of the book
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*metodo che restituisce la copertina del libro / 
     * method that returns the cover of the book
    */

    /**
     * Retrieves the cover of the book.
     * 
     * @return the cover of the book
     */
    public String getCover() {
        return cover;
     }   


}