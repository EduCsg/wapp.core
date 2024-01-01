package com.wapp.core.models;

import com.wapp.core.dto.ExerciseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkoutModel {

    private String id;
    private String user_id;
    private String name;
    private String description;
    private String date;
    private String duration;
    private String start_time;
    private String end_time;

    private List<ExerciseDto> exercises = new ArrayList<>();
}
