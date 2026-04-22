<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="card" style="max-width: 800px;">
            <h2>About Gokyo Bistro</h2>
            
            <p style="margin-bottom: 20px; line-height: 1.6;">
                Welcome to Gokyo Bistro, where culinary excellence meets warm hospitality. 
                Established in 2015, we have been serving authentic Nepali and continental 
                cuisine to our beloved customers.
            </p>
            
            <h3>Our Mission</h3>
            <p style="margin-bottom: 20px; line-height: 1.6;">
                To provide an unforgettable dining experience with high-quality food, 
                exceptional service, and a welcoming atmosphere.
            </p>
            
            <h3>Why Choose Us?</h3>
            <ul style="margin-left: 20px; margin-bottom: 20px; line-height: 1.6;">
                <li>Fresh ingredients sourced locally</li>
                <li>Experienced chefs with authentic recipes</li>
                <li>Clean and hygienic environment</li>
                <li>Affordable prices with great value</li>
                <li>Fast home delivery service</li>
            </ul>
            
            <h3>Our Location</h3>
            <p>Lazimpat, Kathmandu, Nepal</p>
            <p>Phone: 01-1234567</p>
            <p>Email: info@gokyobistro.com</p>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>