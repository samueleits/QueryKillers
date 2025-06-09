package com.tbr.lettura.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_books")
public class LibroUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int bookId;
    private boolean isRead;
    private LocalDate read_date;

    /*metodo che restituisce l'id /
     * method that returns the id
     */
    
    /**
     * Retrieves the id of the UserBook object.
     * 
     * @return the id of the UserBook object
     */
    public int getId() {
        return id;
    }

    /*metodo che modifica l'id / 
     * method that modifies the id
    */
    
    /**
     * Modifies the id of the UserBook object.
     * 
     * @param id the new id of the UserBook object
     */
    public void setId(int id) {
        this.id = id;
    }



   /*metodo che restituisce l'id dell'utente /
    * method that returns the id of the user
    */
    
    /**
     * Retrieves the id of the user associated with the UserBook object.
     * 
     * @return the id of the user associated with the UserBook object
     */
    public int getUserId() {
        return userId;
    }
    
    /*metodo che modifica l'id dell'utente / 
     * method that modifies the id of the user
    */
    
    /**
     * Modifies the id of the user associated with the UserBook object.
     * 
     * @param userId the new id of the user associated with the UserBook object
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /*metodo che restituisce l'id del libro / 
     * method that returns the id of the book
    */
    
    /**
     * Retrieves the id of the book associated with the UserBook object.
     * 
     * @return the id of the book associated with the UserBook object
    */
    public int getBookId() {
        return bookId;
    }
   





    /*metodo che restituisce se il libro e' stato letto / 
     * method that returns if the book has been read
    */
    
    /**
     * Returns if the book has been read.
     * 
     * @return true if the book has been read, false otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /*metodo che modifica se il libro e' stato letto / 
     * method that modifies if the book has been read
    */
    
    /**
     * Modifies if the book has been read.
     * 
     * @param isRead true if the book has been read, false otherwise
     */
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    /*metodo che restituisce la data in cui il libro e' stato letto / 
     * method that returns the date when the book was read
    */
    
    /**
    * Retrieves the date when the book was read.
    * 
    * @return the date when the book was read
    */

    public LocalDate getRead_date() {
        return read_date;
    }

    /*metodo che modifica la data in cui il libro e' stato letto / 
     * method that modifies the date when the book was read
    */
    
    /**
     * Modifies the date when the book was read.
     * 
     * @param readDate the new date when the book was read
     */
    public void setRead_date(LocalDate read_date) {
        this.read_date = read_date;
    }

}
