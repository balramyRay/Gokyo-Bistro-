<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    
    <style>
        body {
            background-image: url('${pageContext.request.contextPath}/images/background.png');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }
        
        .card {
            background: rgba(255, 255, 255, 0.92);
        }
        
        .header, .footer {
            background-color: rgba(44, 62, 80, 0.85);
        }
    </style>
</head>
<body>
    <!-- Include the header file (navigation bar) -->
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="card">
        
            <h2>Login to Your Account</h2>
            
            <!-- Success Message -->
            <%
                String success = request.getParameter("success");
                if(success != null && success.equals("passwordReset")) {
            %>
                <div class="success-message">
                    Password reset successfully! Please login with your new password.
                </div>
            <%
                }
            %>
            
            <!-- Error Messages -->
            <%
                String error = request.getParameter("error");
                
                if(error != null && error.equals("emailNotFound")) {
            %>
                <div class="error-message">
                    Email address does not exist in our records. Please register first.
                </div>
            <%
                } else if(error != null && error.equals("invalid")) {
            %>
                <div class="error-message">
                    password is incorrect. Please try again.
                    3 times wrong password, account locked.
                </div>
            <%
                } else if(error != null && error.equals("locked")) {
            %>
                <div class="error-message">
                    Account is locked. Please try again after 5 minutes.
                </div>
            <%
                } else if(error != null && error.equals("empty")) {
            %>
                <div class="error-message">
                    Please fill all required fields.
                </div>
            <%
                }
            %>
            
            <!-- Login Form -->
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email">
                </div>
                
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password">
                </div>
                
                <div style="text-align: right; margin-bottom: 15px;">
                    <a href="${pageContext.request.contextPath}/forgotPassword">
                        Forgot Password?
                    </a>
                </div>
                
                <button type="submit" class="btn">
                    Login
                </button>
            </form>
            
            <p style="text-align: center; margin-top: 20px;">
                Don't have an account?
                <a href="${pageContext.request.contextPath}/register">
                    Register here
                </a>
            </p>
        </div>
    </div>
    <!-- footer added -->
    <jsp:include page="footer.jsp" />

</body>
</html>