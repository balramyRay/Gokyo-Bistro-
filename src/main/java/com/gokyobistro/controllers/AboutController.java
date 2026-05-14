package com.gokyobistro.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * About Controller Servlet
   Displays about page
 */
@WebServlet("/about")
public class AboutController extends HttpServlet {
    
    /*
      Handles GET request - displays about page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Forward to about.jsp
        request.getRequestDispatcher("/WEB-INF/pages/about.jsp").forward(request, response);
    }
}