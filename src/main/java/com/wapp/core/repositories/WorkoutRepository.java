package com.wapp.core.repositories;

import com.wapp.core.dto.ExerciseDto;
import com.wapp.core.models.SerieModel;
import com.wapp.core.models.WorkoutModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutRepository {

    public WorkoutModel getWorkoutById(Connection conn, String workout_id) throws SQLException {

        String query = "SELECT w.name, w.user_id, w.description, w.date, w.duration, w.start_time, w.end_time, " +
                               " ed.exercise_order, ed.id, ed.exercise_id, ed.description, " +
                               " e.name, e.muscle_group, es.id, es.exercise_done_id, es.repetitions, es.weight, es.series_order, es.description " +
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
                workoutModel.setUserId(res.getString("w.user_id"));
                workoutModel.setDescription(res.getString("w.description"));
                workoutModel.setDate(res.getString("w.date"));
                workoutModel.setDuration(res.getString("w.duration"));
                workoutModel.setStartTime(res.getString("w.start_time"));
                workoutModel.setEndTime(res.getString("w.end_time"));
            }

            // Se o exercício não foi encontrado, cria um novo
            if (exerciseDto == null) {
                exerciseDto = new ExerciseDto();
                exerciseDto.setId(exerciseId);
                exerciseDto.setWorkoutId(workout_id);
                exerciseDto.setUserId(res.getString("w.user_id"));
                exerciseDto.setExerciseId(res.getString("ed.exercise_id"));
                exerciseDto.setExerciseOrder(res.getInt("ed.exercise_order"));
                exerciseDto.setDescription(res.getString("ed.description"));
                exerciseDto.setName(res.getString("e.name"));
                exerciseDto.setMuscleGroup(res.getString("e.muscle_group"));

                workoutModel.getExercises().add(exerciseDto);
            }

            // Cria uma nova série e adiciona ao exercício
            SerieModel seriesModel = new SerieModel();
            seriesModel.setId(res.getString("es.id"));
            seriesModel.setExerciseDoneId(res.getString("es.exercise_done_id"));
            seriesModel.setRepetitions(res.getInt("es.repetitions"));
            seriesModel.setWeight(res.getInt("es.weight"));
            seriesModel.setSerieOrder(res.getInt("es.series_order"));
            seriesModel.setDescription(res.getString("es.description"));

            exerciseDto.getSeries().add(seriesModel);
        }

        return workoutModel;
    }

    public void doneWorkout(Connection conn, WorkoutModel workoutModel) throws SQLException {

        String query = "INSERT INTO WORKOUTS (id, user_id, name, description, date, duration, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement workouts_stm = conn.prepareStatement(query);
        workouts_stm.setString(1, workoutModel.getId());
        workouts_stm.setString(2, workoutModel.getUserId());
        workouts_stm.setString(3, workoutModel.getName());
        workouts_stm.setString(4, workoutModel.getDescription());
        workouts_stm.setString(5, workoutModel.getDate());
        workouts_stm.setString(6, workoutModel.getDuration());
        workouts_stm.setString(7, workoutModel.getStartTime());
        workouts_stm.setString(8, workoutModel.getEndTime());

        workouts_stm.executeUpdate();
    }

    public void doneExercise(Connection conn, ExerciseDto exercise) throws SQLException {
        String query = "INSERT INTO EXERCISES_DONE (id, workout_id, user_id, exercise_id, exercise_order, description) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement exercises_done_stm = conn.prepareStatement(query);

        exercises_done_stm.setString(1, exercise.getId());
        exercises_done_stm.setString(2, exercise.getWorkoutId());
        exercises_done_stm.setString(3, exercise.getUserId());
        exercises_done_stm.setString(4, exercise.getExerciseId());
        exercises_done_stm.setInt(5, exercise.getExerciseOrder());
        exercises_done_stm.setString(6, exercise.getDescription());

        exercises_done_stm.executeUpdate();
    }

    public void doneSerie(Connection conn, SerieModel serie) throws SQLException {

        String query = "INSERT INTO EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order, description) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement exercises_series_stm = conn.prepareStatement(query);

        exercises_series_stm.setString(1, serie.getId());
        exercises_series_stm.setString(2, serie.getExerciseDoneId());
        exercises_series_stm.setInt(3, serie.getRepetitions());
        exercises_series_stm.setInt(4, serie.getWeight());
        exercises_series_stm.setInt(5, serie.getSerieOrder());
        exercises_series_stm.setString(6, serie.getDescription());

        exercises_series_stm.executeUpdate();
    }

}
