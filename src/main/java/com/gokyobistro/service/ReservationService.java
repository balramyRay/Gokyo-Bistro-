package com.gokyobistro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gokyobistro.config.DbConfig;
import com.gokyobistro.model.ReservationModel;

/**
 Handles reservation related business logic and database operations

 */
public class ReservationService {
    
    /**
     * CREATE - Books a new table reservation
     reservation ReservationModel with booking details
     true if successful, false otherwise
     */
    public boolean bookTable(ReservationModel reservation) {
        String sql = "INSERT INTO reservation (user_id, reservation_date, reservation_time, "
                   + "table_number, number_of_guests, status, created_date) "
                   + "VALUES (?, ?, ?, ?, ?, 'confirmed', CURDATE())";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reservation.getUserId());
            pstmt.setString(2, reservation.getReservationDate());
            pstmt.setString(3, reservation.getReservationTime());
            pstmt.setInt(4, reservation.getTableNumber());
            pstmt.setInt(5, reservation.getNumberOfGuests());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*
      READ - Gets all reservations for a specific user
    
     */
    public List<ReservationModel> getReservationsByUser(int userId) {
        List<ReservationModel> reservations = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name as user_name FROM reservation r "
                   + "JOIN user u ON r.user_id = u.user_id "
                   + "WHERE r.user_id = ? ORDER BY r.reservation_date DESC";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ReservationModel reservation = new ReservationModel();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setUserId(rs.getInt("user_id"));
                reservation.setUserName(rs.getString("user_name"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setReservationTime(rs.getString("reservation_time"));
                reservation.setTableNumber(rs.getInt("table_number"));
                reservation.setNumberOfGuests(rs.getInt("number_of_guests"));
                reservation.setStatus(rs.getString("status"));
                reservation.setCreatedDate(rs.getString("created_date"));
                reservations.add(reservation);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return reservations;
    }
    
    /**
     * READ  Gets all reservations (for Admin)
     List of all reservations
     */
    public List<ReservationModel> getAllReservations() {
        List<ReservationModel> reservations = new ArrayList<>();
        String sql = "SELECT r.*, u.full_name as user_name FROM reservation r "
                   + "JOIN user u ON r.user_id = u.user_id "
                   + "ORDER BY r.reservation_date DESC";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                ReservationModel reservation = new ReservationModel();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setUserId(rs.getInt("user_id"));
                reservation.setUserName(rs.getString("user_name"));
                reservation.setReservationDate(rs.getString("reservation_date"));
                reservation.setReservationTime(rs.getString("reservation_time"));
                reservation.setTableNumber(rs.getInt("table_number"));
                reservation.setNumberOfGuests(rs.getInt("number_of_guests"));
                reservation.setStatus(rs.getString("status"));
                reservation.setCreatedDate(rs.getString("created_date"));
                reservations.add(reservation);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return reservations;
    }
    
    /**
     * UPDATE - Cancels a reservation
      reservationId Reservation ID to cancel
     true if successful, false otherwise
     */
    public boolean cancelReservation(int reservationId) {
        String sql = "UPDATE reservation SET status = 'cancelled' WHERE reservation_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reservationId);
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Checks if table is available at given date and time
      tableNumber Table number
      date Reservation date
      time Reservation time
      true if available, false otherwise
     */
    public boolean isTableAvailable(int tableNumber, String date, String time) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE table_number = ? "
                   + "AND reservation_date = ? AND reservation_time = ? "
                   + "AND status = 'confirmed'";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tableNumber);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return true;
    }
}