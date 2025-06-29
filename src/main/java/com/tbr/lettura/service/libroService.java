package com.tbr.lettura.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.repository.UserRepository;

/**
 * Service che gestisce la logica relativa ai libri.
 * Fornisce metodi per ottenere, aggiungere e cancellare libri dal repository.
 */
@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
private LibroUserRepository libroUserRepository;

@Autowired
private UserRepository userRepository;

    /**
     * Restituisce la lista di tutti i libri presenti nel repository.
     *
     * @return lista di tutti i libri
     */
    public List<Libro> getLibri() {
        return libroRepository.findAll();
    }

    /**
     * Restituisce un libro in base all'id.
     *
     * @param id id del libro da cercare
     * @return il libro con l'id specificato, oppure null se non trovato
     */
    public Libro getLibroById(int id) {
        for (Libro l : libroRepository.findAll()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    /**
     * Restituisce la lista dei libri scritti da un autore specifico.
     *
     * @param author nome dell'autore
     * @return lista di libri scritti dall'autore
     */
    public List<Libro> getLibroByAuthor(String author) {
        List<Libro> libriByAuthor = new ArrayList<>();
        for (Libro l : libroRepository.findAll()) {
            if (l.getAuthor().toLowerCase().equals(author.toLowerCase())) {
                libriByAuthor.add(l);
            }
        }
        return libriByAuthor;
    }

    /**
     * Restituisce la lista dei libri con un titolo specifico.
     *
     * @param title titolo del libro
     * @return lista di libri con il titolo specificato
     */
    public List<Libro> getLibroByTitle(String title) {
        List<Libro> libriByTitle = new ArrayList<>(); // Inizializza una lista per i libri trovati
        for (Libro l : libroRepository.findAll()) { // Itera su tutti i libri nel repository
            if (l.getTitle().toLowerCase().equals(title.toLowerCase())) { // Confronta il titolo del libro in modo case-insensitive
                libriByTitle.add(l);
            }
        }
        return libriByTitle;
    }

    /**
     * Restituisce la lista dei libri di un determinato genere.
     *
     * @param genre genere del libro
     * @return lista di libri del genere specificato
     */
    public List<Libro> getLibroByGenre(String genre) {
        List<Libro> libriByGenre = new ArrayList<>(); 
        for (Libro l : libroRepository.findAll()) { 
            if (l.getGenre().toLowerCase().equals(genre.toLowerCase())) { 
                libriByGenre.add(l);
            }
        }
        return libriByGenre;
    }

    /**
     * Restituisce la lista dei libri pubblicati in un determinato anno.
     *
     * @param year anno di pubblicazione
     * @return lista di libri pubblicati nell'anno specificato
     */
    public List<Libro> getLibroByYear(int year) {
        List<Libro> libriByYear = new ArrayList<>();
        for (Libro l : libroRepository.findAll()) {
            if (l.getYear() == year) {
                libriByYear.add(l);
            }
        }
        return libriByYear;
    }

    /**
     * Restituisce la lista dei libri pubblicati tra due anni specificati.
     *
     * @param firstYear anno di inizio intervallo
     * @param secondYear anno di fine intervallo
     * @return lista di libri pubblicati tra i due anni
     */
    public List<Libro> getLibroByBetweenYear(int firstYear, int secondYear) {
        List<Libro> libriByBetweenYear = new ArrayList<>();
        for (Libro l : libroRepository.findAll()) {
            if (l.getYear() >= firstYear && l.getYear() <= secondYear) {
                libriByBetweenYear.add(l);
            }
        }
        return libriByBetweenYear;
    }

    /**
     * Aggiunge un libro al repository.
     *
     * @param libro libro da aggiungere
     */
    public void addLibro(Libro libro) {
        libroRepository.save(libro);
    }

    /**
     * Cancella un libro dal repository tramite id.
     *
     * @param id id del libro da cancellare
     */
    public void deleteLibro(int id) {
        libroRepository.deleteById(id);
    }
    /**
     * Aggiorna un libro esistente nel repository.
     *
     * @param libro libro da aggiornare
     */

    public void toggleReadStatus(int userId, int bookId) {
        LibroUser libroUser = libroUserRepository.findByUserIdAndBookId(userId, bookId).orElse(null);

        if (libroUser == null) {
            libroUser = new LibroUser();
            libroUser.setUser(userRepository.findById(userId).orElseThrow());
            libroUser.setBook(libroRepository.findById(bookId).orElseThrow());
            libroUser.setRead(true);
        } else {
            libroUser.setRead(!libroUser.isRead());
        }

        libroUserRepository.save(libroUser);
    }
    /**
     * Restituisce i libri più letti, limitati al numero massimo specificato.
     *
     * @param max numero massimo di libri da restituire
     * @return lista dei libri più letti
     */
    public List<Libro> getLibriPiuLetti(int max) {
        Pageable pageable = PageRequest.of(0, max);
        return libroRepository.findMostReadBooks(pageable);
    }

    /**
     * Restituisce le ultime uscite, limitate al numero massimo specificato.
     *
     * @param max numero massimo di libri da restituire
     * @return lista delle ultime uscite
     */
    public List<Libro> getUltimeUscite(int max) {
        Pageable pageable = PageRequest.of(0, max);
        return libroRepository.findAllByOrderByIdDesc(pageable);
    }


}