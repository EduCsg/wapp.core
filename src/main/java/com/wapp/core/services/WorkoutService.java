package com.wapp.core.services;

import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.WorkoutDto;
import com.wapp.core.repository.WorkoutRepository;
import com.wapp.core.utils.DatabaseConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class WorkoutService {

    WorkoutRepository workoutRepository = new WorkoutRepository();

    public ResponseEntity<?> createWorkout(WorkoutDto workout) {

        ResponseModel response = new ResponseModel();

        try {
            workout.setId(UUID.randomUUID().toString());
            Connection conn = DatabaseConnection.getConnection();

            workoutRepository.createWorkout(conn, workout);

            response.setStatus("Success");
            response.setSuccess(true);
            response.setMessage("Workout created successfully!");
            response.setData(workout);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus("Failed");
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
