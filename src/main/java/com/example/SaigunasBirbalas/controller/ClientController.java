package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.model.TrainingGroup;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.TrainingGroupRepository;
import com.example.SaigunasBirbalas.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    public TrainingGroupRepository trainingGroupRepository;

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
        List<TrainingGroup> groups=trainingGroupRepository.findAll();
        model.addAttribute("trainingGroups",groups);

        return "client_new";
    }

    @PostMapping("/clients/new")
    public String storeClient(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("training_group_id") Integer trainingGroupId
    ){
        TrainingGroup g = trainingGroupRepository.getReferenceById(trainingGroupId);
        Client c = new Client(name, surname, email, phone, g);
        clientRepository.save(c);

        return "redirect:/clients";
    }


    @GetMapping("clients/update/{id}")
    public String update(
            @PathVariable("id") Integer id,
            Model model
    ){
        List<TrainingGroup> groups=trainingGroupRepository.findAll();
        model.addAttribute("trainingGroups",groups);

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
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("training_group_id") Integer trainingGroupId
    ){
        Client c=clientRepository.getReferenceById(id);
        TrainingGroup g = trainingGroupRepository.getReferenceById(trainingGroupId);
        c.setName(name);
        c.setSurname(surname);
        c.setEmail(email);
        c.setPhone(phone);
        c.setTrainingGroup(g);
        clientRepository.save(c);

        return "redirect:/clients";
    }

    @GetMapping("clients/delete/{id}")
    public String delete(
            @PathVariable("id") Integer id
    ){
        clientRepository.deleteById(id);
        return "redirect:/clients";
    }
}