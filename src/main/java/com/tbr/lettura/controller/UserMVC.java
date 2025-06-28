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

    /**     * Mostra il profilo dell'utente.
     *    * @param model il modello che contiene l'oggetto User da popolare         
     * @return la view "profile"
     */

    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);
        model.addAttribute("user", user);
        List<Libro> libri = libroRepository.findAll();
        model.addAttribute("libri", libri);

        // Libri letti
        int libriLetti = libroUserRepository.countLibriLettiByUserId(user.getId());
        model.addAttribute("libriLetti", libriLetti);

        // Calcola il livello (esempio: 1-4 = 'Principiante', 5-9 = 'Intermedio', ecc.)
        String livello;
        if (libriLetti >= 10) {
            livello = "Esperto";
        } else if (libriLetti >= 5) {
            livello = "Intermedio";
        } else if (libriLetti >= 1) {
            livello = "Principiante";
        } else {
            livello = "Nessun livello";
        }

        model.addAttribute("livello", livello);

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

        List<LibroUser> libriNellaLibreria = libroUserRepository.findByUser(user);
        
        model.addAttribute("libriNellaLibreria", libriNellaLibreria);

       
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

    @PostMapping("/profile/add-to-library")
    public String addToLibrary(@RequestParam("bookId") int bookId, Principal principal) {
        String email = principal.getName();
        Users user = userService.findByEmail(email);

        // Recupera il libro 
        Libro libro = libroRepository.findById(bookId).orElse(null);
        if (libro == null) return "redirect:/profile";

        // Evita duplicati
        if (libroUserRepository.findByUserAndBook(user, libro).isPresent()) {
            return "redirect:/profile";
        }

        // Crea e salva la relazione
        LibroUser libroUser = new LibroUser();
        libroUser.setUser(user);
        libroUser.setBook(libro);
        libroUser.setRead(false);
        libroUser.setRead_date(null);
        libroUserRepository.save(libroUser);

        return "redirect:/profile";
    }
    @PostMapping("/profile/remove-from-library")
    public String removeFromLibrary(@RequestParam("libroUserId") int libroUserId, Principal principal) {
        LibroUser libroUser = libroUserRepository.findById(libroUserId).orElse(null);
        if (libroUser != null && libroUser.getUser().getEmail().equals(principal.getName())) {
            libroUserRepository.delete(libroUser);
        }
        return "redirect:/profile";
    }
    @PostMapping("/profile/mark-as-read")
    public String markAsRead(@RequestParam("libroUserId") int libroUserId, Principal principal) {
        LibroUser libroUser = libroUserRepository.findById(libroUserId).orElse(null);
        if (libroUser != null && libroUser.getUser().getEmail().equals(principal.getName())) {
            libroUser.setRead(true);
            libroUser.setRead_date(java.time.LocalDate.now());
            libroUserRepository.save(libroUser);
        }
        return "redirect:/profile";
    }

}