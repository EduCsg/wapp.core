package com.wapp.core.resources;

import com.wapp.core.services.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/core/workout")
public class WorkoutResource {

    WorkoutService workoutService = new WorkoutService();

    @GetMapping("/{workoutId}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String workoutId) {
        return workoutService.getWorkoutById(workoutId);
    }
}
