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
import com.tbr.lettura.model.Users;
import com.tbr.lettura.service.ChallengeService;
import com.tbr.lettura.service.UserService;

@Controller
public class ChallengeMVC {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private UserService userService;
    


    @GetMapping("/Challenge")
    public String getChallenge(Model model, @RequestParam(required = false) String search) {
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

    

}
