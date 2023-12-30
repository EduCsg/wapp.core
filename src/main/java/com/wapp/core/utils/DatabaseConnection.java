package com.wapp.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String env = System.getenv("ENV");
    private static final String url = System.getenv("DB_URL");
    private static final String user = System.getenv("DB_USER");
    private static final String password = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (env.equals("DEVELOPMENT")) {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/wapp_db", "root", "123");
        }

        return DriverManager.getConnection("jdbc:mysql://" + url, user, password);
    }
}
