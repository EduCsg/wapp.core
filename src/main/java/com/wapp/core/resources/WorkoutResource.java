package com.wapp.core.resources;

import com.wapp.core.models.WorkoutModel;
import com.wapp.core.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/core/workout")
public class WorkoutResource {

    WorkoutService workoutService = new WorkoutService();

    @GetMapping("/{workoutId}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String workoutId) {
        return workoutService.getWorkoutById(workoutId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutModel workoutModel) {
        return workoutService.createWorkout(workoutModel);
    }
}
