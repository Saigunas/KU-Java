package com.example.SaigunasBirbalas.dataLoader;

import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class DataLoader {

    @Autowired
    private ClientRepository clientRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String rawPassword = "password";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            String username = "user";

            Client client = clientRepository.findByUsername(username);
            if (client == null) {
                client = new Client();
                client.setUsername(username);
                client.setPassword(encodedPassword);
                client.setName("fake");
                client.setSurname("fake");
                client.setEmail("fake@fake.com");
                clientRepository.save(client);
            }
        };
    }
}