package com.wapp.core.services;

import com.wapp.core.models.ExerciseModel;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.repositories.ExerciseRepository;
import com.wapp.core.utils.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    DatabaseConfig databaseConfig;

    @Autowired
    ExerciseRepository exerciseRepository;

    public ResponseEntity<?> getExerciseList(String userId) {
        System.out.println("   [LOG] getExerciseList  ->  userId: " + userId);

        ResponseModel response = new ResponseModel();
        Connection conn = null;

        try {
            conn = databaseConfig.getConnection();
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
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

}
