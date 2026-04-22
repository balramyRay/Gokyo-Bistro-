<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gokyobistro.model.UserModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member Dashboard - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <%
            UserModel user = (UserModel) session.getAttribute("loggedInUser");
        %>
        <div class="welcome-banner">
            <h2>Welcome, <%= user.getFullName() %>!</h2>
            <p>Manage your reservations, orders, and profile from here.</p>
        </div>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>📅 Book a Table</h3>
                <p>Reserve your table for an unforgettable dining experience.</p>
                <a href="${pageContext.request.contextPath}/member/bookTable" class="btn">Book Now</a>
            </div>
            
            <div class="dashboard-card">
                <h3>📋 My Reservations</h3>
                <p>View and manage your table bookings.</p>
                <a href="${pageContext.request.contextPath}/member/myReservations" class="btn">View Reservations</a>
            </div>
            
            <div class="dashboard-card">
                <h3>🍽️ Place Order</h3>
                <p>Order your favorite food for delivery.</p>
                <a href="${pageContext.request.contextPath}/menu" class="btn">Order Now</a>
            </div>
            
            <div class="dashboard-card">
                <h3>📦 My Orders</h3>
                <p>Track your food delivery orders.</p>
                <a href="${pageContext.request.contextPath}/member/myOrders" class="btn">View Orders</a>
            </div>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>