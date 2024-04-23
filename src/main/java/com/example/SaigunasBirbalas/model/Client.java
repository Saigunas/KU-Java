package com.example.SaigunasBirbalas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nationalized
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    @Column(nullable = false)
    private String name;

    @Nationalized
    @NotNull(message = "Surname is required")
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 25, message = "Surname must be between 3 and 25 characters")
    @Column(nullable = false)
    private String surname;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email may not be blank")
    @Email(message = "Invalid email address")
    @Column(nullable = false)
    private String email;

    @Size(max = 15, message = "Phone number must be at most 15 characters")
    private String phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Registration> registrations;

    public Client(String name, String surname, String email, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public Client() {
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
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