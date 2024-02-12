package com.wapp.core.models;

import com.wapp.core.dto.ExerciseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutModel {

    private String id;
    private String userId;
    private String name;
    private String description;
    private String date;
    private String duration;
    private String startTime;
    private String endTime;

    private List<ExerciseDto> exercises = new ArrayList<>();
}
