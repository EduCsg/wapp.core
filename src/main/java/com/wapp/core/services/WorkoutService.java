package com.wapp.core.services;

import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.WorkoutModel;
import com.wapp.core.repositories.WorkoutRepository;
import com.wapp.core.utils.DatabaseConnection;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class WorkoutService {

    public ResponseEntity<?> getWorkoutById(String workout_id) {

        WorkoutRepository workoutRepository = new WorkoutRepository();
        ResponseModel response = new ResponseModel();

        try {
            Connection conn = DatabaseConnection.getConnection();
            WorkoutModel workoutModel = workoutRepository.getWorkoutById(conn, workout_id);

            if (workoutModel.getId() == null) {
                response.setMessage("Workout not found");
                response.setSuccess(false);
                response.setStatus("404");
                return ResponseEntity.status(404).body(response);
            }

            response.setMessage("Workout found");
            response.setSuccess(true);
            response.setStatus("200");
            response.setData(workoutModel);

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            e.printStackTrace();

            response.setMessage("Error: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("500");
            return ResponseEntity.status(500).body(response);
        }
    }
}
