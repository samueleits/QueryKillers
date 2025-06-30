package com.tbr.lettura.controller;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.UserChallenge;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.repository.UserChallengeRepository;
import com.tbr.lettura.service.UserService;


/**
 * Controller MVC per la gestione degli utenti.
 * Gestisce la registrazione di nuovi utenti e la visualizzazione del form di registrazione.
 */
@Controller
public class UserMVC {

    @Autowired
    private UserService userService;

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private LibroUserRepository libroUserRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Mostra il form per la registrazione di un nuovo utente.
     * 
     * @param model il modello che contiene l'oggetto User da popolare
     * @return la view "register"
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users()); 
        return "login"; 
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    
    /**
     * Mostra il profilo dell'utente autenticato, inclusi i libri nella libreria,
     * i libri letti, il livello dell'utente e le challenge a cui partecipa.
     * 
     * @param model il modello che contiene le informazioni da visualizzare
     * @param principal l'utente autenticato
     * @return la view "profile"
     */
    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        model.addAttribute("user", user);
        List<Libro> libri = libroRepository.findAll();
        model.addAttribute("libri", libri);

        // Recupera tutti i libri dell'utente
        List<LibroUser> libriNellaLibreria = libroUserRepository.findByUser(user);

        // Lista libri letti e non letti
        List<LibroUser> libriLetti = libriNellaLibreria.stream()
            .filter(LibroUser::isRead)
            .toList();

        List<LibroUser> libriNonLetti = libriNellaLibreria.stream()
            .filter(lu -> !lu.isRead())
            .toList();

        // Passa le liste al template
        model.addAttribute("libriLetti", libriLetti);
        model.addAttribute("libriNonLetti", libriNonLetti);

        // Passa i conteggi al template
        model.addAttribute("libriLettiCount", libriLetti.size());
        model.addAttribute("libriDaLeggere", libriNonLetti.size());

        // Calcola il livello in base al numero di libri letti
        int libriLettiCount = libriLetti.size();
        String livello;
        String animalImg;
        if (libriLettiCount >= 0 && libriLettiCount <= 4) {
            livello = "Bruco";
            animalImg = "(0-4)bruco(livello 0).png";
        } else if (libriLettiCount >= 5 && libriLettiCount <= 9) {
            livello = "Topo";
            animalImg = "(5-9)topo(livello 1).png";
        } else if (libriLettiCount >= 10 && libriLettiCount <= 14) {
            livello = "Coniglio";
            animalImg = "(10-14)coniglio(livello 2).png";
        } else if (libriLettiCount >= 15 && libriLettiCount <= 22) {
            livello = "Volpe";
            animalImg = "(15-22)volpe(livello 3).png";
        } else if (libriLettiCount >= 23 && libriLettiCount <= 31) {
            livello = "Lupo";
            animalImg = "(23-31)lupo(livello 4).png";
        } else if (libriLettiCount >= 32 && libriLettiCount <= 39) {
            livello = "Orso";
            animalImg = "(32-39)orso(livello 5).png";
        } else if (libriLettiCount >= 40 && libriLettiCount <= 47) {
            livello = "Gufo";
            animalImg = "(40-47)gufo(livello 6).png";
        } else if (libriLettiCount >= 48 && libriLettiCount <= 50) {
            livello = "Drago";
            animalImg = "(48-50)drago(livello 7).png";
        } else {
            livello = "Bruco";
            animalImg = "(0-4)bruco(livello 0).png";
        }
        model.addAttribute("livello", livello);
        model.addAttribute("animalImg", animalImg);

        // Challenge a cui partecipa
        List<UserChallenge> userChallenges = userChallengeRepository.findByUserId(user.getId());
        model.addAttribute("userChallenges", userChallenges);

        // Per ogni challenge, aggiungi la classifica (leaderboard)
        Map<Integer, List<UserChallenge>> leaderboardMap = new HashMap<>();
        for (UserChallenge uc : userChallenges) {
            int challengeId = uc.getChallenge().getId();
            List<UserChallenge> leaderboard = userChallengeRepository.findByChallengeId(challengeId)
                .stream()
                .sorted(Comparator.comparingInt(UserChallenge::getScore).reversed())
                .toList();
            leaderboardMap.put(challengeId, leaderboard);
        }
        model.addAttribute("leaderboardMap", leaderboardMap);

        return "profile";
    }



    /**
     * Registra un nuovo utente e lo reindirizza alla pagina di login se la registrazione ha successo,
     * altrimenti restituisce nuovamente la pagina di registrazione con un messaggio di errore.
     * 
     * @param user l'oggetto User contenente le informazioni dell'utente
     * @param model il modello che contiene l'eventuale messaggio di errore
     * @return la view "redirect:/login" o "register" in base all'esito della registrazione
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user, Model model) {
        System.out.println("Ricevuta richiesta di registrazione per: " + user.getEmail());
        if (userService.emailExists(user.getEmail())) {
            System.out.println("Email già in uso: " + user.getEmail());
            model.addAttribute("registerError", "Email già in uso.");
            model.addAttribute("showRegister", true);
            return "login";
        }
        if (!userService.isPasswordValid(user.getPassword_hash())) {
            System.out.println("Password non valida per: " + user.getEmail());
            model.addAttribute("registerError", "Password non valida: almeno 12 caratteri, una maiuscola e un carattere speciale.");
            model.addAttribute("showRegister", true);
            return "login";
        }
        userService.registerUser(user);
        System.out.println("Registrazione avvenuta con successo per: " + user.getEmail());
        model.addAttribute("registerSuccess", "Registrazione avvenuta con successo! Ora puoi accedere.");
        return "login";
    }

    @PostMapping("/login")
        public String loginUser(@ModelAttribute("user") Users user, Model model) {
            Users dbUser = userService.findByEmail(user.getEmail());
            if (dbUser == null) {
                model.addAttribute("loginError", "Email non trovata.");
                model.addAttribute("showRegister", false); // Attiva il form di login
                return "login";
            }
            if (!dbUser.getPassword_hash().equals(user.getPassword_hash())) {
                model.addAttribute("loginError", "Password errata.");
                model.addAttribute("showRegister", false); // Attiva il form di login
                return "login";
            }
            return "redirect:/Home";
        }

    @PostMapping("/profile")
        public String updateUserProfile(@ModelAttribute("user") Users user, Model model) {
            // Recupera il vero utente dal DB (per sicurezza)
            Users dbUser = userService.findById(user.getId());

            // Ricalcola il livello animale
            String livello = userService.calcolaLivelloAnimale(dbUser);

            // Aggiungi attributi al modello
            model.addAttribute("user", dbUser);
            model.addAttribute("livello", livello);

            return "profile";
        }

    

   

    /**
     * Rimuove un libro dalla libreria dell'utente.
     *
     * @param libroUserId l'ID della relazione Libro-User da rimuovere
     * @param principal l'utente autenticato
     * @return la redirect alla pagina del profilo
     */
    @PostMapping("/profile/remove-from-library")
    public String removeFromLibrary(@RequestParam("libroUserId") int libroUserId, Principal principal) {
        LibroUser libroUser = libroUserRepository.findById(libroUserId).orElse(null);
        if (libroUser != null && libroUser.getUser().getEmail().equals(principal.getName())) {
            libroUserRepository.delete(libroUser);
        }
        return "redirect:/profile";
    }

    /**
     * Segna un libro come letto.
     *
     * @param libroUserId l'ID della relazione Libro-User da aggiornare
     * @param principal l'utente autenticato
     * @return la redirect alla pagina del profilo
     */
    @PostMapping("/profile/mark-as-read")
    public String markAsRead(@RequestParam("libroUserId") int libroUserId, Principal principal) {
        LibroUser libroUser = libroUserRepository.findById(libroUserId).orElse(null);
        if (libroUser != null && libroUser.getUser().getEmail().equals(principal.getName())) {
            libroUser.setRead(true);
            libroUserRepository.save(libroUser);
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/mark-as-unread")
    public String markAsUnread(@RequestParam("libroUserId") int libroUserId, Principal principal) {
        LibroUser libroUser = libroUserRepository.findById(libroUserId).orElse(null);
        if (libroUser != null && libroUser.getUser().getEmail().equals(principal.getName())) {
            libroUser.setRead(false);
            libroUserRepository.save(libroUser);
        }
        return "redirect:/profile";
    }

}