<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.gokyobistro.model.OrderModel" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders - Gokyo Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container">
        <h2 style="text-align: center; margin-bottom: 30px;">My Orders</h2>
        
        <%
            String success = request.getParameter("success");
            if (success != null && success.equals("placed")) {
        %>
            <div class="success-message">Order placed successfully!</div>
        <%
            }
        %>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Item</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Delivery Address</th>
                        <th>Status</th>
                        <th>Order Date</th>
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
                            <td><%= order.getMenuItemName() %></td>
                            <td><%= order.getQuantity() %></td>
                            <td>Rs. <%= order.getTotalPrice() %></td>
                            <td><%= order.getDeliveryAddress() %></td>
                            <td>
                                <%
                                    String status = order.getStatus();
                                    if ("pending".equals(status)) {
                                        out.print("<span style='color:orange;'>Pending</span>");
                                    } else if ("confirmed".equals(status)) {
                                        out.print("<span style='color:green;'>Confirmed</span>");
                                    } else if ("delivered".equals(status)) {
                                        out.print("<span style='color:blue;'>Delivered</span>");
                                    } else if ("cancelled".equals(status)) {
                                        out.print("<span style='color:red;'>Cancelled</span>");
                                    }
                                %>
                            </td>
                            <td><%= order.getOrderDate() %></td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="7" style="text-align: center;">No orders found.</td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        
        <p style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/menu" class="btn">Order More Food</a>
            <a href="${pageContext.request.contextPath}/member/dashboard" style="margin-left: 15px;">Back to Dashboard</a>
        </p>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>