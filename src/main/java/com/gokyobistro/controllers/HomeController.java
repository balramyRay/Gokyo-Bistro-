package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
  Purpose: Displays the home page
  */
@WebServlet("/home")
public class HomeController extends HttpServlet {
    
    /**
     * Handles GET request - displays home page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Forward to home.jsp inside WEB-INF/pages
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
    }
}