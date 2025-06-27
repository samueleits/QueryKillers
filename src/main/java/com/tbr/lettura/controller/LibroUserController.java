package com.tbr.lettura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; // Usa @Controller per le view
import org.springframework.web.bind.annotation.RequestParam; // Per passare dati alla view
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.UserRepository; // Per restituire JSON o stringhe direttamente
import com.tbr.lettura.service.UserLibroService;


@Controller // Usiamo @Controller perché vogliamo restituire una view (HTML)
@RequestMapping("/") // Root URL
public class LibroUserController {

    @Autowired
    private UserLibroService userLibroService;

    @Autowired
    private UserRepository userRepository; // Per ottenere gli utenti per la view
    @Autowired
    private LibroRepository libroRepository; // Per ottenere i libri per la view

    @GetMapping
    public String index(Model model) {
        // Popola il modello con dati per la visualizzazione
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("libri", libroRepository.findAll()); // <--- ASSICURATI CHE QUESTO SIA PRESENTE E CORRETTO
       
        return "index"; // Restituisce il nome della view (index.html)
    }

    @PostMapping("/toggle-read")
    @ResponseBody // Indica che questo metodo restituirà direttamente il valore, non una view
    public String toggleReadStatus(@RequestParam int userId, @RequestParam int libroId) {
        try {
            boolean newStatus = userLibroService.toggleLibroLetto(userId, libroId);
            return "Stato di lettura aggiornato a: " + newStatus;
        } catch (RuntimeException e) {
            // In un'app reale, qui faresti un log o restituiresti un messaggio di errore più strutturato
            return "Errore: " + e.getMessage();
        }
    }

    @GetMapping("/get-read-status")
    @ResponseBody // Indica che questo metodo restituirà direttamente il valore
    public String getReadStatus(@RequestParam int userId, @RequestParam int libroId) {
        try {
            boolean status = userLibroService.getStatoLetturaLibro(userId, libroId);
            return "Stato di lettura per Utente " + userId + " e Libro " + libroId + ": " + status;
        } catch (RuntimeException e) {
            return "Errore: " + e.getMessage();
        }
    }

    @GetMapping("/libri-letti/{userId}")
    public String getLibriLettiView(@PathVariable int userId, Model model) {
        try {
            Users user = userRepository.findById(userId)
                                      .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
            List<Libro> libriLetti = userLibroService.getLibriLettiDaUtente(userId);
            model.addAttribute("user", user);
            model.addAttribute("libriLetti", libriLetti);
            return "libriLetti"; // Restituisce una nuova view (creeremo libriLetti.html)
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Potresti creare una pagina di errore generica
        }
    }

    @GetMapping("/libri-non-letti/{userId}") // Questo è l'endpoint che verrà chiamato
    public String getLibriNonLettiView(@PathVariable int userId, Model model) {
        try {
            Users user = userRepository.findById(userId)
                                    .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
            List<Libro> libriNonLetti = userLibroService.getLibriNonLettiDaUtente(userId); // Chiama il metodo del Service
            model.addAttribute("user", user);
            model.addAttribute("libriNonLetti", libriNonLetti); // Il nome del modello per la view
            return "libriNonLetti"; // Restituisce il nome del template HTML
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Gestione dell'errore (puoi avere un template errorPage.html)
        }
    }

    @GetMapping("/raccomandazioni/{userId}")
    public String getRaccomandazioniView(@PathVariable int userId, Model model) {
        try {
            Users user = userRepository.findById(userId)
                                      .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
            List<Libro> raccomandazioni = userLibroService.getRaccomandazioniPerUtente(userId);
            model.addAttribute("user", user);
            model.addAttribute("raccomandazioni", raccomandazioni);
            return "raccomandazioni"; // Restituisce una nuova view (creeremo raccomandazioni.html)
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Assicurati di avere un template errorPage.html o gestisci l'errore
        }
    }

    @GetMapping("/cerca-libreria")
    public String cercaLibreria(@RequestParam int userId, @RequestParam String criterio, @RequestParam String query, Model model) {
        try {
            Users user = userRepository.findById(userId)
                                      .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
            List<Libro> libriTrovati = userLibroService.cercaLibriNellaLibreria(userId, criterio, query);
            model.addAttribute("user", user);
            model.addAttribute("query", query);
            model.addAttribute("criterio", criterio);
            model.addAttribute("libriTrovati", libriTrovati);
            return "ricercaLibreriaResults"; // Utilizziamo il template generico dei risultati
        } catch (IllegalArgumentException e) {
            // Questo gestisce il caso di criterio di ricerca non valido
            model.addAttribute("error", "Errore nel criterio di ricerca: " + e.getMessage());
            return "errorPage";
        } catch (RuntimeException e) {
            // Gestisce altri errori, come utente non trovato
            model.addAttribute("error", e.getMessage());
            return "errorPage";
        }
    }

     // NUOVO ENDPOINT: Aggiungi libro alla libreria
    @PostMapping("/aggiungi-libro")
    @ResponseBody // Restituisce un messaggio testuale
    public String aggiungiLibro(@RequestParam int userId, @RequestParam int libroId) {
        try {
            LibroUser libroUser = userLibroService.aggiungiLibroAllaLibreria(userId, libroId);
            return "Libro '" + libroUser.getBook().getTitle() + "' aggiunto alla libreria di Utente " + libroUser.getUser().getUsername() + ".";
        } catch (RuntimeException e) {
            return "Errore nell'aggiunta del libro: " + e.getMessage();
        }
    }

    // NUOVO ENDPOINT: Rimuovi libro dalla libreria
    @PostMapping("/rimuovi-libro")
    @ResponseBody // Restituisce un messaggio testuale
    public String rimuoviLibro(@RequestParam int userId, @RequestParam int libroId) {
        try {
            userLibroService.rimuoviLibroDallaLibreria(userId, libroId);
            return "Libro rimosso con successo dalla libreria dell'utente.";
        } catch (RuntimeException e) {
            return "Errore nella rimozione del libro: " + e.getMessage();
        }
    }

    @GetMapping("/autocomplete")
    public String showAutocompletePage(Model model) {
        // Recupera tutti i libri dal database e li aggiunge al modello
        List<Libro> allLibri = libroRepository.findAll();
        model.addAttribute("libri", allLibri);
        return "autocomplete"; // Restituisce il nome del template Thymeleaf (autocomplete.html)
    }
}