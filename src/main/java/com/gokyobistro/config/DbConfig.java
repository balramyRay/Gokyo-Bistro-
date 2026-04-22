package com.gokyobistro.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Configuration Class
 * 
 * Purpose: Establishes connection between Java application and MySQL database
 * 
 * Lecture Reference: Week 6 - Implementing JDBC API
 * DSA Instructions: Section 6 - Use a config class (e.g., DBConfig)
 * 
 * @author Your Name
 */
public class DbConfig {
    
    // Database connection parameters
    // These MUST match your XAMPP/MySQL settings
    private static final String URL = "jdbc:mysql://localhost:3306/gokyo_bistro_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Returns a connection to the database
     * 
     * @return Connection object
     * @throws ClassNotFoundException if MySQL driver not found
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Step 1: Load the MySQL JDBC driver
        Class.forName(DRIVER);
        
        // Step 2: Create and return connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    /**
     * Closes database connection to free resources
     * 
     * @param conn Connection to close
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
    
    /**
     * Tests the database connection
     * 
     * @return true if connection successful, false otherwise
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