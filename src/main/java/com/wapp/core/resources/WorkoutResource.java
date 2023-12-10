package com.wapp.core.resources;

import com.wapp.core.models.WorkoutDto;
import com.wapp.core.models.WorkoutModel;
import com.wapp.core.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/v1/core")
public class WorkoutResource {

    WorkoutService workoutService = new WorkoutService();

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutModel workout) throws SQLException {
        return workoutService.createWorkout(workout);
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<?> getWorkout(@PathVariable String workoutId) throws SQLException {
        return workoutService.getWorkoutById(workoutId);
    }
    
}
