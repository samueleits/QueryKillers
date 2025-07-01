package com.tbr.lettura.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Classe che rappresenta una sfida.
 * Una sfida ha un ID, un nome, una data di inizio e una data di fine.
 * L'ID viene generato automaticamente.
 * Il nome identifica la sfida.
 * Le date indicano il periodo della sfida.
 */
@Entity // Indica che questa classe è un'entità JPA
@Table(name = "challenges") // Specifica il nome della tabella nel database
public class Challenge {
    @Id // Indica che questo campo è la chiave primaria dell'entità
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica che l'ID sarà generato automaticamente dal database
    private int id;

    private String name;

    private LocalDate start_date;

    private LocalDate end_date;

    /**
     * Costruttore vuoto della classe Challenge.
     */
    public Challenge() {
    }

    /**
     * Costruttore della classe Challenge.
     *
     * @param name       nome della sfida
     * @param start_date data di inizio della sfida
     * @param end_date   data di fine della sfida
     * @param id         identificativo della sfida
     */
    public Challenge(String name, LocalDate start_date, LocalDate end_date, int id) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    /**
     * Restituisce l'ID della sfida.
     *
     * @return id della sfida
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce il nome della sfida.
     *
     * @return nome della sfida
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la data di inizio della sfida.
     *
     * @return data di inizio
     */
    public LocalDate getStart_date() {
        return start_date;
    }

    /**
     * Restituisce la data di fine della sfida.
     *
     * @return data di fine
     */
    public LocalDate getEnd_date() {
        return end_date;
    }

    /**
     * Imposta la data di inizio della sfida.
     *
     * @param start_date nuova data di inizio
     */
    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    /**
     * Imposta la data di fine della sfida.
     *
     * @param end_date nuova data di fine
     */
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    /**
     * Imposta il nome della sfida.
     *
     * @param name nuovo nome della sfida
     */
    public void setName(String name) {
        this.name = name;
    }
}