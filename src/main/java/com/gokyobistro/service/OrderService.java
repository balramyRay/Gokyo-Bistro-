package com.gokyobistro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gokyobistro.config.DbConfig;
import com.gokyobistro.model.OrderModel;

/**
 Handles order related business logic and database operations

 */
public class OrderService {
    
    /**
     * CREATE - Places a new food order
     * 
     * @param order OrderModel with order details
     * @return true if successful, false otherwise
     */
    public boolean placeOrder(OrderModel order) {
        String sql = "INSERT INTO orders (user_id, menu_id, quantity, total_price, "
                   + "delivery_address, status, order_date) "
                   + "VALUES (?, ?, ?, ?, ?, 'pending', CURDATE())";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getMenuId());
            pstmt.setInt(3, order.getQuantity());
            pstmt.setDouble(4, order.getTotalPrice());
            pstmt.setString(5, order.getDeliveryAddress());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * READ - Gets all orders for a specific user
     * userId User ID
     *  List of user's orders
     */
    public List<OrderModel> getOrdersByUser(int userId) {
        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT o.*, u.full_name as user_name, m.item_name as menu_item_name "
                   + "FROM orders o "
                   + "JOIN user u ON o.user_id = u.user_id "
                   + "JOIN menu m ON o.menu_id = m.menu_id "
                   + "WHERE o.user_id = ? ORDER BY o.order_date DESC";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setUserName(rs.getString("user_name"));
                order.setMenuId(rs.getInt("menu_id"));
                order.setMenuItemName(rs.getString("menu_item_name"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getString("order_date"));
                orders.add(order);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * READ Gets all orders (for Admin)
     * 
     * List of all orders
     */
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        String sql = "SELECT o.*, u.full_name as user_name, m.item_name as menu_item_name "
                   + "FROM orders o "
                   + "JOIN user u ON o.user_id = u.user_id "
                   + "JOIN menu m ON o.menu_id = m.menu_id "
                   + "ORDER BY o.order_date DESC";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setUserName(rs.getString("user_name"));
                order.setMenuId(rs.getInt("menu_id"));
                order.setMenuItemName(rs.getString("menu_item_name"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getString("order_date"));
                orders.add(order);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * UPDATE - Updates order status
      orderId Order ID
      status New status (confirmed, delivered, cancelled)
      true if successful, false otherwise
     */
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}