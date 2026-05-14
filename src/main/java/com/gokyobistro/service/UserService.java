package com.gokyobistro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.security.NoSuchAlgorithmException;

import com.gokyobistro.config.DbConfig;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.util.PasswordUtil;

public class UserService {
    
    // REGISTER USER
    public boolean registerUser(UserModel user) {
        String sql = "INSERT INTO user (full_name, email, password, phone, address, role, created_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            String encryptedPassword = PasswordUtil.encryptPassword(user.getPassword());
            pstmt.setString(3, encryptedPassword);
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, "member");
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //LOGIN USER WITH ACCOUNT LOCK 
    public UserModel login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Check if account is locked
                Timestamp lockTime = rs.getTimestamp("lock_time");
                if (lockTime != null) {
                    long diff = System.currentTimeMillis() - lockTime.getTime();
                    if (diff < 5 * 60 * 1000) { // Less than 5 minutes
                        System.out.println("Account locked. Please try after 5 minutes.");
                        return null;
                    } else {
                        // Reset lock after 5 minutes
                        resetLoginAttempts(email);
                    }
                }
                
                String storedPassword = rs.getString("password");
                
                if (PasswordUtil.verifyPassword(password, storedPassword)) {
                    // Login success - reset attempts
                    resetLoginAttempts(email);
                    
                    UserModel user = new UserModel();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRole(rs.getString("role"));
                    user.setCreatedDate(rs.getString("created_date"));
                    return user;
                } else {
                    // Wrong password - increment attempts
                    incrementLoginAttempts(email);
                    return null;
                }
            }
            
        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // RESET LOGIN ATTEMPTS 
    private void resetLoginAttempts(String email) {
        String sql = "UPDATE user SET login_attempts = 0, lock_time = NULL WHERE email = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    // COUNT LOGIN ATTEMPTS
    private void incrementLoginAttempts(String email) {
        String sql = "UPDATE user SET login_attempts = login_attempts + 1 WHERE email = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
            
            // Check if attempts reached 3
            String selectSql = "SELECT login_attempts FROM user WHERE email = ?";
            PreparedStatement ps2 = conn.prepareStatement(selectSql);
            ps2.setString(1, email);
            ResultSet rs = ps2.executeQuery();
            if (rs.next() && rs.getInt(1) >= 3) {
                lockAccount(email);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    // LOCK ACCOUNT 
    private void lockAccount(String email) {
        String sql = "UPDATE user SET lock_time = NOW() WHERE email = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    //CHECK IF ACCOUNT IS LOCKED 
    public boolean isAccountLocked(String email) {
        String sql = "SELECT lock_time FROM user WHERE email = ? AND lock_time IS NOT NULL";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp lockTime = rs.getTimestamp("lock_time");
                long diff = System.currentTimeMillis() - lockTime.getTime();
                return diff < 5 * 60 * 1000;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // CHECK IF EMAIL EXISTS 
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
    
    // GET USER BY ID
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
    
    // UPDATE USER PROFILE 
    public boolean updateUserProfile(UserModel user) {
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
    
    // GET ALL MEMBERS
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
    
    // UPDATE PASSWORD BY EMAIL 
    public boolean updatePasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String encrypted = PasswordUtil.encryptPassword(newPassword);
            ps.setString(1, encrypted);
            ps.setString(2, email);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteMemberWithAllData(int userId) {
        try (Connection conn = DbConfig.getConnection()) {
            conn.setAutoCommit(false);
            
            // Delete orders
            String sql1 = "DELETE FROM orders WHERE user_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, userId);
            ps1.executeUpdate();
            ps1.close();
            
            // Delete reservations
            String sql2 = "DELETE FROM reservation WHERE user_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, userId);
            ps2.executeUpdate();
            ps2.close();
            
            // Delete user
            String sql3 = "DELETE FROM user WHERE user_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, userId);
            int result = ps3.executeUpdate();
            
            conn.commit();
            return result > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}