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

    @GetMapping("/Challenge")
    public String getChallenge(Model model, @RequestParam(required = false) String search, Principal principal) {
        List<Challenge> challenges;
        if (search != null && !search.isEmpty()) {
            challenges = challengeService.getAllChallenges().stream()
                .filter(c -> c.getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        } else {
            challenges = challengeService.getAllChallenges();
        }
        model.addAttribute("challenges", challenges);
        model.addAttribute("challenge", new Challenge());

        // Aggiungi la lista delle challenge a cui l'utente partecipa
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        List<Integer> joinedChallengeIds = challengeService.getUserChallenges(user.getId())
            .stream().map(uc -> uc.getChallenge().getId()).toList();
        model.addAttribute("joinedChallengeIds", joinedChallengeIds);

        return "challenge";
    }

    


    @PostMapping("/Challenge")
    public String createChallenge(@ModelAttribute Challenge challenge, Principal principal) {
        challenge.setStart_date(LocalDate.now());
        Challenge savedChallenge = challengeService.saveChallenge(challenge); 

        String email = principal.getName();
        Users user = userService.findByEmail(email);

        // Usa l'ID della challenge appena salvata
        challengeService.addChallengeToUser(user.getId(), savedChallenge.getId());
        return "redirect:/Challenge";
    }

    @PostMapping("/Challenge/join")
    public String joinChallenge(@RequestParam int challengeId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        challengeService.addChallengeToUser(user.getId(), challengeId);
        return "redirect:/Challenge";
    }

    @PostMapping("/Challenge/leave")
    public String leaveChallenge(@RequestParam int challengeId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        challengeService.removeChallengeFromUser(user.getId(), challengeId);
        return "redirect:/Challenge";
    }

    @PostMapping("/challenge/add-book")
    public String aggiungiLibroAChallenge(@RequestParam int challengeId, @RequestParam int bookId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
  
        // Verifica che partecipi alla challenge
        if (challengeService.userIsInChallenge(user.getId(), challengeId)) {
            // Aggiorna il punteggio (es: +1)
            challengeService.incrementScore(user.getId(), challengeId, 1);
        }
        return "redirect:/profile";
    }

    @PostMapping("/challenge/mark-read")
    public String markBookAsRead(
        @RequestParam int challengeId,
        @RequestParam String bookTitle,
        Principal principal
    ) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);

        Libro libro = libroRepository.findAll().stream()
            .filter(l -> l.getTitle().equalsIgnoreCase(bookTitle.trim()))
            .findFirst()
            .orElse(null);

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
