<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="card">
            <h2>Create New Account</h2>
            
            <%
                String error = request.getParameter("error");
                if (error != null) {
                    if (error.equals("email")) {
            %>
                <div class="error-message">Email already exists. Please use another email.</div>
            <%
                    } else if (error.equals("missing")) {
            %>
                <div class="error-message">Please fill all required fields.</div>
            <%
                    } else if (error.equals("db")) {
            %>
                <div class="error-message">Registration failed. Please try again later.</div>
            <%
                    }
                }
            %>
            
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label for="fullName">Full Name *</label>
                    <input type="text" id="fullName" name="fullName" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email Address *</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" required>
                </div>
                
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone">
                </div>
                
                <div class="form-group">
                    <label for="address">Address</label>
                    <textarea id="address" name="address" rows="3"></textarea>
                </div>
                
                <button type="submit" class="btn">Register</button>
            </form>
            
            <p style="text-align: center; margin-top: 20px;">
                Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
            </p>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>