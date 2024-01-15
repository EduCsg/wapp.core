package com.wapp.core.services;

import com.wapp.core.dto.ExerciseDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.SerieModel;
import com.wapp.core.models.WorkoutModel;
import com.wapp.core.repositories.WorkoutRepository;
import com.wapp.core.utils.DatabaseConnection;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class WorkoutService {

    WorkoutRepository workoutRepository = new WorkoutRepository();

    public ResponseEntity<?> getWorkoutById(String workoutId) {
        System.out.println("   [LOG] getWorkoutById  ->  workoutId: " + workoutId);

        ResponseModel response = new ResponseModel();

        try {
            Connection conn = DatabaseConnection.getConnection();
            WorkoutModel workoutModel = workoutRepository.getWorkoutById(conn, workoutId);

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

    public ResponseEntity<?> createWorkout(WorkoutModel workoutModel) {
        System.out.println("   [LOG] createWorkout  ->  workoutModel: " + workoutModel.toString());

        // Insere na ordem do banco de dados (PKs e FKs)
        // -> workout -> exercises_done -> exercises_series

        workoutModel.setId(UUID.randomUUID().toString());
        ResponseModel response = new ResponseModel();
        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            workoutRepository.doneWorkout(conn, workoutModel);

            for (ExerciseDto exercise : workoutModel.getExercises()) {
                exercise.setId(UUID.randomUUID().toString());
                exercise.setWorkoutId(workoutModel.getId());
                exercise.setUserId(workoutModel.getUserId());
                exercise.setExerciseOrder(workoutModel.getExercises().indexOf(exercise) + 1);

                workoutRepository.doneExercise(conn, exercise);

                for (SerieModel serie : exercise.getSeries()) {
                    serie.setId(UUID.randomUUID().toString());
                    serie.setExerciseDoneId(exercise.getId());

                    workoutRepository.doneSerie(conn, serie);
                }
            }

            conn.commit();

            response.setMessage("Treino criado com sucesso!");
            response.setSuccess(true);
            response.setStatus("201");
            response.setData("Treino criado com sucesso!");

            return ResponseEntity.status(201).body(response);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("500");

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
