package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.model.Registration;
import com.example.SaigunasBirbalas.model.Workout;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.RegistrationRepository;
import com.example.SaigunasBirbalas.repository.WorkoutRepository;
import com.example.SaigunasBirbalas.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    public RegistrationRepository registrationRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public WorkoutRepository workoutRepository;

    @Autowired
    RegistrationService registrationService;

    @GetMapping("/registrations/new")
    public String newRegistration(){
        return "registrations_new";
    }

    @PostMapping("/registrations/new")
    public String storeWorkoutRegistration(
            @RequestParam("workout_id") Integer workout_id,
            @RequestParam("client_id") Integer client_id
    ){
        Workout workout = workoutRepository.getReferenceById(workout_id);
        Client client = clientRepository.getReferenceById(client_id);

        Registration registration = new Registration(LocalDate.now(ZoneOffset.UTC), client, workout);
        registrationService.insertRegistration(registration);
        return "redirect:/workouts/registrations/" + workout_id;
    }

    @GetMapping("/registrations/delete/{id}")
    public  String delete(
            @PathVariable("id") Integer id
    ){
        Registration registration = registrationRepository.getReferenceById(id);
        Integer workout_id = registration.getWorkout().getId();
        registrationRepository.deleteById(id);
        return "redirect:/workouts/registrations/" + workout_id;
    }

}