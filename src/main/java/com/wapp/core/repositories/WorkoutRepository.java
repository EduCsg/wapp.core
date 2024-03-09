package com.wapp.core.repositories;

import com.wapp.core.dto.ExerciseDto;
import com.wapp.core.models.SerieModel;
import com.wapp.core.models.WorkoutModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkoutRepository {

    public WorkoutModel getWorkoutById(Connection conn, String workoutId) throws SQLException {

        String query = "SELECT w.name, w.user_id, w.description, w.duration, w.start_datetime, w.end_datetime, " +
                               " ed.exercise_order, ed.id, ed.exercise_id, ed.description, " +
                               " e.name, e.muscle_group, es.id, es.exercise_done_id, es.repetitions, es.weight, es.series_order, es.description " +
                               " FROM WORKOUTS w " +
                               " INNER JOIN EXERCISES_DONE ed on w.id = ed.workout_id " +
                               " INNER JOIN EXERCISES e on ed.exercise_id = e.id and w.id = ed.workout_id " +
                               " INNER JOIN EXERCISES_SERIES es on ed.id = es.exercise_done_id " +
                               " WHERE w.id = ? " +
                               " ORDER BY ed.exercise_order, es.series_order;";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, workoutId);

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
                workoutModel.setId(workoutId);
                workoutModel.setName(res.getString("w.name"));
                workoutModel.setUserId(res.getString("w.user_id"));
                workoutModel.setDescription(res.getString("w.description"));
                workoutModel.setDuration(res.getString("w.duration"));
                workoutModel.setStartDate(res.getTimestamp("w.start_datetime"));
                workoutModel.setEndDate(res.getTimestamp("w.end_datetime"));
            }

            // Se o exercício não foi encontrado, cria um novo
            if (exerciseDto == null) {
                exerciseDto = new ExerciseDto();
                exerciseDto.setId(exerciseId);
                exerciseDto.setWorkoutId(workoutId);
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

        String query = "INSERT INTO WORKOUTS (id, user_id, name, description, duration, start_datetime, end_datetime) VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement workoutStm = conn.prepareStatement(query);
        workoutStm.setString(1, workoutModel.getId());
        workoutStm.setString(2, workoutModel.getUserId());
        workoutStm.setString(3, workoutModel.getName());
        workoutStm.setString(4, workoutModel.getDescription());
        workoutStm.setString(5, workoutModel.getDuration());
        workoutStm.setTimestamp(6, workoutModel.getStartDate());
        workoutStm.setTimestamp(7, workoutModel.getEndDate());

        workoutStm.executeUpdate();
    }

    public void doneExercise(Connection conn, ExerciseDto exercise) throws SQLException {
        String query = "INSERT INTO EXERCISES_DONE (id, workout_id, user_id, exercise_id, exercise_order, description) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement exerciseDoneStm = conn.prepareStatement(query);

        exerciseDoneStm.setString(1, exercise.getId());
        exerciseDoneStm.setString(2, exercise.getWorkoutId());
        exerciseDoneStm.setString(3, exercise.getUserId());
        exerciseDoneStm.setString(4, exercise.getExerciseId());
        exerciseDoneStm.setInt(5, exercise.getExerciseOrder());
        exerciseDoneStm.setString(6, exercise.getDescription());

        exerciseDoneStm.executeUpdate();
    }

    public void doneSerie(Connection conn, SerieModel serie) throws SQLException {

        String query = "INSERT INTO EXERCISES_SERIES (id, exercise_done_id, repetitions, weight, series_order, description) VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement exerciseSerieStm = conn.prepareStatement(query);

        exerciseSerieStm.setString(1, serie.getId());
        exerciseSerieStm.setString(2, serie.getExerciseDoneId());
        exerciseSerieStm.setInt(3, serie.getRepetitions());
        exerciseSerieStm.setInt(4, serie.getWeight());
        exerciseSerieStm.setInt(5, serie.getSerieOrder());
        exerciseSerieStm.setString(6, serie.getDescription());

        exerciseSerieStm.executeUpdate();
    }

    public List<WorkoutModel> getWorkoutsHistoryByUserId(Connection conn, String userId, Integer limit, Integer offset) throws SQLException {

        String query = "SELECT w.id, w.name, w.description, w.duration, w.start_datetime, w.end_datetime, " +
                               " ed.exercise_order, ed.id, ed.exercise_id, ed.description, e.name, e.muscle_group, " +
                               " es.id, es.exercise_done_id, es.repetitions, es.weight, es.series_order, es.description " +
                               " FROM WORKOUTS w " +
                               "         INNER JOIN EXERCISES_DONE ed on w.id = ed.workout_id " +
                               "         INNER JOIN EXERCISES e on ed.exercise_id = e.id and w.id = ed.workout_id " +
                               "         INNER JOIN EXERCISES_SERIES es on ed.id = es.exercise_done_id " +
                               " WHERE w.user_id = ? " +
                               " ORDER BY w.end_datetime DESC, w.start_datetime DESC, ed.exercise_order ASC, es.series_order ASC ";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, userId);

        ResultSet res = stm.executeQuery();

        List<WorkoutModel> workoutsList = new ArrayList<>();

        while (res.next()) {
            SerieModel serieModel = new SerieModel();
            ExerciseDto exerciseDto = null;
            WorkoutModel workoutModel = null;

            // Procura o exercício na lista de exercícios do workoutModel
            for (WorkoutModel w : workoutsList) {
                if (w.getId().equals(res.getString("w.id"))) {
                    workoutModel = w;
                    break;
                }
            }

            // se o id do workout ainda não foi setado, seta
            if (workoutModel == null) {
                workoutModel = new WorkoutModel();

                workoutModel.setId(res.getString("w.id"));
                workoutModel.setName(res.getString("w.name"));
                workoutModel.setUserId(userId);
                workoutModel.setDescription(res.getString("w.description"));
                workoutModel.setDuration(res.getString("w.duration"));
                workoutModel.setStartDate(res.getTimestamp("w.start_datetime"));
                workoutModel.setEndDate(res.getTimestamp("w.end_datetime"));

                workoutsList.add(workoutModel);
            }

            // Procura o exercício na lista de exercícios do workoutModel
            for (ExerciseDto e : workoutModel.getExercises()) {
                if (e.getId().equals(res.getString("ed.exercise_id"))) {
                    exerciseDto = e;
                    break;
                }
            }

            // Se o exercício não foi encontrado, cria um novo
            if (exerciseDto == null) {
                exerciseDto = new ExerciseDto();

                exerciseDto.setId(res.getString("ed.exercise_id"));
                exerciseDto.setWorkoutId(res.getString("w.id"));
                exerciseDto.setUserId(userId);
                exerciseDto.setExerciseId(res.getString("ed.exercise_id"));
                exerciseDto.setExerciseOrder(res.getInt("ed.exercise_order"));
                exerciseDto.setDescription(res.getString("ed.description"));
                exerciseDto.setName(res.getString("e.name"));
                exerciseDto.setMuscleGroup(res.getString("e.muscle_group"));

                workoutModel.getExercises().add(exerciseDto);
            }

            // Cria uma nova série e adiciona ao exercício
            serieModel.setId(res.getString("es.id"));
            serieModel.setExerciseDoneId(res.getString("es.exercise_done_id"));
            serieModel.setRepetitions(res.getInt("es.repetitions"));
            serieModel.setWeight(res.getInt("es.weight"));
            serieModel.setSerieOrder(res.getInt("es.series_order"));
            serieModel.setDescription(res.getString("es.description"));

            exerciseDto.getSeries().add(serieModel);
        }

        return workoutsList;

    }
}
