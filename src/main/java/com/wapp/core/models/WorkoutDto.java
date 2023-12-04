package com.wapp.core.models;

import lombok.Data;

import java.util.Date;

@Data
public class WorkoutDto {
    private String id;
    private String name;
    private String description;
    private String date;
    private String duration;
    private String startTime;
    private String endTime;

    // TODO: list of exercises
    // private List<ExerciseDto> exercises;


    @Override
    public String toString() {
        return "WorkoutDto{" +
                       "id='" + id + '\'' +
                       ", name='" + name + '\'' +
                       ", description='" + description + '\'' +
                       ", date='" + date + '\'' +
                       ", duration='" + duration + '\'' +
                       ", startTime='" + startTime + '\'' +
                       ", endTime='" + endTime + '\'' +
                       '}';
    }
}
