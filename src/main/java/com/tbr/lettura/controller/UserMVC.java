package com.tbr.lettura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tbr.lettura.model.User;
import com.tbr.lettura.service.UserService;

@Controller
public class UserMVC {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); 
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
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
