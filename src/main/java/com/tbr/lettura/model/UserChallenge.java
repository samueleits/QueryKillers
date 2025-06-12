package com.tbr.lettura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Classe che rappresenta l'associazione tra un utente e una sfida.
 * Contiene l'id dell'associazione, l'utente, la sfida e il punteggio ottenuto.
 * Ogni coppia utente-sfida è unica.
 */
@Entity // Indica che questa classe è un'entità JPA
@Table(name = "user_challenges") // Specifica il nome della tabella nel database
public class UserChallenge {
    @Id // Indica che questo campo è la chiave primaria dell'entità
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica che l'ID sarà generato automaticamente dal database
    private int id;

     /**
     * Associazione molti-a-uno con la classe Users.
     * Ogni UserChallenge è collegato a un solo utente, ma un utente può avere più UserChallenge.
     * L'annotazione @ManyToOne indica la relazione molti-a-uno.
     * L'annotazione @JoinColumn specifica la colonna "user_id" nella tabella, che rappresenta la chiave esterna verso la tabella degli utenti.
     * Servono a dire a JPA/Hibernate come mappare le relazioni tra le entità Java e le tabelle del database
     * Il parametro nullable = false indica che questo campo non può essere nullo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    /**
     * Associazione molti-a-uno con la classe Challenge.
     * Ogni UserChallenge è collegato a una sola sfida, ma una sfida può essere associata a più UserChallenge.
     * L'annotazione @ManyToOne indica la relazione molti-a-uno.
     * L'annotazione @JoinColumn specifica la colonna "challenge_id" nella tabella, che rappresenta la chiave esterna verso la tabella delle sfide.
     * Servono a dire a JPA/Hibernate come mappare le relazioni tra le entità Java e le tabelle del database
     * Il parametro nullable = false indica che questo campo non può essere nullo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    private int score = 0;

    /**
     * Costruttore vuoto della classe UserChallenge.
     */
    public UserChallenge() {}

    /**
     * Costruttore della classe UserChallenge.
     *
     * @param user utente associato
     * @param challenge sfida associata
     * @param score punteggio ottenuto
     */
    public UserChallenge(Users user, Challenge challenge, int score) {
        this.user = user;
        this.challenge = challenge;
        this.score = score;
    }

    /**
     * Restituisce l'id dell'associazione.
     *
     * @return id dell'associazione
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce l'utente associato.
     *
     * @return utente associato
     */
    public Users getUser() {
        return user;
    }

    /**
     * Imposta l'utente associato.
     *
     * @param user nuovo utente associato
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * Restituisce la sfida associata.
     *
     * @return sfida associata
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Imposta la sfida associata.
     *
     * @param challenge nuova sfida associata
     */
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    /**
     * Restituisce il punteggio ottenuto.
     *
     * @return punteggio ottenuto
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Imposta il punteggio ottenuto.
     *
     * @param score nuovo punteggio
     */
    public void setScore(Integer score) {
        this.score = score;
    }
}