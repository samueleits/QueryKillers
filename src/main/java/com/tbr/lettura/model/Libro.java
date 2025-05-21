package com.tbr.lettura.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity        // dice a Spring che questa classe va collegata a una tabella nel DB
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       // colonna "id" (numero unico)

    private String titolo; // colonna "titolo"
    private String autore; // colonna "autore"
    private String descrizione; // colonna "descrizione"

    // Getter e Setter servono per leggere/scrivere questi dati
}