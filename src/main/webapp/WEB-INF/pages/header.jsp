<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gokyobistro.model.UserModel" %>

<%
    // Get session to check if user is logged in
    UserModel loggedInUser = (UserModel) session.getAttribute("loggedInUser");
%>

<div class="header">
    <div class="logo">
        <img src="${pageContext.request.contextPath}/images/logo/restaurant-logo.png" alt="Gokyo Bistro Logo" 
             onerror="this.src='https://via.placeholder.com/50x50?text=GB'">
        <h1>Gokyo Bistro</h1>
    </div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/menu">Menu</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/contact">Contact</a>
        
        <% if (loggedInUser != null) { %>
            <!-- User is logged in -->
            <% if ("admin".equals(loggedInUser.getRole())) { %>
                <a href="${pageContext.request.contextPath}/admin/dashboard">Admin Dashboard</a>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/member/dashboard">My Dashboard</a>
            <% } %>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        <% } else { %>
            <!-- User is not logged in -->
            <a href="${pageContext.request.contextPath}/login">Login</a>
            <a href="${pageContext.request.contextPath}/register">Register</a>
        <% } %>
    </div>
</div>