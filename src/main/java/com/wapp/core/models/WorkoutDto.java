package com.wapp.core.models;

import lombok.Data;

import java.util.Date;

@Data
public class WorkoutDto {
    private String name;
    private String description;
    private String date;
    private String duration;
    private String startTime;
    private String endTime;

    // TODO: list of exercises
    // private List<ExerciseDto> exercises;
}
