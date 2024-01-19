package com.wapp.core.services;

import com.wapp.core.dto.RoutineDto;
import com.wapp.core.models.ResponseModel;
import com.wapp.core.repositories.RoutinesRepository;
import com.wapp.core.utils.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class RoutinesService {

    @Autowired
    DatabaseConfig databaseConfig;
    @Autowired
    RoutinesRepository routinesRepository;

    public ResponseEntity<?> getRoutineByUserId(String userId) {
        Connection conn = null;
        ResponseModel response = new ResponseModel();


        try {
            conn = databaseConfig.getConnection();

            List<RoutineDto> routineList = routinesRepository.getRoutineListByUserId(conn, userId);

            if (routineList.isEmpty()) {
                response.setStatus("404");
                response.setSuccess(false);
                response.setMessage("Nenhuma rotina encontrada para o usuário!");

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
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        } finally {
            if (conn != null) {
                databaseConfig.closeConnection(conn);
            }
        }
    }

}
