package com.tbr.lettura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Classe che rappresenta un utente.
 * Un utente ha un id, un'email, una password e un username.
 */
@Entity // Indica che questa classe è un'entità JPA
public class Users {
    @Id // Indica che questo campo è la chiave primaria dell'entità
    private int id;
    private String email;
    private String password_hash;
    private String username;

    /**
     * Costruttore vuoto della classe Users.
     */
    public Users() {
    }

    /**
     * Restituisce l'id dell'utente.
     *
     * @return id dell'utente
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica l'id dell'utente.
     *
     * @param id nuovo id dell'utente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica l'email dell'utente.
     *
     * @param email nuova email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce la password hash dell'utente.
     *
     * @return password hash dell'utente
     */
    public String getPassword_hash() {
        return password_hash;
    }

    /**
     * Modifica la password hash dell'utente.
     *
     * @param password_hash nuova password hash dell'utente
     */
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return username dell'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Modifica lo username dell'utente.
     *
     * @param username nuovo username dell'utente
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // altri campi, getter, setter
}