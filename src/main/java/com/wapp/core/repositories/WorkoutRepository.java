package com.wapp.core.repositories;

import com.wapp.core.dto.ExerciseDto;
import com.wapp.core.models.SeriesModel;
import com.wapp.core.models.WorkoutModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutRepository {

    public WorkoutModel getWorkoutById(Connection conn, String workout_id) throws SQLException {

        String query = "SELECT w.name, w.user_id, w.description, w.date, w.duration, w.start_time, w.end_time, " +
                               " ed.exercise_order, ed.id, ed.exercise_id, " +
                               " e.name, e.muscle_group, es.id, es.exercise_done_id, es.repetitions, es.weight, es.series_order " +
                               " FROM WORKOUTS w " +
                               " INNER JOIN EXERCISES_DONE ed on w.id = ed.workout_id " +
                               " INNER JOIN EXERCISES e on ed.exercise_id = e.id and w.id = ed.workout_id " +
                               " INNER JOIN EXERCISES_SERIES es on ed.id = es.exercise_done_id " +
                               " WHERE w.id = ? " +
                               " ORDER BY ed.exercise_order, es.series_order;";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, workout_id);

        ResultSet res = stm.executeQuery();

        WorkoutModel workoutModel = new WorkoutModel();

        while (res.next()) {
            String exerciseId = res.getString("ed.exercise_id");
            ExerciseDto exerciseDto = null;

            // Procura o exercício na lista de exercícios do workoutModel
            for (ExerciseDto e : workoutModel.getExercises()) {
                if (e.getId().equals(exerciseId)) {
                    exerciseDto = e;
                    break;
                }
            }

            // se o id do workout ainda não foi setado, seta
            if (workoutModel.getId() == null) {
                workoutModel.setId(workout_id);
                workoutModel.setName(res.getString("w.name"));
                workoutModel.setUser_id(res.getString("w.user_id"));
                workoutModel.setDescription(res.getString("w.description"));
                workoutModel.setDate(res.getString("w.date"));
                workoutModel.setDuration(res.getString("w.duration"));
                workoutModel.setStart_time(res.getString("w.start_time"));
                workoutModel.setEnd_time(res.getString("w.end_time"));
            }

            // Se o exercício não foi encontrado, cria um novo
            if (exerciseDto == null) {
                exerciseDto = new ExerciseDto();
                exerciseDto.setId(exerciseId);
                exerciseDto.setWorkout_id(workout_id);
                exerciseDto.setUser_id(res.getString("w.user_id"));
                exerciseDto.setExercise_id(res.getString("ed.exercise_id"));
                exerciseDto.setExercise_order(res.getInt("ed.exercise_order"));
                exerciseDto.setName(res.getString("e.name"));
                exerciseDto.setMuscle_group(res.getString("e.muscle_group"));

                workoutModel.getExercises().add(exerciseDto);
            }

            // Cria uma nova série e adiciona ao exercício
            SeriesModel seriesModel = new SeriesModel();
            seriesModel.setId(res.getString("es.id"));
            seriesModel.setExerciseDoneId(res.getString("es.exercise_done_id"));
            seriesModel.setRepetitions(res.getInt("es.repetitions"));
            seriesModel.setWeight(res.getInt("es.weight"));
            seriesModel.setSeries_order(res.getInt("es.series_order"));

            exerciseDto.getSeries().add(seriesModel);
        }

        return workoutModel;
    }

}
