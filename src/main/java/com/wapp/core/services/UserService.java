package com.wapp.core.services;


import com.wapp.core.dto.UserDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.repositories.UserRepository;
import com.wapp.core.utils.DatabaseConnection;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {

    UserRepository userRepository = new UserRepository();

    public ResponseEntity<?> getUserById(String userId) {

        ResponseModel response = new ResponseModel();

        try {
            Connection conn = DatabaseConnection.getConnection();
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
        }

    }

}
