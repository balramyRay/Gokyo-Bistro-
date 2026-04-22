<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gokyobistro.model.MenuModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Place Order - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <div class="card">
            <h2>Place Your Order</h2>
            
            <%
                MenuModel menuItem = (MenuModel) request.getAttribute("menuItem");
                String error = request.getParameter("error");
                if (error != null && error.equals("db")) {
            %>
                <div class="error-message">Order failed. Please try again later.</div>
            <%
                }
                
                if (menuItem != null) {
            %>
                <div style="background: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px;">
                    <h3><%= menuItem.getItemName() %></h3>
                    <p><%= menuItem.getDescription() %></p>
                    <p><strong>Price: Rs. <%= menuItem.getPrice() %></strong></p>
                </div>
            <%
                }
            %>
            
            <form action="${pageContext.request.contextPath}/member/placeOrder" method="post">
                <input type="hidden" name="menuId" value="<%= menuItem != null ? menuItem.getMenuId() : request.getParameter("menuId") %>">
                
                <div class="form-group">
                    <label for="quantity">Quantity *</label>
                    <input type="number" id="quantity" name="quantity" min="1" max="10" value="1" required>
                </div>
                
                <div class="form-group">
                    <label for="deliveryAddress">Delivery Address *</label>
                    <textarea id="deliveryAddress" name="deliveryAddress" rows="3" required></textarea>
                </div>
                
                <button type="submit" class="btn">Place Order</button>
            </form>
            
            <p style="text-align: center; margin-top: 20px;">
                <a href="${pageContext.request.contextPath}/menu">Back to Menu</a>
            </p>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>