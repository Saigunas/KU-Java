package com.example.SaigunasBirbalas.service;


import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }
}
