package com.tbr.lettura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private int id;
    private String email;
    private String password;
    private String username;


    public User() {
    }
    /*metodo che restituisce l'id dell'utente /
     * method that returns the id of the user
     */
    
    /**
    * Retrieves the id of the user.
    *
    * @return the id of the user.
    */
    public int getId() {
        return id;
    }   
    /*metodo che modifica l'id dell'utente / 
     * method that modifies the id of the user
    */
    
    /**
    * Modifies the id of the user.
    *
    * @param id the new id of the user.
    */
    public void setId(int id) {
        this.id = id;
    }

    /*metodo che restituisce l'email dell'utente /
     * method that returns the email of the user
    */
    
    /**
    * Retrieves the email of the user.
    *
    * @return the email of the user.
    */
    public String getEmail() {
        return email;
    }
    /*metodo che modifica l'email dell'utente / 
     * method that modifies the email of the user
    */

    /**
    * Modifies the email of the user.
    *
    * @param email the new email of the user.
    */
    public void setEmail(String email) {
        this.email = email;
    }
    /*metodo che restituisce la password dell'utente /
     * method that returns the password of the user
    */
    
    /**
    * Retrieves the password of the user.
    *
    * @return the password of the user.
    */
    public String getPassword() {
        return password;
    }
    /*metodo che modifica la password dell'utente / 
     * method that modifies the password of the user
    */
    
    /**
    * Modifies the password of the user.
    *
    * @param password the new password of the user.
    */
    public void setPassword(String password) {
        this.password = password;
    }
    /*metodo che restituisce il username dell'utente /
     * method that returns the username of the user
    */
    
    /**
    * Retrieves the username of the user.
    *
    * @return the username of the user.
    */
    public String getUsername() {
        return username;
    }
    /*metodo che modifica il username dell'utente / 
     * method that modifies the username of the user
    */
    
    /**
    * Modifies the username of the user.
    *
    * @param username the new username of the user.
    */
    public void setUsername(String username) {
        this.username = username;
    }


    // altri campi, getter, setter
}