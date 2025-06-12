package com.tbr.lettura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.repository.UserRepository;

/**
 * Service che gestisce la logica relativa alle associazioni tra utenti e libri.
 * Fornisce metodi per collegare, ottenere e gestire i libri associati agli utenti.
 */
@Service
public class UserLibroService {
    @Autowired
    LibroUserRepository libroUserRepository;
    @Autowired
    LibroRepository libroRepository;
    @Autowired
    UserRepository userRepository;

}