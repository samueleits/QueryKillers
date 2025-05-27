package com.tbr.lettura.controller;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LibreriaMVC {

    @Autowired
    private LibroService libroService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titolo", "Benvenuto nella libreria");
        return "index";
    }

    @GetMapping("/libri")
    public String getLibri(Model model) {
        model.addAttribute("titolo", "I più grandi libri di sempre");
        model.addAttribute("libri", libroService.getLibri());
        return "vista_libri";
    }

    @PostMapping("/libri")
    public String addLibro(@ModelAttribute Libro libro, Model model) {
        libroService.addLibro(libro);
        model.addAttribute("titolo", "I più grandi libri di sempre");
        model.addAttribute("libri", libroService.getLibri());
        return "vista_libri";
    }
}