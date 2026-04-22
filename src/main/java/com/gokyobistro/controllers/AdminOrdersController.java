package com.gokyobistro.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.OrderModel;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.OrderService;

/**
  View and manage all customer orders (Admin only)
 */
@WebServlet("/admin/orders")
public class AdminOrdersController extends HttpServlet {
    
    private OrderService orderService = new OrderService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        List<OrderModel> orders = orderService.getAllOrders();
        request.setAttribute("orders", orders);
        
        request.getRequestDispatcher("/WEB-INF/pages/admin/orders.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        String orderIdStr = request.getParameter("orderId");
        String status = request.getParameter("status");
        
        if (orderIdStr != null && status != null) {
            int orderId = Integer.parseInt(orderIdStr);
            orderService.updateOrderStatus(orderId, status);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/orders");
    }
}