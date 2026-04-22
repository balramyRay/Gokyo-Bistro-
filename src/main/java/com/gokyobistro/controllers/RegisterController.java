package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.UserService;

/**
 * Register Controller Servlet
 * Handles new user registration
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
    
    private UserService userService = new UserService();
    
    /**
     * Handles GET request - displays registration page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Forward to register.jsp
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }
    
    /**
     * Handles POST request - processes registration form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        // Check if email already exists
        if (userService.isEmailExists(email)) {
            response.sendRedirect(request.getContextPath() + "/register?error=email");
            return;
        }
        
        // Create user object
        UserModel user = new UserModel();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        
        // Register user
        boolean registered = userService.registerUser(user);
        
        if (registered) {
            // Registration successful - redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Registration failed - show error
            response.sendRedirect(request.getContextPath() + "/register?error=db");
        }
    }
}