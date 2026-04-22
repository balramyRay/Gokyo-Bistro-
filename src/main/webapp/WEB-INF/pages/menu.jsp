<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.MenuModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
    body {
        background-color: #FDF8F0;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">Our Menu</h2>
        
        <!-- Search Bar -->
        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/menu" method="get">
                <input type="text" name="search" placeholder="Search menu items..." 
                       value="<%= request.getAttribute("searchKeyword") != null ? request.getAttribute("searchKeyword") : "" %>">
                <button type="submit" class="btn">Search</button>
            </form>
        </div>
        
        <!-- Menu Grid -->
        <div class="menu-grid">
            <%
                List<MenuModel> menuList = (List<MenuModel>) request.getAttribute("menuList");
                if (menuList != null && !menuList.isEmpty()) {
                    for (MenuModel item : menuList) {
            %>
                <div class="menu-card">
                    <div class="menu-card-content">
                        <h3><%= item.getItemName() %></h3>
                        <p><%= item.getDescription() %></p>
                        <p><strong>Category:</strong> <%= item.getCategory() %></p>
                        <div class="menu-price">Rs. <%= item.getPrice() %></div>
                        <% if (item.getOfferPrice() > 0) { %>
                            <p><span style="text-decoration: line-through;">Rs. <%= item.getPrice() %></span> 
                            <strong>Offer: Rs. <%= item.getOfferPrice() %></strong></p>
                        <% } %>
                        <a href="${pageContext.request.contextPath}/member/placeOrder?menuId=<%= item.getMenuId() %>" 
                           class="btn" style="margin-top: 10px;">Order Now</a>
                    </div>
                </div>
            <%
                    }
                } else {
            %>
                <p style="text-align: center; width: 100%;">No menu items found.</p>
            <%
                }
            %>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>