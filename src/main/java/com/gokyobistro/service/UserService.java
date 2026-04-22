package com.gokyobistro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gokyobistro.config.DbConfig;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.util.PasswordUtil;

/**
 * User Service Class
 * 
 * Purpose: Handles user-related business logic and database operations
 * 
 * Lecture Reference: Week 6 - Service layer for business logic
 * DSA Instructions: Section 6 - Use PreparedStatement for queries
 * 
 * @author Your Name
 */
public class UserService {
    
    /**
     * Registers a new user (member) in the database
     * 
     * @param user UserModel object with registration data
     * @return true if registration successful, false otherwise
     */
    public boolean registerUser(UserModel user) {
        String sql = "INSERT INTO user (full_name, email, password, phone, address, role, created_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set parameters - Using PreparedStatement prevents SQL injection
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            
            // Encrypt password before storing
            String encryptedPassword = PasswordUtil.encryptPassword(user.getPassword());
            pstmt.setString(3, encryptedPassword);
            
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, "member");  // Default role is member
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Authenticates user login credentials
     * 
     * @param email User's email
     * @param password User's plain text password
     * @return UserModel if credentials valid, null otherwise
     */
    public UserModel login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Get stored encrypted password
                String storedPassword = rs.getString("password");
                
                // Verify password
                if (PasswordUtil.verifyPassword(password, storedPassword)) {
                    UserModel user = new UserModel();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRole(rs.getString("role"));
                    user.setCreatedDate(rs.getString("created_date"));
                    return user;
                }
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null; // Invalid credentials
    }
    
    /**
     * Checks if email already exists in database
     * 
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Gets user by ID
     * 
     * @param userId User ID
     * @return UserModel if found, null otherwise
     */
    public UserModel getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setCreatedDate(rs.getString("created_date"));
                return user;
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}