<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gokyobistro.model.UserModel" %>
<%
    UserModel user = (UserModel) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/member/dashboard");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .profile-container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .btn {
            background-color: #e67e22;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #d35400;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <div class="profile-container">
            <h2 style="text-align: center; margin-bottom: 30px;">My Profile</h2>
            
            <% if (request.getParameter("success") != null) { %>
                <div class="success-message">Profile updated successfully!</div>
            <% } %>
            <% if (request.getParameter("error") != null) { %>
                <div class="error-message">Update failed. Please try again.</div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/member/profile" method="post">
                <input type="hidden" name="action" value="update">
                
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="fullName" value="<%= user.getFullName() %>" required>
                </div>
                
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" value="<%= user.getEmail() %>" disabled style="background-color: #f0f0f0;">
                </div>
                
                <div class="form-group">
                    <label>Phone</label>
                    <input type="text" name="phone" value="<%= user.getPhone() != null ? user.getPhone() : "" %>">
                </div>
                
                <div class="form-group">
                    <label>Address</label>
                    <input type="text" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                </div>
                
                <div class="form-group">
                    <label>New Password (leave blank to keep current)</label>
                    <input type="password" name="password" placeholder="Enter new password">
                </div>
                
                <button type="submit" class="btn">Update Profile</button>
                <a href="${pageContext.request.contextPath}/member/dashboard" class="btn" style="background-color: #7f8c8d; text-decoration: none; margin-left: 10px;">Cancel</a>
            </form>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>