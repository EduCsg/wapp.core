package com.wapp.core.repository;

import com.wapp.core.models.WorkoutDto;
import com.wapp.core.models.WorkoutModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkoutRepository {

    public void createWorkout(Connection conn, WorkoutModel workout) {

        String query = "INSERT INTO WORKOUTS (id, user_id, name, description, date, duration, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, workout.getId());
            stm.setString(2, workout.getUserId());
            stm.setString(3, workout.getName());
            stm.setString(4, workout.getDescription());
            stm.setString(5, workout.getDate());
            stm.setString(6, workout.getDuration());
            stm.setTimestamp(7, Timestamp.valueOf(workout.getStartTime()));
            stm.setTimestamp(8, Timestamp.valueOf(workout.getEndTime()));

            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WorkoutDto getWorkoutById(Connection conn, String workoutId) {

        WorkoutDto workout = new WorkoutDto();
        String query = "SELECT name, description, date, duration, start_time, end_time FROM WORKOUTS WHERE id = ?";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, workoutId);
            stm.execute();

            ResultSet res = stm.getResultSet();

            while (res.next()) {
                workout.setName(res.getString("name"));
                workout.setDescription(res.getString("description"));
                workout.setDate(res.getString("date"));
                workout.setDuration(res.getString("duration"));
                workout.setStartTime(String.valueOf(res.getTimestamp("start_time").toLocalDateTime()));
                workout.setEndTime(String.valueOf(res.getTimestamp("end_time").toLocalDateTime()));
                workout.setId(workoutId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workout;
    }

    public List<WorkoutDto> getWorkoutByUserId(Connection conn, String userId) {

        List<WorkoutDto> workouts = new ArrayList<>();
        String query = "SELECT id, name, description, date, duration, start_time, end_time FROM WORKOUTS WHERE user_id = ?";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, userId);
            stm.execute();

            ResultSet res = stm.getResultSet();

            while (res.next()) {
                WorkoutDto workout = new WorkoutDto();

                workout.setName(res.getString("name"));
                workout.setDescription(res.getString("description"));
                workout.setDate(res.getString("date"));
                workout.setDuration(res.getString("duration"));
                workout.setStartTime(String.valueOf(res.getTimestamp("start_time").toLocalDateTime()));
                workout.setEndTime(String.valueOf(res.getTimestamp("end_time").toLocalDateTime()));
                workout.setId(res.getString("id"));

                workouts.add(workout);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workouts;
    }

}
