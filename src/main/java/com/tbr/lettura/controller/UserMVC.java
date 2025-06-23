package com.tbr.lettura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.service.UserService;

/**
 * Controller MVC per la gestione degli utenti.
 * Gestisce la registrazione di nuovi utenti e la visualizzazione del form di registrazione.
 */
@Controller
public class UserMVC {

    @Autowired
    private UserService userService;

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
}