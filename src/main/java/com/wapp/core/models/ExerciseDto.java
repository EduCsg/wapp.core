package com.wapp.core.models;

import lombok.Data;

@Data
public class ExerciseDto {
    private String id;
    private String name;
    private String muscleGroup;
    private String reps;
    private String weight;
    private String exerciseOrder;
}

// w.name, w.description, w.date, w.duration, w.start_time, w.end_time

// ed.exercise_id, ed.exercise_order, ed.reps, ed.weight, e.name, e.muscle_group
