package com.wapp.core.dto;

import com.wapp.core.models.SerieModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExerciseDto {

    private String id;
    private String workoutId;
    private String userId;
    private String exerciseId;
    private int exerciseOrder;
    private String name;
    private String muscleGroup;
    private String description;

    private List<SerieModel> series = new ArrayList<>();
}
