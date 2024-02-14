package com.wapp.core.services;


import com.wapp.core.dto.UserDto;
import com.wapp.core.dto.UserMetadataDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.UserModel;
import com.wapp.core.repositories.UserRepository;
import com.wapp.core.utils.CryptoUtil;
import com.wapp.core.utils.DatabaseConfig;
import com.wapp.core.utils.DateUtils;
import com.wapp.core.utils.JwtUtils;
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

            if (userDto.getUsername() == null) {
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
            response.setMessage("Erro: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

    public ResponseEntity<?> registerUser(UserModel userModel) {
        System.out.println("   [LOG] getUserById  ->  email: " + userModel.getEmail());

        ResponseModel response = new ResponseModel();
        String userId = UUID.randomUUID().toString();
        Connection conn = null;

        try {
            conn = databaseConfig.getConnection();

            UserModel userExists = userRepository.getUserByEmailOrUsername(conn, userModel.getEmail(), userModel.getUsername());

            if (userExists.getUsername() != null || userExists.getEmail() != null) {

                if (Objects.equals(userExists.getEmail(), userModel.getEmail()) && Objects.equals(userExists.getUsername(), userModel.getUsername()))
                    response.setMessage("Email e Nome de Usuário já cadastrados!");
                else if (Objects.equals(userExists.getEmail(), userModel.getEmail()))
                    response.setMessage("Email já cadastrado!");
                else if (Objects.equals(userExists.getUsername(), userModel.getUsername()))
                    response.setMessage("Nome de Usuário já cadastrado!");

                response.setStatus("400");
                response.setSuccess(false);

                return ResponseEntity.status(400).body(response);
            }

            userModel.setId(userId);
            userModel.setPassword(CryptoUtil.hashPassword(userModel.getPassword()));
            userModel.setToken(JwtUtils.generateToken(userId, userModel.getUsername(), userModel.getEmail(), userModel.getName()));

            userRepository.registerUser(conn, userModel);

            response.setStatus("200");
            response.setSuccess(true);
            response.setMessage("Usuário cadastrado com sucesso!");
            response.setData(userModel);

            return ResponseEntity.ok(response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus("500");
            response.setSuccess(false);
            response.setMessage("Erro: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }

    }

    public ResponseEntity<?> loginUser(UserModel userModel) {
        System.out.println("   [LOG] Login  ->  " + userModel.getIdentification());

        Connection conn = null;
        ResponseModel response = new ResponseModel();

        try {
            conn = databaseConfig.getConnection();

            UserModel userLogin = userRepository.loginUser(conn, userModel.getIdentification());

            if (userLogin.getUsername() == null || userLogin.getEmail() == null) {
                response.setStatus("404");
                response.setSuccess(false);
                response.setMessage("Usuário não encontrado!");

                return ResponseEntity.status(404).body(response);
            }

            if (! CryptoUtil.checkPassword(userModel.getPassword(), userLogin.getPassword())) {
                response.setStatus("401");
                response.setSuccess(false);
                response.setMessage("A senha está incorreta!");

                return ResponseEntity.status(401).body(response);
            }

            userLogin.setToken(JwtUtils.generateToken(userLogin.getId(), userLogin.getUsername(), userLogin.getEmail(), userLogin.getName()));

            response.setStatus("200");
            response.setSuccess(true);
            response.setMessage("Usuário logado com sucesso!");
            response.setData(userLogin);

            return ResponseEntity.ok(response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus("500");
            response.setSuccess(false);
            response.setMessage("Erro: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }
    }

    public ResponseEntity<?> insertUserMetadata(String userId, UserMetadataDto userMetadataDto) {
        System.out.println("   [LOG] insertUserMetadata  ->  userId: " + userId);

        userMetadataDto.setInsertedAt(DateUtils.getCurrentDateTimeAsString());
        userMetadataDto.setId(UUID.randomUUID().toString());

        Connection conn = null;
        ResponseModel response = new ResponseModel();

        try {
            conn = databaseConfig.getConnection();
            userRepository.insertUserMetadata(conn, userId, userMetadataDto);

            response.setStatus("200");
            response.setSuccess(true);
            response.setMessage("Dados do usuário inseridos com sucesso!");
            response.setData(userMetadataDto);

            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus("500");
            response.setSuccess(false);
            response.setMessage("Erro: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) databaseConfig.closeConnection(conn);
        }
    }

}
