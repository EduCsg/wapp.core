package com.wapp.core.resources;

import com.wapp.core.models.ExerciseModel;
import com.wapp.core.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/core/exercises")
public class ExerciseResource {

    @Autowired
    ExerciseService exerciseService;

    @PostMapping({"/create/{userId}", "/create"})
    public ResponseEntity<?> createExercise(@PathVariable(required = false) String userId, @RequestBody ExerciseModel exerciseModel) {
        return exerciseService.createExercise(userId, exerciseModel);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String userId) {
        return exerciseService.getExerciseList(userId);
    }

}
