package com.wapp.core.models;

import lombok.Data;

@Data
public class ExercisesDoneModel {

    private String id;
    private String workout_id;
    private String user_id;
    private String exercise_id;
    private Integer exercise_order;

}
