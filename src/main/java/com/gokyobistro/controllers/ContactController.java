package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
  Displays contact page and processes contact form
 **/
@WebServlet("/contact")
public class ContactController extends HttpServlet {
    
    /**
     * Handles GET request - displays contact page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Forward to contact.jsp
        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }
    
    /**
     * Handles POST request - processes contact form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        
         // For now, just redirect with success message
        
        response.sendRedirect(request.getContextPath() + "/contact?success=sent");
    }
}