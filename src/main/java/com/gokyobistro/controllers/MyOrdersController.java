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
   Purpose: Displays user's food orders
 */
@WebServlet("/member/myOrders")
public class MyOrdersController extends HttpServlet {
    
    private OrderService orderService = new OrderService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        // Get user's orders
        List<OrderModel> orders = orderService.getOrdersByUser(user.getUserId());
        request.setAttribute("orders", orders);
        
        request.getRequestDispatcher("/WEB-INF/pages/member/myOrders.jsp").forward(request, response);
    }
}