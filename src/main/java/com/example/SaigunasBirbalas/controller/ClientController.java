package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.model.Workout;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    ClientService clientService;
    @GetMapping("/clients")
    public String clients(Model model) {
        List<Client> clients = clientService.getClients();
        model.addAttribute("clients", clients);
        return "clients_list";
    }

    @GetMapping("/clients/new")
    public String newClient(Model model){
        Client c = new Client();
        model.addAttribute("client", c);
        return "client_new";
    }

    @PostMapping("/clients/new")
    public String storeClient(
            @Valid Client client,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return back to the form with errors
            return "client_new";
        }
        Client c = new Client(client.getName(), client.getSurname(), client.getEmail(), client.getPhone());
        clientRepository.save(c);

        return "redirect:/clients";
    }


    @GetMapping("/clients/update/{id}")
    public String update(
            @PathVariable("id") Integer id,
            Model model
    ){
        Optional<Client> c=clientRepository.findById(id);

        if (c.isPresent()) {
            Client client = c.get();
            model.addAttribute("client", client);
            return "client_update"; // Return the view name for displaying client details
        } else {
            // Handle the case where client with the specified ID is not found
            return "clientNotFound"; // Return the view name for displaying a message indicating client not found
        }
    }

    @PostMapping("/clients/update/{id}")
    public String save(
            @PathVariable("id") Integer id,
            @Valid Client client,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return back to the form with errors
            return "client_update";
        }
        Client c=clientRepository.getReferenceById(id);
        c.setName(client.getName());
        c.setSurname(client.getSurname());
        c.setEmail(client.getEmail());
        c.setPhone(client.getPhone());
        clientRepository.save(c);

        return "redirect:/clients";
    }

    @GetMapping("/clients/delete/{id}")
    public String delete(
            @PathVariable("id") Integer id
    ){
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
}