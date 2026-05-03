package com.gokyobistro.model;

/**
 * Menu Model Class - Represents a food item in the menu 
 * @author Your Name
 */
public class MenuModel {
    
    // Private fields - matches database columns
    private int menuId;
    private String itemName;
    private String category;
    private String description;
    private double price;
    private String availability;
    private String createdDate;
    
    // No-argument constructor
    public MenuModel() {}
    
    // Parameterized constructor
    public MenuModel(int menuId, String itemName, String category, String description, 
                     double price, String availability, String createdDate) 
    {
        this.menuId = menuId;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.createdDate = createdDate;
    }
    
    // Getters and Setters
    public int getMenuId() {
        return menuId;
    }
    
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
        
    public String getAvailability() {
        return availability;
    }
    
    public void setAvailability(String availability) {
        this.availability = availability;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}