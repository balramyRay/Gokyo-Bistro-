<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book a Table - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <div class="card">
            <h2>Book a Table</h2>
            
            <%
                String error = request.getParameter("error");
                if (error != null) {
                    if (error.equals("unavailable")) {
            %>
                <div class="error-message">Selected table is not available at this time. Please choose another table or time.</div>
            <%
                    } else if (error.equals("missing")) {
            %>
                <div class="error-message">Please fill all required fields.</div>
            <%
                    } else if (error.equals("db")) {
            %>
                <div class="error-message">Booking failed. Please try again later.</div>
            <%
                    }
                }
            %>
            
            <form action="${pageContext.request.contextPath}/member/bookTable" method="post">
                <div class="form-group">
                    <label for="reservationDate">Reservation Date *</label>
                    <input type="date" id="reservationDate" name="reservationDate" required>
                </div>
                
                <div class="form-group">
                    <label for="reservationTime">Reservation Time *</label>
                    <select id="reservationTime" name="reservationTime" required>
                        <option value="">Select Time</option>
                        <option value="12:00:00">12:00 PM</option>
                        <option value="13:00:00">1:00 PM</option>
                        <option value="14:00:00">2:00 PM</option>
                        <option value="18:00:00">6:00 PM</option>
                        <option value="19:00:00">7:00 PM</option>
                        <option value="20:00:00">8:00 PM</option>
                        <option value="21:00:00">9:00 PM</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="tableNumber">Table Number *</label>
                    <select id="tableNumber" name="tableNumber" required>
                        <option value="">Select Table</option>
                        <option value="1">Table 1 (2 seats)</option>
                        <option value="2">Table 2 (2 seats)</option>
                        <option value="3">Table 3 (4 seats)</option>
                        <option value="4">Table 4 (4 seats)</option>
                        <option value="5">Table 5 (6 seats)</option>
                        <option value="6">Table 6 (6 seats)</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="numberOfGuests">Number of Guests *</label>
                    <input type="number" id="numberOfGuests" name="numberOfGuests" min="1" max="10" required>
                </div>
                
                <button type="submit" class="btn">Book Now</button>
            </form>
            
            <p style="text-align: center; margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/member/dashboard">Back to Dashboard</a>
            </p>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>