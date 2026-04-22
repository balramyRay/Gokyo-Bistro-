<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gokyobistro.model.UserModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <%
            UserModel user = (UserModel) session.getAttribute("loggedInUser");
        %>
        <div class="welcome-banner">
            <h2>Admin Dashboard</h2>
            <p>Welcome, <%= user.getFullName() %>! Manage your restaurant from here.</p>
        </div>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>📋 Manage Menu</h3>
                <p>Add, edit, or delete menu items.</p>
                <a href="${pageContext.request.contextPath}/admin/manageMenu" class="btn">Manage Menu</a>
            </div>
            
            <div class="dashboard-card">
                <h3>📅 All Reservations</h3>
                <p>View all customer table bookings.</p>
                <a href="${pageContext.request.contextPath}/admin/reservations" class="btn">View Reservations</a>
            </div>
            
            <div class="dashboard-card">
                <h3>📦 All Orders</h3>
                <p>View and manage customer orders.</p>
                <a href="${pageContext.request.contextPath}/admin/orders" class="btn">View Orders</a>
            </div>
            
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>