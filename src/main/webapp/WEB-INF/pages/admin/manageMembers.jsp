<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.UserModel" %>
<%
    List<UserModel> membersList = (List<UserModel>) request.getAttribute("membersList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Members - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .table-container {
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #2c3e50;
            color: white;
        }
        .delete-btn {
            background-color: #e74c3c;
            color: white;
            padding: 5px 10px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 12px;
        }
        .delete-btn:hover {
            background-color: #c0392b;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">Manage Members</h2>
        
        <% if (request.getParameter("success") != null && request.getParameter("success").equals("deleted")) { %>
            <div class="success-message">Member deleted successfully!</div>
        <% } %>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Registered Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (membersList != null && !membersList.isEmpty()) { 
                        for (UserModel member : membersList) { %>
                        <tr>
                            <td><%= member.getUserId() %></td>
                            <td><%= member.getFullName() %></td>
                            <td><%= member.getEmail() %></td>
                            <td><%= member.getPhone() != null ? member.getPhone() : "-" %></td>
                            <td><%= member.getAddress() != null ? member.getAddress() : "-" %></td>
                            <td><%= member.getCreatedDate() != null ? member.getCreatedDate() : "-" %></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/manageMembers?action=delete&id=<%= member.getUserId() %>" 
                                   class="delete-btn" onclick="return confirm('Are you sure you want to delete this member?')">Delete</a>
                            </td>
                        </tr>
                    <% } 
                    } else { %>
                        <tr>
                            <td colspan="7" style="text-align: center;">No members found.</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
        
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
        </p>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>