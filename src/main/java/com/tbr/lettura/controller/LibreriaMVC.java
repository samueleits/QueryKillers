package com.tbr.lettura.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.service.LibroService;
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
}