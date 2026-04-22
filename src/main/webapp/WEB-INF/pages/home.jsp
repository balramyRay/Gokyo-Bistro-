<!-- This tells the server that this is a JSP page using Java.
     It will output HTML with UTF-8 encoding so all languages correctly. -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gokyo Bistro - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
         /* Sets a background image for the whole page. */
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
   <!-- header.jsp filed taken in this home page -->
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="welcome-banner">
            <h2>Welcome to Gokyo Bistro</h2>
            <p>Experience the finest dining in town with authentic flavors and warm hospitality.</p>
        </div>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>Book a Table</h3>
                <p>Reserve your table for an unforgettable dining experience.</p>
                <a href="${pageContext.request.contextPath}/member/bookTable" class="btn" style="margin-top: 15px;">Book Now</a>
            </div>
            
            <div class="dashboard-card">
                <h3>Order Food</h3>
                <p>Enjoy our delicious food at home with quick delivery.</p>
                <a href="${pageContext.request.contextPath}/menu" class="btn" style="margin-top: 15px;">Order Now</a>
            </div>
            
            <div class="dashboard-card">
                <h3>View Menu</h3>
                <p>Explore our wide range of authentic dishes.</p>
                <a href="${pageContext.request.contextPath}/menu" class="btn" style="margin-top: 15px;">View Menu</a>
            </div>
        </div>
        
       
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>