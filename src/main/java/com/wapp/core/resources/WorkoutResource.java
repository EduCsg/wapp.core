package com.wapp.core.resources;

import com.wapp.core.models.WorkoutDto;
import com.wapp.core.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/v1/core")
public class WorkoutResource {

    WorkoutService workoutService = new WorkoutService();

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutDto workout) throws SQLException {

        System.out.println(workout.toString());

        return workoutService.createWorkout(workout);
    }

}
