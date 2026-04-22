<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.ReservationModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Reservations - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">My Reservations</h2>
        
        <%
            String success = request.getParameter("success");
            if (success != null && success.equals("booked")) {
        %>
            <div class="success-message">Table booked successfully!</div>
        <%
            } else if (success != null && success.equals("cancelled")) {
        %>
            <div class="success-message">Reservation cancelled successfully!</div>
        <%
            }
        %>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Table</th>
                        <th>Guests</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<ReservationModel> reservations = (List<ReservationModel>) request.getAttribute("reservations");
                        if (reservations != null && !reservations.isEmpty()) {
                            for (ReservationModel res : reservations) {
                    %>
                        <tr>
                            <td><%= res.getReservationDate() %></td>
                            <td><%= res.getReservationTime() %></td>
                            <td>Table <%= res.getTableNumber() %></td>
                            <td><%= res.getNumberOfGuests() %></td>
                            <td><%= res.getStatus() %></td>
                            <td>
                                <% if ("confirmed".equals(res.getStatus())) { %>
                                    <form action="${pageContext.request.contextPath}/member/myReservations" method="post" style="display:inline;">
                                        <input type="hidden" name="reservationId" value="<%= res.getReservationId() %>">
                                        <button type="submit" class="btn btn-danger" style="background-color:#e74c3c; padding:5px 10px;">Cancel</button>
                                    </form>
                                <% } else { %>
                                    <span>Cancelled</span>
                                <% } %>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="6" style="text-align: center;">No reservations found.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/member/bookTable" class="btn">Book New Table</a>
            <a href="${pageContext.request.contextPath}/member/dashboard" style="margin-left: 15px;">Back to Dashboard</a>
        </p>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>