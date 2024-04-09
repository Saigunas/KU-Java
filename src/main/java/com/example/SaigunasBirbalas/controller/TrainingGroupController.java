package com.example.SaigunasBirbalas.controller;

import com.example.SaigunasBirbalas.model.Client;
import com.example.SaigunasBirbalas.model.TrainingGroup;
import com.example.SaigunasBirbalas.repository.ClientRepository;
import com.example.SaigunasBirbalas.repository.TrainingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class TrainingGroupController {
    @Autowired
    public TrainingGroupRepository trainingGroupRepository;

    @Autowired
    public ClientRepository clientRepository;

    @GetMapping("/")
    public String trainingGroups(Model model){
        List<TrainingGroup> trainingGroups= trainingGroupRepository.findAll();
        /*
        for(TrainingTrainingGroup g:trainingGroups){
            System.out.println("Grupė: "+g.getName());
            for (Client s:g.getClients()){
                System.out.println("Clientas: "+s.getName());

            }
        }*/
        model.addAttribute("trainingGroups", trainingGroups);
        return "trainingGroups_list";
    }

    @GetMapping("/new")
    public String newTrainingGroup(){
        return "trainingGroup_new";
    }

    @PostMapping("/new")
    public String storeTrainingGroup(
            @RequestParam("name") String name
    ){
        TrainingGroup g=new TrainingGroup(name);
        trainingGroupRepository.save(g);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String update(
            @PathVariable("id") Integer id,
            Model model
    ){
        TrainingGroup g=trainingGroupRepository.getReferenceById(id);
        model.addAttribute("trainingGroup", g);
        return "trainingGroup_update";
    }

    @PostMapping("/update/{id}")
    public String save(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name
    ){
        TrainingGroup g=trainingGroupRepository.getReferenceById(id);
        g.setName(name);
        trainingGroupRepository.save(g);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public  String delete(
            @PathVariable("id") Integer id
    ){
        TrainingGroup g=trainingGroupRepository.getReferenceById(id);
        for (Client s:g.getClients()){
            clientRepository.deleteById(s.getId());
        }
        trainingGroupRepository.deleteById(id);
        return "redirect:/";
    }

}