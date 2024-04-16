package com.example.SaigunasBirbalas.service;


import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.model.Registration;
import com.example.SaigunasBirbalas.model.Workout;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.RegistrationRepository;
import com.example.SaigunasBirbalas.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegistrationService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    public RegistrationRepository registrationRepository;
    @Autowired
    public WorkoutRepository workoutRepository;
    @Transactional
    public void insertRegistration(Registration registration) {
        // Calculate the number of registered clients in the group
        long registeredClientsCount = registrationRepository
                .findAllByWorkoutId(registration.getWorkout().getId()).stream().count();

        // Check if the group has zero places based on its capacity and the number of registered clients
        if (registration.getWorkout().getPlaces() <= registeredClientsCount) {
            throw new IllegalArgumentException("Cannot assign client to a full group");
        }

        registrationRepository.save(registration);
    }
}
