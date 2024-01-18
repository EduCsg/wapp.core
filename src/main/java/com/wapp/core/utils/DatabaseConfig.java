package com.wapp.core.utils;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    private final String env = System.getenv("ENV");
    private final String url = System.getenv("DB_URL");
    private final String user = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASSWORD");

    public Connection getConnection() throws SQLException {
        if (env.equals("DEVELOPMENT")) {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/wapp_db", "root", "123");
        }

        return DriverManager.getConnection("jdbc:mysql://" + url, user, password);
    }

    public void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("   [LOG] Error closing connection: " + ex.getMessage());
        }
    }
}
