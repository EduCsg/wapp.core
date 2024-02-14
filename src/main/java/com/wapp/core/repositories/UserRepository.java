package com.wapp.core.repositories;

import com.wapp.core.dto.UserDto;
import com.wapp.core.dto.UserMetadataDto;
import com.wapp.core.models.UserModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {


    public UserDto getUserById(Connection conn, String userId) throws SQLException {

        String query = "SELECT u.username, u.email, u.name, " +
                               " um.age, um.body_fat, um.gender, um.height, um.inserted_at, um.weight " +
                               " FROM USERS u " +
                               " LEFT JOIN USER_METADATA um " +
                               " ON u.id = um.user_id " +
                               " WHERE u.id = ? " +
                               " ORDER BY um.inserted_at DESC LIMIT 1; ";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, userId);

        UserDto userDto = new UserDto();
        ResultSet res = stm.executeQuery();

        if (! res.next()) return userDto;

        userDto.setWeight(res.getFloat("um.weight"));
        userDto.setHeight(res.getInt("um.height"));
        userDto.setUsername(res.getString("u.username"));
        userDto.setEmail(res.getString("u.email"));
        userDto.setName(res.getString("u.name"));
        userDto.setBodyFat(res.getFloat("um.body_fat"));
        userDto.setAge(res.getInt("um.age"));
        userDto.setGender(res.getString("um.gender"));
        userDto.setInsertedAt(res.getString("um.inserted_at"));

        return userDto;

    }

    public UserModel getUserByEmailOrUsername(Connection conn, String email, String username) throws SQLException {

        String query = "SELECT u.username, u.email FROM USERS u WHERE email = ? OR username = ?";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, email);
        stm.setString(2, username);

        UserModel userModel = new UserModel();
        ResultSet res = stm.executeQuery();

        if (! res.next()) return userModel;

        userModel.setUsername(res.getString("u.username"));
        userModel.setEmail(res.getString("u.email"));

        return userModel;
    }

    public void registerUser(Connection conn, UserModel userModel) throws SQLException {

        String query = "INSERT INTO USERS (id, username, email, name, password) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, userModel.getId());
        stm.setString(2, userModel.getUsername());
        stm.setString(3, userModel.getEmail());
        stm.setString(4, userModel.getName());
        stm.setString(5, userModel.getPassword());

        stm.executeUpdate();
    }

    public UserModel loginUser(Connection conn, String identification) throws SQLException {

        String query = "SELECT u.id, u.username, u.email, u.name, u.password FROM USERS u WHERE email = ? OR username = ?";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, identification);
        stm.setString(2, identification);

        ResultSet res = stm.executeQuery();

        UserModel userModel = new UserModel();
        if (! res.next()) return userModel;

        userModel.setId(res.getString("u.id"));
        userModel.setUsername(res.getString("u.username"));
        userModel.setEmail(res.getString("u.email"));
        userModel.setName(res.getString("u.name"));
        userModel.setPassword(res.getString("u.password"));

        return userModel;
    }

    public void insertUserMetadata(Connection conn, String userId, UserMetadataDto userMetadataDto) throws SQLException {

        String query = "INSERT INTO USER_METADATA (id, user_id, height, weight, body_fat, gender, age, inserted_at) " +
                               "    VALUES (?, ?, ?, ?, ?, ?, ?, ?); ";

        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, userMetadataDto.getId());
        stm.setString(2, userId);
        stm.setInt(3, userMetadataDto.getHeight());
        stm.setDouble(4, userMetadataDto.getWeight());
        stm.setDouble(5, userMetadataDto.getBodyFat());
        stm.setString(6, userMetadataDto.getGender());
        stm.setInt(7, userMetadataDto.getAge());
        stm.setString(8, userMetadataDto.getInsertedAt());

        stm.executeUpdate();

    }

}
