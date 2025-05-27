package com.tbr.lettura.service;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> getLibri() {
        return libroRepository.findAll();
    }

    public void addLibro(Libro libro) {
        libroRepository.save(libro);
    }
}