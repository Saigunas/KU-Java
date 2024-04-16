package com.example.SaigunasBirbalas.repository;

import com.example.SaigunasBirbalas.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

}