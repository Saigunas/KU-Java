package com.example.SaigunasBirbalas.repository;

import com.example.SaigunasBirbalas.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    List<Registration> findAllByWorkoutId(long workout_id);
}