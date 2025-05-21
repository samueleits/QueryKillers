package com.tbr.lettura.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.repository.LibroRepository;

@RestController
@RequestMapping("/api/libri")    // tutte le richieste a /api/libri finiscono qui
public class LibroController {

    private final LibroRepository libroRepository;

    public LibroController(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @GetMapping      // per GET /api/libri: restituisce tutti i libri
    public List<Libro> getAll() {
        return libroRepository.findAll();
    }

    @PostMapping     // per POST /api/libri: aggiunge un libro
    public Libro addLibro(@RequestBody Libro libro) {
        return libroRepository.save(libro);
    }
}