package com.example.SaigunasBirbalas.DTO;

import com.example.SaigunasBirbalas.model.Workout;

public class WorkoutWithAvailablePlacesDTO {
    private Workout workout;
    private int availablePlaces;

    public WorkoutWithAvailablePlacesDTO() {
    }

    public WorkoutWithAvailablePlacesDTO(Workout workout, int availablePlaces) {
        this.workout = workout;
        this.availablePlaces = availablePlaces;
    }

    // Getters and setters
    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }
}
