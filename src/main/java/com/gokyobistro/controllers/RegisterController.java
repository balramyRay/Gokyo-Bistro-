package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.UserService;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    
    private UserService userService = new UserService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        // EMAIL FORMAT VALIDATION ONLY
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        
        if (email == null || !email.matches(emailPattern)) {
            // Pass back the values so form retains them
            response.sendRedirect(request.getContextPath() + "/register?error=invalidEmail&fullName=" + fullName + "&email=" + email + "&phone=" + phone + "&address=" + address);
            return;
        }
        
        // Check if email already exists
        if (userService.isEmailExists(email)) {
            response.sendRedirect(request.getContextPath() + "/register?error=email&fullName=" + fullName + "&email=" + email + "&phone=" + phone + "&address=" + address);
            return;
        }
        
        UserModel user = new UserModel();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        
        boolean registered = userService.registerUser(user);
        
        if (registered) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            response.sendRedirect(request.getContextPath() + "/register?error=db&fullName=" + fullName + "&email=" + email + "&phone=" + phone + "&address=" + address);
        }
    }
}