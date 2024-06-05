package com.wapp.core.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wapp.core.dto.RoutineDto;
import com.wapp.core.models.ExerciseModel;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.repositories.RoutinesRepository;
import com.wapp.core.utils.DatabaseConfig;
import com.wapp.core.utils.ValidationUtils;

@Service
public class RoutinesService {

	@Autowired
	DatabaseConfig databaseConfig;
	@Autowired
	RoutinesRepository routinesRepository;

	public ResponseEntity<?> getRoutineByUserId(String userId) {
		System.out.println("   [LOG] getRoutineByUserId  ->  userId: " + userId);

		Connection conn = null;
		ResponseModel response = new ResponseModel();

		try {
			conn = databaseConfig.getConnection();

			List<RoutineDto> routineList = routinesRepository.getRoutineListByUserId(conn, userId);

			if (ValidationUtils.isEmpty(routineList)) {
				response.setStatus("404");
				response.setSuccess(false);
				response.setMessage("Nenhuma rotina encontrada!");

				return ResponseEntity.status(404).body(response);
			}

			response.setData(routineList);
			response.setStatus("200");
			response.setSuccess(true);
			response.setMessage(routineList.size() + " rotinas encontradas com sucesso!");

			return ResponseEntity.status(200).body(response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus("500");
			response.setSuccess(false);
			response.setMessage("Erro: " + e.getMessage());

			return ResponseEntity.status(500).body(response);
		} finally {
			if (conn != null) {
				databaseConfig.closeConnection(conn);
			}
		}
	}

	public ResponseEntity<?> getRoutineById(String routineId) {
		System.out.println("   [LOG] getRoutineById  ->  routineId: " + routineId);

		Connection conn = null;
		ResponseModel response = new ResponseModel();

		try {
			conn = databaseConfig.getConnection();

			RoutineDto routine = routinesRepository.getRoutineById(conn, routineId);

			if (ValidationUtils.isEmpty(routine)) {
				response.setStatus("404");
				response.setSuccess(false);
				response.setMessage("Nenhuma rotina encontrada!");

				return ResponseEntity.status(404).body(response);
			}

			response.setData(routine);
			response.setStatus("200");
			response.setSuccess(true);
			response.setMessage("Rotina encontrada com sucesso!");

			return ResponseEntity.status(200).body(response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus("500");
			response.setSuccess(false);
			response.setMessage("Erro: " + e.getMessage());

			return ResponseEntity.status(500).body(response);
		} finally {
			if (conn != null) {
				databaseConfig.closeConnection(conn);
			}
		}
	}

	public ResponseEntity<?> postRoutine(String userId, RoutineDto routineDto) {
		String routineId = UUID.randomUUID().toString();
		routineDto.setId(routineId);

		System.out.println("   [LOG] postRoutine -> name/id: " + routineDto.getName() + " / " + routineDto.getId());

		Connection conn = null;
		ResponseModel response = new ResponseModel();

		try {
			conn = databaseConfig.getConnection();
			conn.setAutoCommit(false);

			routinesRepository.postRoutine(conn, userId, routineDto);

			for (ExerciseModel exercise : routineDto.getExercises()) {
				routinesRepository.linkRoutineToExercise(conn, routineDto.getId(), exercise);
			}

			conn.commit();

			response.setData(routineDto);
			response.setMessage("Rotina " + routineDto.getName() + " criada com sucesso!");
			response.setStatus("200");
			response.setSuccess(true);

			return ResponseEntity.status(200).body(response);

		} catch (SQLException e) {
			e.printStackTrace();

			if (conn != null) {
				databaseConfig.rollback(conn);
			}

			response.setMessage("Erro: " + e.getMessage());
			response.setSuccess(false);
			response.setStatus("500");

			return ResponseEntity.status(500).body(response);
		} finally {
			if (conn != null) {
				databaseConfig.enableAutoCommit(conn);
				databaseConfig.closeConnection(conn);
			}
		}
	}

	public ResponseEntity<?> updateRoutine(String routineId, RoutineDto routineDto) {
		System.out.println("   [LOG] updateRoutine -> name/id: " + routineDto.getName() + " / " + routineDto.getId());

		Connection conn = null;
		ResponseModel response = new ResponseModel();
		routineDto.setId(routineId);

		try {
			conn = databaseConfig.getConnection();
			conn.setAutoCommit(false);

			List<String> exercisesList = routineDto.getExercises().stream().map(ExerciseModel::getId)
					.collect(Collectors.toList());

			routinesRepository.deleteRoutineExercises(conn, routineId, exercisesList);
			routinesRepository.insertOrUpdateRoutine(conn, routineId, routineDto);

			response.setData(routineDto);
			response.setMessage("Rotina " + routineDto.getName() + " atualizada com sucesso!");
			response.setStatus("200");
			response.setSuccess(true);

			return ResponseEntity.status(200).body(response);

		} catch (Exception e) {
			e.printStackTrace();

			if (conn != null) {
				databaseConfig.rollback(conn);
			}

			response.setMessage("Erro: " + e.getMessage());
			response.setSuccess(false);
			response.setStatus("500");

			return ResponseEntity.status(500).body(response);
		} finally {
			if (conn != null) {
				databaseConfig.enableAutoCommit(conn);
				databaseConfig.closeConnection(conn);
			}
		}
	}

	public ResponseEntity<?> deleteRoutineById(String routineId) {
		System.out.println("   [LOG] deleteRoutine -> routineId: " + routineId);

		Connection conn = null;
		ResponseModel response = new ResponseModel();

		try {
			conn = databaseConfig.getConnection();
			conn.setAutoCommit(false);

			int affectedRows = 0;

			affectedRows += routinesRepository.deleteRoutineExercises(conn, routineId, null);
			affectedRows += routinesRepository.deleteRoutineById(conn, routineId);

			conn.commit();

			if (affectedRows == 0) {
				response.setMessage("Nenhuma rotina encontrada!");
				response.setStatus("404");
				response.setSuccess(false);

				return ResponseEntity.status(404).body(response);
			}

			response.setMessage("Rotina deletada com sucesso!");
			response.setStatus("200");
			response.setSuccess(true);

			return ResponseEntity.status(200).body(response);

		} catch (SQLException e) {
			e.printStackTrace();

			if (conn != null)
				databaseConfig.rollback(conn);

			response.setMessage("Erro: " + e.getMessage());
			response.setSuccess(false);
			response.setStatus("500");

			return ResponseEntity.status(500).body(response);
		} finally {
			if (conn != null) {
				databaseConfig.enableAutoCommit(conn);
				databaseConfig.closeConnection(conn);
			}
		}
	}

}
