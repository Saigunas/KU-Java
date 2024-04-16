package com.example.SaigunasBirbalas.service;


import com.example.SaigunasBirbalas.model.Registration;
import com.example.SaigunasBirbalas.model.Workout;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.RegistrationRepository;
import com.example.SaigunasBirbalas.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class WorkoutService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    public RegistrationRepository registrationRepository;
    @Autowired
    public WorkoutRepository workoutRepository;
    @Transactional
    public Integer getAvailablePlacesInWorkout(Integer workout_id) {
        Workout workout = workoutRepository.getReferenceById(workout_id);

        if (workout == null) {
            throw new NoSuchElementException("Workout not found");
        }

        // Calculate the number of registered clients in the group
        long registeredClientsCount = registrationRepository
                .findAllByWorkoutId(workout.getId()).stream().count();

        Integer places = workout.getPlaces();
        return Math.toIntExact(places.intValue() - registeredClientsCount);
    }
}
