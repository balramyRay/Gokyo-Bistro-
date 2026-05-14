package com.gokyobistro.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Configuration Class
 */
public class DbConfig {
    
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/gokyo_bistro_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /*
     Returns a connection to the database
    */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load the MySQL JDBC driver
        Class.forName(DRIVER);
        
        // Create and return connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    /*
      Closes database connection to free resources
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /*
     Tests the database connection
    */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}