package com.gokyobistro.model;

/**
 * Order Model Class - Represents a food delivery order
 */
public class OrderModel {
    
    // Private fields - matches database columns
    private int orderId;
    private int userId;
    private String userName;        // For display
    private int menuId;
    private String menuItemName;    // For display
    private int quantity;
    private double totalPrice;
    private String deliveryAddress;
    private String status;          // pending, confirmed, delivered, cancelled
    private String orderDate;
    
    // No-argument constructor
    public OrderModel() {}
    
    // Parameterized constructor
    public OrderModel(int orderId, int userId, String userName, int menuId, 
                      String menuItemName, int quantity, double totalPrice, 
                      String deliveryAddress, String status, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.menuId = menuId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.orderDate = orderDate;
    }
    
    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public int getMenuId() {
        return menuId;
    }
    
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    
    public String getMenuItemName() {
        return menuItemName;
    }
    
    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}