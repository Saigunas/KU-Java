package com.example.SaigunasBirbalas.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nationalized
    @Column(length = Integer.MAX_VALUE)
    private String name;

    @Nationalized
    @Column(length = Integer.MAX_VALUE)
    private String surname;

    @Column(length = 128)
    private String email;

    @Column(length = 128, nullable = true)
    private String phone;

    @ManyToOne
    @JoinColumn(name="training_group_id")
    private TrainingGroup trainingGroup;

    public TrainingGroup getTrainingGroup() {
        return trainingGroup;
    }

    public void setTrainingGroup(TrainingGroup trainingGroup) {
        this.trainingGroup = trainingGroup;
    }

    public Client(String name, String surname, String email, String phone, TrainingGroup trainingGroup) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.trainingGroup = trainingGroup;
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}