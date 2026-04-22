<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.OrderModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Orders - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">All Customer Orders</h2>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer</th>
                        <th>Item</th>
                        <th>Qty</th>
                        <th>Total</th>
                        <th>Address</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<OrderModel> orders = (List<OrderModel>) request.getAttribute("orders");
                        if (orders != null && !orders.isEmpty()) {
                            for (OrderModel order : orders) {
                    %>
                        <tr>
                            <td><%= order.getOrderId() %></td>
                            <td><%= order.getUserName() %></td>
                            <td><%= order.getMenuItemName() %></td>
                            <td><%= order.getQuantity() %></td>
                            <td>Rs. <%= order.getTotalPrice() %></td>
                            <td><%= order.getDeliveryAddress() %></td>
                            <td><%= order.getStatus() %></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/orders" method="post" style="display:inline;">
                                    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                                    <select name="status" onchange="this.form.submit()">
                                        <option value="pending" <%= "pending".equals(order.getStatus()) ? "selected" : "" %>>Pending</option>
                                        <option value="confirmed" <%= "confirmed".equals(order.getStatus()) ? "selected" : "" %>>Confirmed</option>
                                        <option value="delivered" <%= "delivered".equals(order.getStatus()) ? "selected" : "" %>>Delivered</option>
                                        <option value="cancelled" <%= "cancelled".equals(order.getStatus()) ? "selected" : "" %>>Cancelled</option>
                                    </select>
                                </form>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="8" style="text-align: center;">No orders found.</td>
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