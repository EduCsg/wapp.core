package com.wapp.core.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutDto {
    private String id;
    private String name;
    private String description;
    private String date;
    private String duration;
    private String startTime;
    private String endTime;

    private List<ExerciseDto> exercises = new ArrayList<>();
}
