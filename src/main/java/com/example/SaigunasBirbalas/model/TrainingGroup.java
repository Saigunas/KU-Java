package com.example.SaigunasBirbalas.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TrainingGroups")
public class TrainingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Nationalized
    @Column(length = Integer.MAX_VALUE)
    private String name;

    @OneToMany(mappedBy = "trainingGroup")
    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public TrainingGroup(String name) {
        this.name = name;
    }

    public TrainingGroup() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}