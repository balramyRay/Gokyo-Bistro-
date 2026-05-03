package com.gokyobistro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;        // For date/time in database
import java.time.LocalDateTime;    // For setting expiry time
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;              // For generating unique token
import java.security.NoSuchAlgorithmException;

import com.gokyobistro.config.DbConfig;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.util.PasswordUtil;

public class UserService {
    
    // ==================== REGISTER USER ====================
    // Purpose: Save new user to database
    public boolean registerUser(UserModel user) {
        String sql = "INSERT INTO user (full_name, email, password, phone, address, role, created_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            
            // Encrypt password before saving
            String encryptedPassword = PasswordUtil.encryptPassword(user.getPassword());
            pstmt.setString(3, encryptedPassword);
            
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, "member");  // Default role is member
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ==================== LOGIN USER ====================
    // Purpose: Verify user credentials and return user data
    public UserModel login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                
                // Check if entered password matches stored encrypted password
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
            
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;  // Login failed
    }
    
    // ==================== CHECK IF EMAIL EXISTS ====================
    // Purpose: Prevent duplicate registration
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Returns true if email found
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // ==================== GET USER BY ID ====================
    // Purpose: Fetch user details for profile page
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
    
    // ==================== UPDATE USER PROFILE ====================
    // Purpose: Update name, phone, address, and optionally password
    public boolean updateUserProfile(UserModel user) {
        // If password provided, update it too
        String sql;
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            sql = "UPDATE user SET full_name = ?, phone = ?, address = ?, password = ? WHERE user_id = ?";
        } else {
            sql = "UPDATE user SET full_name = ?, phone = ?, address = ? WHERE user_id = ?";
        }
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getAddress());
            
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String encryptedPassword = PasswordUtil.encryptPassword(user.getPassword());
                pstmt.setString(4, encryptedPassword);
                pstmt.setInt(5, user.getUserId());
            } else {
                pstmt.setInt(4, user.getUserId());
            }
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ==================== GET ALL MEMBERS (FOR ADMIN) ====================
    // Purpose: Show list of all registered members
    public List<UserModel> getAllMembers() {
        List<UserModel> membersList = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role = 'member' ORDER BY user_id DESC";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setCreatedDate(rs.getString("created_date"));
                membersList.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return membersList;
    }
    
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 }