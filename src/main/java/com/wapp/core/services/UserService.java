package com.wapp.core.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wapp.core.dto.UserDto;
import com.wapp.core.dto.UserMetadataDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.models.UserModel;
import com.wapp.core.repositories.UserRepository;
import com.wapp.core.utils.*;

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
			UserModel userDto = userRepository.getUserById(conn, userId);

			if (ValidationUtils.isEmpty(userDto) || ValidationUtils.isEmpty(userDto.getEmail())) {
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
			if (conn != null)
				databaseConfig.closeConnection(conn);
		}

	}

	public ResponseEntity<?> registerUser(UserDto userDto) {
		System.out.println("   [LOG] RegisterUser  ->  email: " + userDto.getEmail());

		ResponseModel response = new ResponseModel();
		String userId = UUID.randomUUID().toString();
		Connection conn = null;

		try {
			conn = databaseConfig.getConnection();

			UserDto userExists = userRepository.getUserByEmailOrUsername(conn, userDto.getEmail(),
					userDto.getUsername());

			if (ValidationUtils.notEmpty(userExists.getUsername()) || ValidationUtils.notEmpty(userExists.getEmail())) {
				String email = userExists.getEmail();
				String username = userExists.getUsername();
				String message = "";

				if (userDto.getEmail().equals(email) && userDto.getUsername().equals(username))
					message = "Email e Nome de Usuário já cadastrados!";
				else if (userDto.getEmail().equals(email))
					message = "Email já cadastrado!";
				else if (userDto.getUsername().equals(username))
					message = "Nome de Usuário já cadastrado!";

				response.setMessage(message);
				response.setStatus("409");
				response.setSuccess(false);

				return ResponseEntity.status(409).body(response);
			}

			String userJwtToken = JwtUtils.generateToken(userId, userDto.getUsername(), userDto.getEmail(),
					userDto.getName());

			userDto.setId(userId);
			userDto.setPassword(CryptoUtil.hashPassword(userDto.getPassword()));
			userDto.setToken(userJwtToken);

			userRepository.registerUser(conn, userDto);

			response.setStatus("200");
			response.setSuccess(true);
			response.setMessage("Usuário cadastrado com sucesso!");
			response.setData(userDto);

			return ResponseEntity.ok(response);

		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus("500");
			response.setSuccess(false);
			response.setMessage("Erro: " + e.getMessage());

			return ResponseEntity.status(500).body(response);
		} finally {
			if (conn != null)
				databaseConfig.closeConnection(conn);
		}

	}

	public ResponseEntity<?> loginUser(UserDto userDto) {
		System.out.println("   [LOG] Login  ->  " + userDto.getIdentification());

		Connection conn = null;
		ResponseModel response = new ResponseModel();

		try {
			conn = databaseConfig.getConnection();

			UserDto userLogin = userRepository.loginUser(conn, userDto.getIdentification());

			if (ValidationUtils.isEmpty(userLogin.getUsername()) || ValidationUtils.isEmpty(userLogin.getEmail())) {
				response.setStatus("404");
				response.setSuccess(false);
				response.setMessage("Usuário não encontrado!");

				return ResponseEntity.status(404).body(response);
			}

			if (!CryptoUtil.checkPassword(userDto.getPassword(), userLogin.getPassword())) {
				response.setStatus("401");
				response.setSuccess(false);
				response.setMessage("A senha está incorreta!");

				return ResponseEntity.status(401).body(response);
			}

			userLogin.setToken(JwtUtils.generateToken(userLogin.getId(), userLogin.getUsername(), userLogin.getEmail(),
					userLogin.getName()));

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
			if (conn != null)
				databaseConfig.closeConnection(conn);
		}
	}

	public ResponseEntity<?> insertUserMetadata(String userId, UserMetadataDto userMetadataDto) {
		System.out.println("   [LOG] insertUserMetadata  ->  userId: " + userId);

		userMetadataDto.setInsertedAt(DateUtils.getCurrentTimestamp());
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
			if (conn != null)
				databaseConfig.closeConnection(conn);
		}
	}

}
