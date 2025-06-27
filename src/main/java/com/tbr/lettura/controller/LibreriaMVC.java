package com.tbr.lettura.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.UserRepository;
import com.tbr.lettura.service.LibroService;
import com.tbr.lettura.service.UserService;
import java.util.List;


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
    private LibroRepository libroRepository;

        @Autowired
    private UserRepository userRepository;


    /**
     * Pagina di benvenuto.
     *
     * @param model il modello della pagina
     * @return il nome della pagina da visualizzare, ovvero "index"
     */
    @GetMapping("/")
    public String home(Model model) {
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
        model.addAttribute("libri", libroService.getLibri());
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

    @GetMapping("/libri")
    public String mostraTuttiILibri(Model model, Principal principal) {
        Users user = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        List<Libro> libri = libroRepository.findAll();
        model.addAttribute("libri", libri);
        return "libri-tutti";
    }

    @GetMapping("/libri/filter")
public String filterLibri(
    @RequestParam(required = false) String title,
    @RequestParam(required = false) String author,
    @RequestParam(required = false) String genre,
    Model model) {

    List<Libro> filteredLibri = libroRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
        title != null ? title : "", // Passa stringa vuota se null
        author != null ? author : "",
        genre != null ? genre : ""
    );
    model.addAttribute("libri", filteredLibri);
    return "libri-tutti"; // Ricarica la stessa pagina con i libri filtrati
}


@PostMapping("/toggle-read")
@ResponseBody
public ResponseEntity<String> toggleRead(@RequestParam("bookId") int bookId, Principal principal) {
    String email = principal.getName();
    Users user = userService.findByEmail(email);

    libroService.toggleReadStatus(user.getId(), bookId); // assicurati che esista e faccia il toggle

    return ResponseEntity.ok("Stato aggiornato");
}
}