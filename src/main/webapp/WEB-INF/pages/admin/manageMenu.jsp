<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.MenuModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Menu - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">Manage Menu Items</h2>
        
        <%
            String success = request.getParameter("success");
            if (success != null) {
                if (success.equals("added")) {
        %>
            <div class="success-message">Menu item added successfully!</div>
        <%
                } else if (success.equals("updated")) {
        %>
            <div class="success-message">Menu item updated successfully!</div>
        <%
                } else if (success.equals("deleted")) {
        %>
            <div class="success-message">Menu item deleted successfully!</div>
        <%
                }
            }
            
            String error = request.getParameter("error");
            if (error != null && error.equals("db")) {
        %>
            <div class="error-message">Operation failed. Please try again.</div>
        <%
            }
        %>
        
        <!-- Add New Menu Item Form -->
        <div class="card" style="margin-bottom: 30px;">
            <h3>Add New Menu Item</h3>
            <form action="${pageContext.request.contextPath}/admin/manageMenu" method="post">
                <input type="hidden" name="action" value="add">
                
                <div class="form-group">
                    <label>Item Name *</label>
                    <input type="text" name="itemName" required>
                </div>
                
                <div class="form-group">
                    <label>Category *</label>
                    <select name="category" required>
                        <option value="Starter">Starter</option>
                        <option value="Main Course">Main Course</option>
                        <option value="Dessert">Dessert</option>
                        <option value="Beverage">Beverage</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" rows="2"></textarea>
                </div>
                
                <div class="form-group">
                    <label>Price (Rs.) *</label>
                    <input type="number" name="price" step="0.01" required>
                </div>
                
                <div class="form-group">
                    <label>Offer Price (Rs.)</label>
                    <input type="number" name="offerPrice" step="0.01">
                </div>
                                               
                <div class="form-group">
                    <label>Availability</label>
                    <select name="availability">
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                    </select>
                </div>
                
                <button type="submit" class="btn">Add Menu Item</button>
            </form>
        </div>
        
        <!-- Existing Menu Items Table -->
        <h3>Existing Menu Items</h3>
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Item Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Availability</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<MenuModel> menuList = (List<MenuModel>) request.getAttribute("menuList");
                        if (menuList != null && !menuList.isEmpty()) {
                            for (MenuModel item : menuList) {
                    %>
                        <tr>
                            <td><%= item.getMenuId() %></td>
                            <td><%= item.getItemName() %></td>
                            <td><%= item.getCategory() %></td>
                            <td>Rs. <%= item.getPrice() %></td>
                            <td><%= item.getAvailability() %></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/manageMenu?action=edit&id=<%= item.getMenuId() %>" 
                                   style="color:blue;">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/manageMenu?action=delete&id=<%= item.getMenuId() %>" 
                                   style="color:red;" onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="6" style="text-align: center;">No menu items found.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
        </p>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>