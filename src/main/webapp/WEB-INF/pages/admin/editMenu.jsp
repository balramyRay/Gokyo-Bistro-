<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.gokyobistro.model.MenuModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Menu Item - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <div class="card">
            <h2>Edit Menu Item</h2>
            
            <%
                MenuModel menuItem = (MenuModel) request.getAttribute("menuItem");
                if (menuItem == null) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageMenu");
                    return;
                }
            %>
            
            <form action="${pageContext.request.contextPath}/admin/manageMenu" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="menuId" value="<%= menuItem.getMenuId() %>">
                
                <div class="form-group">
                    <label>Item Name *</label>
                    <input type="text" name="itemName" value="<%= menuItem.getItemName() %>" required>
                </div>
                
                <div class="form-group">
                    <label>Category *</label>
                    <select name="category" required>
                        <option value="Starter" <%= "Starter".equals(menuItem.getCategory()) ? "selected" : "" %>>Starter</option>
                        <option value="Main Course" <%= "Main Course".equals(menuItem.getCategory()) ? "selected" : "" %>>Main Course</option>
                        <option value="Dessert" <%= "Dessert".equals(menuItem.getCategory()) ? "selected" : "" %>>Dessert</option>
                        <option value="Beverage" <%= "Beverage".equals(menuItem.getCategory()) ? "selected" : "" %>>Beverage</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" rows="3"><%= menuItem.getDescription() != null ? menuItem.getDescription() : "" %></textarea>
                </div>
                
                <div class="form-group">
                    <label>Price (Rs.) *</label>
                    <input type="number" name="price" step="0.01" value="<%= menuItem.getPrice() %>" required>
                </div>
                
                               
                <div class="form-group">
                    <label>Availability</label>
                    <select name="availability">
                        <option value="Yes" <%= "Yes".equals(menuItem.getAvailability()) ? "selected" : "" %>>Yes</option>
                        <option value="No" <%= "No".equals(menuItem.getAvailability()) ? "selected" : "" %>>No</option>
                    </select>
                </div>
                
                              
                <button type="submit" class="btn">Update Menu Item</button>
                <a href="${pageContext.request.contextPath}/admin/manageMenu" class="btn btn-secondary" style="background-color:#7f8c8d; margin-top:10px; text-align:center; display:inline-block; text-decoration:none;">Cancel</a>
            </form>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>