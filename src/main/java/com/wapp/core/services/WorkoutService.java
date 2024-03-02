package com.wapp.core.services;

import com.wapp.core.dto.ExerciseDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.SerieModel;
import com.wapp.core.models.WorkoutModel;
import com.wapp.core.repositories.WorkoutRepository;
import com.wapp.core.utils.DatabaseConfig;
import com.wapp.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class WorkoutService {

    @Autowired
    DatabaseConfig databaseConfig;

    @Autowired
    WorkoutRepository workoutRepository;

    public ResponseEntity<?> getWorkoutById(String workoutId) {
        System.out.println("   [LOG] getWorkoutById  ->  workoutId: " + workoutId);
        ResponseModel response = new ResponseModel();

        Connection conn = null;

        try {
            conn = databaseConfig.getConnection();
            WorkoutModel workoutModel = workoutRepository.getWorkoutById(conn, workoutId);

            if (workoutModel.getId() == null) {
                response.setMessage("Treino não encontrado!");
                response.setSuccess(false);
                response.setStatus("404");
                return ResponseEntity.status(404).body(response);
            }

            response.setMessage("Treino encontrado com sucesso!");
            response.setSuccess(true);
            response.setStatus("200");
            response.setData(workoutModel);

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            e.printStackTrace();

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("500");
            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) {
                databaseConfig.closeConnection(conn);
            }
        }
    }

    public ResponseEntity<?> createWorkout(WorkoutModel workoutModel) {
        System.out.println("   [LOG] createWorkout  ->  userId: " + workoutModel.getUserId());

        // Insere na ordem do banco de dados (PKs e FKs)
        // -> workout -> exercises_done -> exercises_series

        ResponseModel response = new ResponseModel();
        workoutModel.setId(UUID.randomUUID().toString());
        workoutModel.setEndDate(DateUtils.getCurrentTimestamp());

        Connection conn = null;

        try {
            conn = databaseConfig.getConnection();
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
            e.printStackTrace();

            if (conn != null) {
                databaseConfig.rollback(conn);
            }

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("500");

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) {
                databaseConfig.enableAutoCommit(conn);
                databaseConfig.closeConnection(conn);
            }
        }
    }

    public ResponseEntity<?> getWorkoutsHistoryByUserId(String userId, Integer limit, Integer offset) {
        System.out.println("   [LOG] getWorkoutsHistoryByUserId  ->  userId: " + userId);
        
        Connection conn = null;
        ResponseModel response = new ResponseModel();

        try {
            conn = databaseConfig.getConnection();

            List<WorkoutModel> workoutsList = workoutRepository.getWorkoutsHistoryByUserId(conn, userId, limit, offset);

            if (workoutsList.isEmpty()) {
                response.setMessage("Nenhum treino encontrado!");
                response.setSuccess(false);
                response.setStatus("404");
                return ResponseEntity.status(404).body(response);
            }

            response.setMessage(workoutsList.size() + " treinos encontrados!");
            response.setSuccess(true);
            response.setStatus("200");
            response.setData(workoutsList);

            return ResponseEntity.ok(response);

        } catch (SQLException e) {
            e.printStackTrace();

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("500");

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) {
                databaseConfig.closeConnection(conn);
            }
        }
    }

}
