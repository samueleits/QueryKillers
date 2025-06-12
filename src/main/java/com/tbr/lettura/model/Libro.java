package com.tbr.lettura.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String author;
    private String genre;
    private Integer year;
    private String description;

    public Libro() {}
    

    /**
     * metodo che restituisce l'id del libro / 
    * Retrieves the id of the book.
    * 
    * @return the id of the book.
    */
    public int getId() {
         return id; 
        }
    
    
    /**
     * metodo che modifica l'id del libro / 
     * Modifies the id of the book.
     * 
     * @param id the new id of the book
     */
    public void setId(int id) {
         this.id = id; 
        }
    
    
       
    /**
     * metodo che restituisce il titolo del libro / 
    * Retrieves the title of the book.
    * 
    * @return the title of the book.
    */
    public String getTitle() { 
        return title; 
    }
   
    
    /**
     * metodo che modifica il titolo del libro / 
     * Modifies the title of the book.
     * 
     * @param title the new title of the book
     */
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    
    /**
     * metodo che restituisce l'autore del libro / 
     * Retrieves the author of the book.
     * 
     * @return the author of the book
     */
    public String getAuthor() { 
        return author; 
    }
   
    /**
     * metodo che modifica l'autore del libro / 
     * Modifies the author of the book.
     * 
     * @param author the new author of the book
     */
    public void setAuthor(String author) { 
        this.author = author; 
    }
    
    /**
     * metodo che restituisce il genere del libro /
     * Retrieves the genre of the book.
     * 
     * @return the genre of the book
     */
    public String getGenre() { 
        return genre; 
    }
    
    /**
     * metodo che modifica il genere del libro / 
     * Modifies the genre of the book.
     * 
     * @param genre the new genre of the book
     */
    public void setGenre(String genre) { 
        this.genre = genre; 
    }
    
    
    /**
     * metodo che restituisce l'anno di pubblicazione del libro / 
     * Retrieves the year of publication of the book.
     * 
     * @return the year of publication of the book
     */
    public Integer getYear() { 
        return year; 
    }
   
    /**
     * metodo che modifica l'anno di pubblicazione del libro / 
     * Modifies the year of publication of the book.
     * 
     * @param year the new year of publication of the book
     */
    public void setYear(Integer year) { 
        this.year = year; 
    }
   
    
    /**
     * metodo che restituisce la descrizione del libro /
     * Retrieves the description of the book.
     * 
     * @return the description of the book
     */
    public String getDescription() { 
        return description; 
    }
    
    /**
     * metodo che modifica la descrizione del libro / 
     * Modifies the description of the book.
     * 
     * @param description the new description of the book
     */
    public void setDescription(String description) { 
        this.description = description; 
    }
}