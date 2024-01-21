package com.wapp.core.repositories;

import com.wapp.core.dto.RoutineDto;
import com.wapp.core.models.ExerciseModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class RoutinesRepository {

    public List<RoutineDto> getRoutineListByUserId(Connection conn, String userId) throws SQLException {

        String query = "SELECT r.id, r.name, re.exercise_order, re.series, e.id, e.name, e.muscle_group " +
                               " FROM ROUTINES r " +
                               " LEFT JOIN ROUTINES_EXERCISES re on r.id = re.routine_id " +
                               " LEFT JOIN wapp_db.EXERCISES e on re.exercise_id = e.id " +
                               " where r.user_id = ? " +
                               " ORDER BY re.exercise_order;";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, userId);

        ResultSet res = stm.executeQuery();
        List<RoutineDto> routinesList = new ArrayList<>();


        while (res.next()) {
            String routineId = res.getString("r.id");
            RoutineDto routineDto = null;

            // Procura a rotina na lista de rotinas do routinesList
            for (RoutineDto r : routinesList) {
                if (r.getId().equals(routineId)) {
                    routineDto = r;
                    break;
                }
            }

            // se for uma rotina nova
            if (routineDto == null) {
                routineDto = new RoutineDto();
                routineDto.setId(routineId);
                routineDto.setName(res.getString("r.name"));

                // Adiciona a rotina na lista de rotinas
                routinesList.add(routineDto);
            }

            String exerciseId = res.getString("e.id");
            if (exerciseId == null) continue;

            // Cria um novo exercício
            ExerciseModel exerciseModel = new ExerciseModel();
            exerciseModel.setId(res.getString("e.id"));
            exerciseModel.setName(res.getString("e.name"));
            exerciseModel.setMuscleGroup(res.getString("e.muscle_group"));
            exerciseModel.setExerciseOrder(res.getInt("re.exercise_order"));
            exerciseModel.setSeries(res.getInt("re.series"));

            // Adiciona o exercício na rotina
            routineDto.getExercises().add(exerciseModel);
        }

        return routinesList;
    }

    public RoutineDto getRoutineById(Connection conn, String routineId) throws SQLException {

        String query = "SELECT r.id, r.name, re.exercise_order, re.series, e.id, e.name, e.muscle_group " +
                               " FROM ROUTINES r " +
                               " LEFT JOIN ROUTINES_EXERCISES re on r.id = re.routine_id " +
                               " LEFT JOIN wapp_db.EXERCISES e on re.exercise_id = e.id " +
                               " WHERE r.id = ? " +
                               " ORDER BY re.exercise_order;";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, routineId);

        ResultSet res = stm.executeQuery();
        RoutineDto routineDto = null;

        while (res.next()) {
            if (routineDto == null) {
                routineDto = new RoutineDto();
                routineDto.setId(res.getString("r.id"));
                routineDto.setName(res.getString("r.name"));
            }

            String exerciseId = res.getString("e.id");
            if (exerciseId == null) continue;

            // Cria um novo exercício
            ExerciseModel exerciseModel = new ExerciseModel();
            exerciseModel.setId(res.getString("e.id"));
            exerciseModel.setName(res.getString("e.name"));
            exerciseModel.setMuscleGroup(res.getString("e.muscle_group"));
            exerciseModel.setExerciseOrder(res.getInt("re.exercise_order"));
            exerciseModel.setSeries(res.getInt("re.series"));

            // Adiciona o exercício na rotina
            routineDto.getExercises().add(exerciseModel);
        }

        return routineDto;
    }

    public void postRoutine(Connection conn, String userId, RoutineDto routineDto) throws SQLException {

        String query = " INSERT INTO ROUTINES (id, user_id, name) VALUES (?, ?, ?); ";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, routineDto.getId());
        stm.setString(2, userId);
        stm.setString(3, routineDto.getName());

        int affectedRows = stm.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Erro ao criar rotina!");
        }

    }

    public void linkRoutineToExercise(Connection conn, String routineId, ExerciseModel exercise) throws SQLException {

        String query = " INSERT INTO ROUTINES_EXERCISES (id, routine_id, exercise_id, exercise_order, series) " +
                               " VALUES (?, ?, ?, ?, ?); ";

        PreparedStatement stm = conn.prepareStatement(query);

        stm.setString(1, UUID.randomUUID().toString());
        stm.setString(2, routineId);
        stm.setString(3, exercise.getId());
        stm.setInt(4, exercise.getExerciseOrder());
        stm.setInt(5, exercise.getSeries());

        int affectedRows = stm.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Erro ao vincular exercício!");
        }

    }

}
