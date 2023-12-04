package com.wapp.core.repository;

import com.wapp.core.models.WorkoutDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class WorkoutRepository {

    public void createWorkout(Connection conn, WorkoutDto workout) {

        String query = "INSERT INTO WORKOUTS (id, name, description, date, duration, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, workout.getId());
            stm.setString(2, workout.getName());
            stm.setString(3, workout.getDescription());
            stm.setString(4, workout.getDate());
            stm.setString(5, workout.getDuration());
            stm.setTimestamp(6, Timestamp.valueOf(workout.getStartTime()));
            stm.setTimestamp(7, Timestamp.valueOf(workout.getEndTime()));

            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
