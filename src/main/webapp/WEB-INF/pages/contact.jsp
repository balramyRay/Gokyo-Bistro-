<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="card" style="max-width: 800px;">
            <h2>Contact Us</h2>
            
            <%
                String success = request.getParameter("success");
                if (success != null && success.equals("sent")) {
            %>
                <div class="success-message">Your message has been sent. We will get back to you soon!</div>
            <%
                }
            %>
            
            <div style="margin-bottom: 30px;">
                <h3>Get in Touch</h3>
                <p><strong>Address:</strong> Lazimpat, Kathmandu, Nepal</p>
                <p><strong>Phone:</strong> 01-1234567, 9841234567</p>
                <p><strong>Email:</strong> info@gokyobistro.com</p>
                <p><strong>Opening Hours:</strong> 10:00 AM - 10:00 PM (Daily)</p>
            </div>
            
            <h3>Send us a Message</h3>
            <form action="${pageContext.request.contextPath}/contact" method="post">
                <div class="form-group">
                    <label for="name">Your Name</label>
                    <input type="text" id="name" name="name" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Your Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <input type="text" id="subject" name="subject" required>
                </div>
                
                <div class="form-group">
                    <label for="message">Message</label>
                    <textarea id="message" name="message" rows="5" required></textarea>
                </div>
                
                <button type="submit" class="btn">Send Message</button>
            </form>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>