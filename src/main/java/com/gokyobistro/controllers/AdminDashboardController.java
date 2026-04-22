package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.UserModel;

/**
 * Admin Dashboard Controller
 *  Displays admin dashboard
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        // Check if user is admin
        if (!"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/member/dashboard");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
    }
}