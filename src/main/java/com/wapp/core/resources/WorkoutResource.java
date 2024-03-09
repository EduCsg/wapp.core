package com.wapp.core.resources;

import com.wapp.core.models.WorkoutModel;
import com.wapp.core.services.WorkoutService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/core/workout")
public class WorkoutResource {

    @Autowired
    WorkoutService workoutService;

    @GetMapping("/{workoutId}")
    public ResponseEntity<?> getWorkoutById(@PathVariable String workoutId) {
        return workoutService.getWorkoutById(workoutId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWorkout(@RequestBody WorkoutModel workoutModel) {
        return workoutService.createWorkout(workoutModel);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getWorkoutsHistoryByUserId(@PathVariable String userId, @PathParam("limit") Integer limit, @PathParam("offset") Integer offset) {
        return workoutService.getWorkoutsHistoryByUserId(userId, limit, offset);
    }

}
