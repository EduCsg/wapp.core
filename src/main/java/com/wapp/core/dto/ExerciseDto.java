package com.wapp.core.dto;

import com.wapp.core.models.SeriesModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExerciseDto {

    private String id;
    private String workout_id;
    private String user_id;
    private String exercise_id;
    private int exercise_order;
    private String name;
    private String muscle_group;

    private List<SeriesModel> series = new ArrayList<>();
}
