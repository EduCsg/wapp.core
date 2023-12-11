package com.wapp.core.resources;

import com.wapp.core.models.WorkoutModel;
import com.wapp.core.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/core")
public class WorkoutResource {

    WorkoutService workoutService = new WorkoutService();

    @PostMapping("/workout")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutModel workout) {
        return workoutService.createWorkout(workout);
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<?> getWorkout(@PathVariable String workoutId) {
        return workoutService.getWorkoutById(workoutId);
    }

    @GetMapping("/workout/user/{userId}")
    public ResponseEntity<?> getWorkoutByUserId(@PathVariable String userId) {
        return workoutService.getWorkoutByUserId(userId);
    }

}
