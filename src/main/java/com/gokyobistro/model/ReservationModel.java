package com.gokyobistro.model;

/**
 * Reservation Model Class - Represents a table booking
 */
public class ReservationModel {
    
    // Private fields - matches database columns
    private int reservationId;
    private int userId;
    private String userName;        // For display (from JOIN with user table)
    private String reservationDate;
    private String reservationTime;
    private int tableNumber;
    private int numberOfGuests;
    private String status;          // confirmed, cancelled, completed
    private String createdDate;
    
    // No-argument constructor
    public ReservationModel() {}
    
    // Parameterized constructor
    public ReservationModel(int reservationId, int userId, String userName,
                            String reservationDate, String reservationTime, 
                            int tableNumber, int numberOfGuests, 
                            String status, String createdDate) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.userName = userName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.tableNumber = tableNumber;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.createdDate = createdDate;
    }
    
    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }
    
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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
    
    public String getReservationDate() {
        return reservationDate;
    }
    
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
    
    public String getReservationTime() {
        return reservationTime;
    }
    
    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }
    
    public int getTableNumber() {
        return tableNumber;
    }
    
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}