package com.wapp.core.services;


import com.wapp.core.dto.UserDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.UserModel;
import com.wapp.core.repositories.UserRepository;
import com.wapp.core.utils.CryptoUtil;
import com.wapp.core.utils.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    DatabaseConfig databaseConfig;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> getUserById(String userId) {
        System.out.println("   [LOG] getUserById  ->  userId: " + userId);

        ResponseModel response = new ResponseModel();
        Connection conn = null;

        try {
            conn = databaseConfig.getConnection();
            UserDto userDto = userRepository.getUserById(conn, userId);

            if (userDto.getId() == null) {
                response.setStatus("404");
                response.setSuccess(false);
                response.setMessage("Usuário não encontrado");

                return ResponseEntity.status(404).body(response);
            }

            response.setStatus("200");
            response.setSuccess(true);
            response.setMessage("Usuário encontrado com sucesso");
            response.setData(userDto);

            return ResponseEntity.ok(response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus("500");
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

    public ResponseEntity<?> registerUser(UserModel userModel) {
        System.out.println("   [LOG] getUserById  ->  userModel: " + userModel.toString());

        ResponseModel response = new ResponseModel();
        String userId = UUID.randomUUID().toString();
        Connection conn = null;

        try {
            conn = databaseConfig.getConnection();

            UserModel userExists = userRepository.getUserByEmailOrUsername(conn, userModel.getEmail(), userModel.getUsername());

            if (userExists.getUsername() != null || userExists.getEmail() != null) {

                if (Objects.equals(userExists.getEmail(), userModel.getEmail()) && Objects.equals(userExists.getUsername(), userModel.getUsername()))
                    response.setMessage("Email e username já cadastrados");
                else if (Objects.equals(userExists.getEmail(), userModel.getEmail()))
                    response.setMessage("Email já cadastrado");
                else if (Objects.equals(userExists.getUsername(), userModel.getUsername()))
                    response.setMessage("Username já cadastrado");

                response.setStatus("400");
                response.setSuccess(false);

                return ResponseEntity.status(400).body(response);
            }

            userModel.setId(userId);
            userModel.setPassword(CryptoUtil.hashPassword(userModel.getPassword()));

            userRepository.registerUser(conn, userModel);

            response.setStatus("200");
            response.setSuccess(true);
            response.setMessage("Usuário cadastrado com sucesso");
            response.setData(userId);

            return ResponseEntity.ok(response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus("500");
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

}
