<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.ReservationModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Reservations - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">All Customer Reservations</h2>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer Name</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Table</th>
                        <th>Guests</th>
                        <th>Status</th>
                        <th>Booked On</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<ReservationModel> reservations = (List<ReservationModel>) request.getAttribute("reservations");
                        if (reservations != null && !reservations.isEmpty()) {
                            for (ReservationModel res : reservations) {
                    %>
                        <tr>
                            <td><%= res.getReservationId() %></td>
                            <td><%= res.getUserName() %></td>
                            <td><%= res.getReservationDate() %></td>
                            <td><%= res.getReservationTime() %></td>
                            <td><%= res.getTableNumber() %></td>
                            <td><%= res.getNumberOfGuests() %></td>
                            <td><%= res.getStatus() %></td>
                            <td><%= res.getCreatedDate() %></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="8" style="text-align: center;">No reservations found.</td>
                        </tr>
                    <%
                        }
                    %>
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