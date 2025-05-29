package com.tbr.lettura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tbr.lettura.model.Users;
import com.tbr.lettura.service.UserService;

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
        return "register"; 
    }

    /**
     * Registra un nuovo utente e lo reindirizza alla pagina di login se la registrazione ha successo, altrimenti
     * restituisce nuovamente la pagina di registrazione con un messaggio di errore.
     * 
     * @param user l'oggetto User contenente le informazioni dell'utente
     * @param model il modello che contiene l'eventuale messaggio di errore
     * @return la view "redirect:/login" o "register" in base all'esito della registrazione
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user, Model model) {
       boolean success = userService.registerUser(user);

       if (success){
        return "redirect:/login"; 
       }
         else {
          model.addAttribute("error", "Email già in uso o password non valida (12 caratteri, almeno una maiuscola e un carattere speciale)");
          return "register"; 
         }

    }


}
