package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.DTO.WorkoutWithAvailablePlacesDTO;
import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.model.Workout;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.WorkoutRepository;
import com.example.SaigunasBirbalas.service.RegistrationService;
import com.example.SaigunasBirbalas.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkoutController {
    @Autowired
    public WorkoutRepository workoutRepository;

    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    WorkoutService workoutService;
    @GetMapping("/")
    public String workouts(Model model){
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);
        return "workouts_list";
    }

    @GetMapping("/workouts/registrations/{id}")
    public String registrations(@PathVariable("id") Integer id, Model model){
        Workout workout= workoutRepository.getReferenceById(id);
        model.addAttribute("workout", workout);
        return "registrations_list";
    }

    @GetMapping("/workouts/registration/new/{id}")
    public String newWorkoutRegistration(@PathVariable("id") Integer id, Model model){
        Workout workout = workoutRepository.getReferenceById(id);
        model.addAttribute("currentWorkout", workout);

        List<Workout> workouts = workoutRepository.findAll();
        List<WorkoutWithAvailablePlacesDTO> workoutsWithAvailablePlaces = new ArrayList<>();
        for (Workout w : workouts) {
            int availablePlaces = workoutService.getAvailablePlacesInWorkout(w.getId());

            WorkoutWithAvailablePlacesDTO workoutDTO = new WorkoutWithAvailablePlacesDTO(w, availablePlaces);
            workoutsWithAvailablePlaces.add(workoutDTO);
        }

        model.addAttribute("workoutsWithAvailablePlaces", workoutsWithAvailablePlaces);

        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);

        return "registrations_new";
    }

    @GetMapping("/new")
    public String newWorkout(){
        return "workouts_new";
    }

    @PostMapping("/new")
    public String storeWorkout(
            @RequestParam("name") String name,
            @RequestParam("date") LocalDate date,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ){
        Workout g=new Workout(name, date, places, location);
        workoutRepository.save(g);
        return "redirect:/";
    }

    @GetMapping("/workouts/update/{id}")
    public String update(
            @PathVariable("id") Integer id,
            Model model
    ){
        Workout g=workoutRepository.getReferenceById(id);
        model.addAttribute("workout", g);
        return "workouts_update";
    }

    @PostMapping("/workouts/update/{id}")
    public String save(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("date") LocalDate date,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ){
        Workout g=workoutRepository.getReferenceById(id);
        g.setName(name);
        g.setDate(date);
        g.setPlaces(places);
        g.setLocation(location);

        workoutRepository.save(g);
        return "redirect:/";
    }

    @GetMapping("/workouts/delete/{id}")
    public  String delete(
            @PathVariable("id") Integer id
    ){
        workoutRepository.deleteById(id);
        return "redirect:/";
    }

}