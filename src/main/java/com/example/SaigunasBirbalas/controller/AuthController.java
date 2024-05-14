package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class AuthController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }

    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        client.setPassword(encoder.encode(client.getPassword()));
        clientService.save(client);
        return "redirect:/login";
    }
}
