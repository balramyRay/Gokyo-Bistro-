package com.gokyobistro.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gokyobistro.config.DbConfig;
import com.gokyobistro.model.MenuModel;

/**
 * Handles menu-related business logic and database operations
 */
public class MenuService {
    
    /**
     * CREATE - Adds a new menu item to database
     */
    public boolean addMenuItem(MenuModel menu) {
        String sql = "INSERT INTO menu (item_name, category, description, price, "
                   + "availability, created_date) VALUES (?, ?, ?, ?, ?, CURDATE())";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, menu.getItemName());
            pstmt.setString(2, menu.getCategory());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setString(5, menu.getAvailability());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * READ - Gets all menu items with sorting
     */
    public List<MenuModel> getAllMenuItems(String sortBy) {
        List<MenuModel> menuList = new ArrayList<>();
        String sql = "SELECT * FROM menu " + getSortQuery(sortBy);
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                MenuModel menu = new MenuModel();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setItemName(rs.getString("item_name"));
                menu.setCategory(rs.getString("category"));
                menu.setDescription(rs.getString("description"));
                menu.setPrice(rs.getDouble("price"));
                menu.setAvailability(rs.getString("availability"));
                menu.setCreatedDate(rs.getString("created_date"));
                menuList.add(menu);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return menuList;
    }
    
    /**
     * READ - Gets all menu items (without sorting - for backward compatibility)
     */
    public List<MenuModel> getAllMenuItems() {
        return getAllMenuItems("default");
    }
    
    /**
     * READ - Gets menu items by category
     */
    public List<MenuModel> getMenuItemsByCategory(String category) {
        List<MenuModel> menuList = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE category = ? AND availability = 'Yes'";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MenuModel menu = new MenuModel();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setItemName(rs.getString("item_name"));
                menu.setCategory(rs.getString("category"));
                menu.setDescription(rs.getString("description"));
                menu.setPrice(rs.getDouble("price"));
                menu.setAvailability(rs.getString("availability"));
                menu.setCreatedDate(rs.getString("created_date"));
                menuList.add(menu);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return menuList;
    }
    
    /**
     * READ - Gets a single menu item by ID
     */
    public MenuModel getMenuItemById(int menuId) {
        String sql = "SELECT * FROM menu WHERE menu_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, menuId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                MenuModel menu = new MenuModel();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setItemName(rs.getString("item_name"));
                menu.setCategory(rs.getString("category"));
                menu.setDescription(rs.getString("description"));
                menu.setPrice(rs.getDouble("price"));
                menu.setAvailability(rs.getString("availability"));
                menu.setCreatedDate(rs.getString("created_date"));
                return menu;
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * UPDATE - Updates an existing menu item
     */
    public boolean updateMenuItem(MenuModel menu) {
        String sql = "UPDATE menu SET item_name = ?, category = ?, description = ?, "
                   + "price = ?, availability = ? WHERE menu_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, menu.getItemName());
            pstmt.setString(2, menu.getCategory());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setString(5, menu.getAvailability());
            pstmt.setInt(6, menu.getMenuId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * DELETE - Removes a menu item from database
     */
    public boolean deleteMenuItem(int menuId) {
        String sql = "DELETE FROM menu WHERE menu_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, menuId);
            return pstmt.executeUpdate() > 0;
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Search menu items by name or category with sorting
     */
    public List<MenuModel> searchMenuItems(String keyword, String sortBy) {
        List<MenuModel> menuList = new ArrayList<>();
        String sql = "SELECT * FROM menu WHERE item_name LIKE ? OR category LIKE ? " + getSortQuery(sortBy);
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MenuModel menu = new MenuModel();
                menu.setMenuId(rs.getInt("menu_id"));
                menu.setItemName(rs.getString("item_name"));
                menu.setCategory(rs.getString("category"));
                menu.setDescription(rs.getString("description"));
                menu.setPrice(rs.getDouble("price"));
                menu.setAvailability(rs.getString("availability"));
                menu.setCreatedDate(rs.getString("created_date"));
                menuList.add(menu);
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return menuList;
    }
    
    /**
     * Search menu items by name or category (without sorting - for backward compatibility)
     */
    public List<MenuModel> searchMenuItems(String keyword) {
        return searchMenuItems(keyword, "default");
    }
    
    /**
     * Helper method to get ORDER BY clause based on sort parameter
     */
    private String getSortQuery(String sortBy) {
        if (sortBy == null || sortBy.equals("default")) {
            return "ORDER BY menu_id DESC";
        } else if (sortBy.equals("price_asc")) {
            return "ORDER BY price ASC";
        } else if (sortBy.equals("price_desc")) {
            return "ORDER BY price DESC";
        } else if (sortBy.equals("name_asc")) {
            return "ORDER BY item_name ASC";
        }
        return "ORDER BY menu_id DESC";
    }
}