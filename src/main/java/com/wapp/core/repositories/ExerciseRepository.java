package com.wapp.core.repositories;

import com.wapp.core.models.ExerciseModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExerciseRepository {

    public List<ExerciseModel> getExerciseList(Connection conn, String userId) throws SQLException {

        String query = "SELECT e.id, e.name, e.muscle_group " +
                               " FROM EXERCISES e " +
                               " WHERE e.inserted_by = ? " +
                               " OR e.inserted_by IS NULL;";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, userId);

        ResultSet res = stm.executeQuery();
        List<ExerciseModel> exerciseList = new ArrayList<>();

        while (res.next()) {
            ExerciseModel exerciseModel = new ExerciseModel();

            exerciseModel.setId(stm.getResultSet().getString("e.id"));
            exerciseModel.setName(stm.getResultSet().getString("e.name"));
            exerciseModel.setMuscleGroup(stm.getResultSet().getString("e.muscle_group"));

            exerciseList.add(exerciseModel);
        }

        return exerciseList;

    }

    public int createExercise(Connection conn, String userId, ExerciseModel exerciseModel) throws SQLException {

        String query = " INSERT INTO EXERCISES (id, name, muscle_group, inserted_by) " +
                               " VALUES (?, ?, ?, ?); ";

        PreparedStatement stm = conn.prepareStatement(query);

        stm.setString(1, exerciseModel.getId());
        stm.setString(2, exerciseModel.getName());
        stm.setString(3, exerciseModel.getMuscleGroup());
        stm.setString(4, userId);

        return stm.executeUpdate();

    }
}
