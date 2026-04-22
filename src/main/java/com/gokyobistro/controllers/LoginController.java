package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.UserService;

//When user goes to /login, this servlet runs

@WebServlet("/login")
public class LoginController extends HttpServlet {
    
    private UserService userService = new UserService();
    
    /**
     * Handles GET request - displays login page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Forward to login.jsp
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
    
    /**
     * Handles POST request - processes login form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate credentials using service
        UserModel user = userService.login(email, password);
        
        if (user != null) {
            // Login successful - create session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            
            // Redirect based on role
            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/member/dashboard");
            }
        } else {
            // Login failed - redirect back with error
            response.sendRedirect(request.getContextPath() + "/login?error=invalid");
        }
    }
}