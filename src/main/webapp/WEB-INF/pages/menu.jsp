<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.MenuModel" %>
<%
    String searchKeyword = (String) request.getAttribute("searchKeyword");
    String sortBy = (String) request.getAttribute("sortBy");
    List<MenuModel> menuList = (List<MenuModel>) request.getAttribute("menuList");
    
    if (sortBy == null || sortBy.isEmpty()) {
        sortBy = "default";
    }
%>
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
        
        .availability-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            margin-left: 10px;
        }
        
        .available {
            background-color: #28a745;
            color: white;
        }
        
        .not-available {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-disabled {
            background-color: #6c757d;
            cursor: not-allowed;
            display: inline-block;
            padding: 8px 20px;
            border-radius: 5px;
            color: white;
            font-size: 14px;
            text-align: center;
            margin-top: 10px;
        }
        
        .btn {
            background-color: #e67e22;
            color: white;
            padding: 8px 20px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
            border: none;
            cursor: pointer;
        }
        
        .btn:hover {
            background-color: #d35400;
        }
        
        .menu-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: flex-start;
        }
        
        .menu-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            width: 300px;
            max-width: 100%;
            overflow: hidden;
        }
        
        .menu-card-content {
            padding: 15px;
        }
        
        .menu-price {
            color: #e67e22;
            font-size: 20px;
            font-weight: bold;
            margin: 10px 0;
        }
        
        .search-bar {
            margin-bottom: 30px;
            text-align: center;
        }
        
        .search-bar input {
            padding: 10px;
            width: 250px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        
        .search-bar select {
            padding: 10px;
            width: 150px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-left: 10px;
        }
        
        .search-bar button {
            padding: 10px 20px;
            margin-left: 10px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .clear-search {
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">Our Menu</h2>
        
        <!-- Search Bar with Sort -->
        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/menu" method="get">
                <input type="text" name="search" placeholder="Search menu items..." 
                       value="<%= searchKeyword != null ? searchKeyword : "" %>">
                
                <select name="sort">
                    <option value="default" <%= "default".equals(sortBy) ? "selected" : "" %>>Default</option>
                    <option value="price_asc" <%= "price_asc".equals(sortBy) ? "selected" : "" %>>Price: Low to High</option>
                    <option value="price_desc" <%= "price_desc".equals(sortBy) ? "selected" : "" %>>Price: High to Low</option>
                    <option value="name_asc" <%= "name_asc".equals(sortBy) ? "selected" : "" %>>Name: A to Z</option>
                </select>
                
                <button type="submit" class="btn">Search</button>
                
                <% if (searchKeyword != null && !searchKeyword.isEmpty()) { %>
                    <a href="${pageContext.request.contextPath}/menu" class="clear-search" style="color: #e67e22;">Clear Search</a>
                <% } %>
            </form>
        </div>
        
        <!-- Search Info -->
        <% if (searchKeyword != null && !searchKeyword.isEmpty()) { %>
            <div style="text-align: center; margin-bottom: 20px; padding: 10px; background-color: #f0f0f0; border-radius: 5px;">
                Showing results for: <strong>"<%= searchKeyword %>"</strong>
                <a href="${pageContext.request.contextPath}/menu" style="color: #e67e22; margin-left: 10px;">View All Menu</a>
            </div>
        <% } %>
        
        <!-- Menu Grid -->
        <div class="menu-grid">
            <%
                if (menuList != null && !menuList.isEmpty()) {
                    for (MenuModel item : menuList) {
            %>
                <div class="menu-card">
                    <div class="menu-card-content">
                        <h3>
                            <%= item.getItemName() %>
                            <% if ("Yes".equals(item.getAvailability())) { %>
                                <span class="availability-badge available">Available</span>
                            <% } else { %>
                                <span class="availability-badge not-available">Not Available</span>
                            <% } %>
                        </h3>
                        <p><%= item.getDescription() != null ? item.getDescription() : "" %></p>
                        <p><strong>Category:</strong> <%= item.getCategory() %></p>
                        <div class="menu-price">Rs. <%= item.getPrice() %></div>
                       
                        <% if ("Yes".equals(item.getAvailability())) { %>
                            <a href="${pageContext.request.contextPath}/member/placeOrder?menuId=<%= item.getMenuId() %>" 
                               class="btn">Order Now</a>
                        <% } else { %>
                            <div class="btn-disabled">Not Available</div>
                        <% } %>
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