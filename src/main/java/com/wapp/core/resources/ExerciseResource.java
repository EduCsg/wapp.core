package com.wapp.core.resources;

import com.wapp.core.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/core/exercises")
public class ExerciseResource {

    ExerciseService exerciseService = new ExerciseService();

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String userId) {
        return exerciseService.getExerciseList(userId);
    }

}
