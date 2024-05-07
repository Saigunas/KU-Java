package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.model.Client;

import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.service.ClientService;
import com.example.SaigunasBirbalas.service.FileStorageService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    public FileStorageService fileStorageService;


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
            @RequestParam("agreementFile") MultipartFile agreementFile,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return back to the form with errors
            System.out.println(bindingResult.getAllErrors());
            return "client_new";
        }
        Client c = new Client(client.getName(), client.getSurname(), client.getEmail(), client.getPhone());
        c.setAgreement(agreementFile.getOriginalFilename());
        clientRepository.save(c);
        fileStorageService.store(agreementFile, c.getId().toString() );
        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/agreement")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Integer id){
        Client c=clientRepository.getReferenceById(id);

        Resource r= fileStorageService.loadFile( c.getId().toString() );
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ c.getAgreement() +"\"")
                .body(r);
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
            @RequestParam(value = "agreementFile", required = false) MultipartFile agreementFile,
            @RequestParam(value = "deleteFile", required = false) String deleteFile,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return back to the form with errors
            return "client_update";
        }

        Client c=clientRepository.getReferenceById(id);
        // Check if the delete file button was clicked
        if (deleteFile != null) {
            // Delete the existing file associated with the client
            fileStorageService.deleteFile(client.getId().toString());
            // Clear the agreement field in the client object
            c.setAgreement(null);
        }

        if(agreementFile != null) {
            c.setAgreement(agreementFile.getOriginalFilename());
            fileStorageService.store(agreementFile, c.getId().toString() );
        }

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