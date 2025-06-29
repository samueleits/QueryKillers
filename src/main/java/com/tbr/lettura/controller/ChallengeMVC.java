package com.tbr.lettura.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tbr.lettura.model.Challenge;
import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.service.ChallengeService;
import com.tbr.lettura.service.UserService;

@Controller
public class ChallengeMVC {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private LibroUserRepository libroUserRepository;


    /**
     * Mostra la pagina delle challenge.
     * 
     * @param model il modello della pagina
     * @param search parametro di ricerca per filtrare le challenge
     * @param principal l'utente autenticato
     * @return il nome della pagina da visualizzare, ovvero "challenge"
     */
    @GetMapping("/Challenge")
    public String getChallenge(Model model, @RequestParam(required = false) String search, Principal principal) { 
        List<Challenge> challenges; // Inizializza la lista delle challenge
        // Se il parametro di ricerca è presente, filtra le challenge per nome
        if (search != null && !search.isEmpty()) {
            challenges = challengeService.getAllChallenges().stream()
                .filter(c -> c.getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        // Altrimenti, prendi tutte le challenge
        } else {
            challenges = challengeService.getAllChallenges();
        }
        model.addAttribute("challenges", challenges); // Aggiungi la lista delle challenge al modello
        model.addAttribute("challenge", new Challenge()); // Aggiungi un nuovo oggetto Challenge al modello per il form di creazione

        // Aggiungi la lista delle challenge a cui l'utente partecipa
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        List<Integer> joinedChallengeIds = challengeService.getUserChallenges(user.getId()) // Ottieni le sfide dell'utente
            .stream().map(uc -> uc.getChallenge().getId()).toList(); // Estrai gli ID delle sfide
        model.addAttribute("joinedChallengeIds", joinedChallengeIds); // Aggiungi gli ID delle sfide al modello
        List<Libro> libri = libroRepository.findAll(); // Ottieni tutti i libri dalla repository
        model.addAttribute("libri", libri); // Aggiungi la lista dei libri al modello

        return "challenge";
    }

    

    /**
     * Crea una nuova challenge e la associa all'utente autenticato.
     * 
     * @param challenge l'oggetto Challenge da creare
     * @param principal l'utente autenticato
     * @return il redirect alla pagina delle challenge
     */

    @PostMapping("/Challenge")
    public String createChallenge(@ModelAttribute Challenge challenge, Principal principal) {
        challenge.setStart_date(LocalDate.now());
        Challenge savedChallenge = challengeService.saveChallenge(challenge); 

        // Associa la challenge all'utente autenticato
        String email = principal.getName();
        Users user = userService.findByEmail(email);

        // Aggiungi l'utente alla challenge
        challengeService.addChallengeToUser(user.getId(), savedChallenge.getId());
        return "redirect:/Challenge";
    }


    /**
     * Unisce l'utente autenticato a una challenge esistente.
     * 
     * @param challengeId l'ID della challenge a cui unirsi
     * @param principal l'utente autenticato
     * @return il redirect alla pagina delle challenge
     */
    @PostMapping("/Challenge/join")
    public String joinChallenge(@RequestParam int challengeId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        challengeService.addChallengeToUser(user.getId(), challengeId);
        return "redirect:/Challenge";
    }

    /**
     * Permette all'utente autenticato di lasciare una challenge.
     * 
     * @param challengeId l'ID della challenge da cui uscire
     * @param principal l'utente autenticato
     * @return il redirect alla pagina delle challenge
     */
    @PostMapping("/Challenge/leave")
    public String leaveChallenge(@RequestParam int challengeId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        challengeService.removeChallengeFromUser(user.getId(), challengeId);
        return "redirect:/Challenge";
    }
    
    /**
     * Segna un libro come letto all'interno di una challenge.
     * 
     * @param challengeId l'ID della challenge
     * @param bookTitle il titolo del libro da segnare come letto
     * @param principal l'utente autenticato
     * @return il redirect alla pagina del profilo
     */
    @PostMapping("/challenge/mark-read")
    public String markBookAsRead(
        @RequestParam int challengeId,
        @RequestParam String bookTitle,
        Principal principal
    ) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);

        // Trova il libro per titolo
        Libro libro = libroRepository.findAll().stream()
            .filter(l -> l.getTitle().equalsIgnoreCase(bookTitle.trim()))
            .findFirst()
            .orElse(null);

        // Se il libro esiste e l'utente partecipa alla challenge
        if (libro != null && challengeService.userIsInChallenge(user.getId(), challengeId)) {
            // Segna il libro come letto per l'utente
            LibroUser libroUser = libroUserRepository.findByUserAndBook(user, libro)
                .orElseGet(() -> {
                    LibroUser lu = new LibroUser();
                    lu.setUser(user);
                    lu.setBook(libro);
                    return lu;
                });
            libroUser.setRead(true);
            libroUser.setRead_date(java.time.LocalDate.now());
            libroUserRepository.save(libroUser);

            challengeService.incrementScore(user.getId(), challengeId, 1);
        }
        return "redirect:/profile";
    }

    

}
