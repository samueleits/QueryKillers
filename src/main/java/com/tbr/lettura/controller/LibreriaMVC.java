package com.tbr.lettura.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.repository.UserRepository;
import com.tbr.lettura.service.LibroService;
import com.tbr.lettura.service.UserLibroService;
import com.tbr.lettura.service.UserService;


/**
 * Controller MVC per la gestione della libreria.
 * Gestisce le richieste per la visualizzazione della home, la lista dei libri e l'aggiunta di nuovi libri.
 */
@Controller
public class LibreriaMVC {

    @Autowired
    private LibroService libroService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLibroService userLibroService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibroUserRepository libroUserRepository;




    /**
     * Pagina di benvenuto.
     *
     * @param model il modello della pagina
     * @return il nome della pagina da visualizzare, ovvero "index"
     */
    @GetMapping("/")
    public String home(Model model) {
        List<Libro> libriPiuLetti = libroService.getLibriPiuLetti(5); // Mostra i 5 più letti
        List<Libro> ultimeUscite = libroService.getUltimeUscite(5); // Mostra le ultime 5 uscite
        List<Libro> libri = libroRepository.findAll(); // Ottieni tutti i libri dalla repository
        model.addAttribute("libri", libri); // Aggiungi la lista dei libri al modello
        model.addAttribute("ultimeUscite", ultimeUscite);
        model.addAttribute("libriPiuLetti", libriPiuLetti);
        model.addAttribute("titolo", "Benvenuto nella libreria");
        return "index";
    }

    /**
     * Visualizza la pagina con la lista dei libri.
     *
     * @param model il modello della pagina
     * @return il nome della pagina da visualizzare, ovvero "principale-login"
     */
    @GetMapping("/Home")
    public String getLibri(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        model.addAttribute("user", user);
        List<Libro> libriPiuLetti = libroService.getLibriPiuLetti(5);
        List<Libro> ultimeUscite = libroService.getUltimeUscite(5);
        List<Libro> libri = libroRepository.findAll(); // Ottieni tutti i libri dalla repository
        model.addAttribute("libri", libri); // Aggiungi la lista dei libri al modello
        model.addAttribute("ultimeUscite", ultimeUscite);
        model.addAttribute("libriPiuLetti", libriPiuLetti);

        int libriLetti = libroUserRepository.countLibriLettiByUserId(user.getId());
        model.addAttribute("libriLetti", libriLetti);

        // Calcola livello e immagine animale (stessa logica del profilo)
        String livello;
        String animalImg;
        if (libriLetti >= 0 && libriLetti <= 4) {
            livello = "Bruco";
            animalImg = "(0-4)bruco(livello 0).png";
        } else if (libriLetti >= 5 && libriLetti <= 9) {
            livello = "Topo";
            animalImg = "(5-9)topo(livello 1).png";
        } else if (libriLetti >= 10 && libriLetti <= 14) {
            livello = "Coniglio";
            animalImg = "(10-14)coniglio(livello 2).png";
        } else if (libriLetti >= 15 && libriLetti <= 22) {
            livello = "Volpe";
            animalImg = "(15-22)volpe(livello 3).png";
        } else if (libriLetti >= 23 && libriLetti <= 31) {
            livello = "Lupo";
            animalImg = "(23-31)lupo(livello 4).png";
        } else if (libriLetti >= 32 && libriLetti <= 39) {
            livello = "Orso";
            animalImg = "(32-39)orso(livello 5).png";
        } else if (libriLetti >= 40 && libriLetti <= 47) {
            livello = "Gufo";
            animalImg = "(40-47)gufo(livello 6).png";
        } else if (libriLetti >= 48 && libriLetti <= 50) {
            livello = "Drago";
            animalImg = "(48-50)drago(livello 7).png";
        } else {
            livello = "Bruco";
            animalImg = "(0-4)bruco(livello 0).png";
        }
        model.addAttribute("livello", livello);
        model.addAttribute("animalImg", animalImg);
            return "homepage-logged";
        }

    /**
     * Aggiunge un libro alla libreria.
     *
     * @param libro il libro da aggiungere
     * @param model il modello della pagina
     * @return il nome della pagina da visualizzare, ovvero "principale-login"
     */
    @PostMapping("/Home")
    public String addLibro(@ModelAttribute Libro libro, Model model) {
        libroService.addLibro(libro);
        model.addAttribute("titolo", "I più grandi libri di sempre");
        model.addAttribute("libri", libroService.getLibri());
        return "homepage-logged";
    }

    /**
     * Visualizza la pagina con la lista di tutti i libri.
     *
     * @param model il modello della pagina
     * @return il nome della pagina da visualizzare, ovvero "libri-tutti"
     */

    @GetMapping("/libri")
    public String mostraTuttiILibri(Model model, Principal principal) {
        if (principal != null) {
        Users user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
    }
        List<Libro> libri = libroRepository.findAll(); // Ottieni tutti i libri dalla repository
        model.addAttribute("libri", libri); // Aggiungi la lista dei libri al modello
        return "libri-tutti";
    }

    /**
     * Filtra i libri in base ai parametri di ricerca.
     *
     * @param title il titolo del libro da cercare
     * @param author l'autore del libro da cercare
     * @param genre il genere del libro da cercare
     * @param model il modello della pagina
     * @return il nome della pagina da visualizzare, ovvero "libri-tutti"
     */
    @GetMapping("/libri/filter")
    public String filterLibri(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String genre,
        Principal principal,
        Model model) {

        if (principal != null) {
            Users user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }

        List<Libro> filteredLibri = libroRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
            title != null ? title : "",
            author != null ? author : "",
            genre != null ? genre : ""
        );
        model.addAttribute("libri", filteredLibri);
        return "libri-tutti";
    }


    /**
     * Permette all'utente di segnare un libro come letto o non letto.
     *
     * @param bookId l'ID del libro da aggiornare
     * @param principal l'utente autenticato
     * @return una risposta JSON con lo stato aggiornato
     */
    @PostMapping("/toggle-read")
    @ResponseBody
    public ResponseEntity<String> toggleRead(@RequestParam("bookId") int bookId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);

        libroService.toggleReadStatus(user.getId(), bookId); // Chiama il servizio per aggiornare lo stato del libro

        return ResponseEntity.ok("Stato aggiornato");
    }

    @PostMapping("/libri")
    public String addToLibrary(@RequestParam("bookId") int bookId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);

        // Recupera il libro 
        Libro libro = libroRepository.findById(bookId).orElse(null);
        if (libro == null) return "redirect:/libri";

        // Usa SOLO il service, che gestisce duplicati e salvataggio
        try {
            userLibroService.aggiungiLibroAllaLibreria(user.getId(), bookId);
        } catch (RuntimeException e) {
            // Se il libro è già presente, ignora o gestisci il messaggio
        }

        return "redirect:/libri";
    }

    
}