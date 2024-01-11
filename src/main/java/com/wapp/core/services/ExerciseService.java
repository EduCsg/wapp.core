package com.wapp.core.services;

import com.wapp.core.models.ExerciseModel;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.repositories.ExerciseRepository;
import com.wapp.core.utils.DatabaseConnection;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.util.List;

public class ExerciseService {

    ExerciseRepository exerciseRepository = new ExerciseRepository();

    public ResponseEntity<?> getExerciseList(String userId) {

        ResponseModel response = new ResponseModel();

        try {
            Connection conn = DatabaseConnection.getConnection();
            List<ExerciseModel> exerciseList = exerciseRepository.getExerciseList(conn, userId);

            if (exerciseList.isEmpty()) {
                response.setMessage("No exercises found");
                response.setSuccess(false);
                response.setStatus("404");
                response.setSuccess(false);

                return ResponseEntity.notFound().build();
            }

            response.setMessage("Success");
            response.setSuccess(true);
            response.setStatus("200");
            response.setData(exerciseList);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("Error");
            response.setSuccess(false);
            response.setStatus("400");
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        }

    }

}
