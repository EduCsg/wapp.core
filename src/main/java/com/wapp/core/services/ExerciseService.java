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
import java.util.UUID;

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
                response.setMessage("Nenhum exercício encontrado!");
                response.setSuccess(false);
                response.setStatus("404");
                response.setSuccess(false);

                return ResponseEntity.notFound().build();
            }

            response.setMessage("Exercícios encontrados com sucesso!");
            response.setSuccess(true);
            response.setStatus("200");
            response.setData(exerciseList);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("400");
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

    public ResponseEntity<?> createExercise(String userId, ExerciseModel exerciseModel) {
        System.out.println("   [LOG] createExercise  ->  exerciseName: " + exerciseModel.getName());

        ResponseModel response = new ResponseModel();
        Connection conn = null;

        try {
            exerciseModel.setId(UUID.randomUUID().toString());

            conn = databaseConfig.getConnection();
            int result = exerciseRepository.createExercise(conn, userId, exerciseModel);

            if (result == 0) {
                response.setMessage("Houve um erro ao criar o exercício!");
                response.setSuccess(false);
                response.setStatus("400");
                response.setSuccess(false);

                return ResponseEntity.badRequest().body(response);
            }

            response.setMessage("Exercício criado com sucesso!");
            response.setSuccess(true);
            response.setStatus("201");
            response.setData(exerciseModel);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

        return null;
    }

    public ResponseEntity<?> deleteExerciseById(String userId, String exerciseId) {
        System.out.println("   [LOG] deleteExerciseById  ->  " + exerciseId);

        Connection conn = null;
        ResponseModel response = new ResponseModel();

        try {
            conn = databaseConfig.getConnection();

            int result = exerciseRepository.deleteExerciseById(conn, userId, exerciseId);

            if (result == 0) {
                response.setMessage("Houve um erro ao deletar o exercício!");
                response.setSuccess(false);
                response.setStatus("400");
                response.setSuccess(false);

                return ResponseEntity.badRequest().body(response);
            }

            response.setMessage("Exercício deletado com sucesso!");
            response.setSuccess(true);
            response.setStatus("200");
            response.setData(exerciseId);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("400");
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);

        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

    public ResponseEntity<?> deleteMultipleExercises(String userId, String[] exerciseIds) {
        System.out.println("   [LOG] deleteMultipleExercises  ->  userId: " + userId);

        Connection conn = null;
        ResponseModel response = new ResponseModel();

        try {
            conn = databaseConfig.getConnection();

            int result = exerciseRepository.deleteMultipleExercises(conn, userId, exerciseIds);

            if (result == 0) {
                response.setMessage("Houve um erro ao deletar os exercícios!");
                response.setSuccess(false);
                response.setStatus("400");
                response.setSuccess(false);

                return ResponseEntity.badRequest().body(response);
            }

            if (result == exerciseIds.length) {
                response.setMessage("Todos exercícios deletados com sucesso!");
                response.setSuccess(true);
                response.setStatus("200");
                response.setData(exerciseIds);

                return ResponseEntity.ok().body(response);
            }

            response.setMessage(result + " exercícios deletados com sucesso!");
            response.setSuccess(true);
            response.setStatus("200");

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.setMessage("Erro: " + e.getMessage());
            response.setSuccess(false);
            response.setStatus("400");
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);

        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

}
