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
// below annotation tells when login clicked must run this server.
@WebServlet("/login")
public class LoginController extends HttpServlet {
    
	// instance of UserService to get access of that class.
    private UserService userService = new UserService();

    
    // Display Login Page
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/pages/login.jsp")
               .forward(request, response);
    }

    
    // Handle Login Form Submission
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get input from  email field password field.
        String email = request.getParameter("email");
        String password = request.getParameter("password");


        // Check empty fields
        if(email == null || email.trim().isEmpty() ||
           password == null || password.trim().isEmpty()) {

            response.sendRedirect(
                request.getContextPath() + "/login?error=empty"
            );

            return;
        }
        //check email exist or not
        
        if (!userService.isEmailExists(email)) {
            response.sendRedirect(request.getContextPath() + "/login?error=emailNotFound");
            return;
        }
        // Check account lock
        if(userService.isAccountLocked(email)) {

            response.sendRedirect(
                request.getContextPath() + "/login?error=locked"
            );

            return;
        }


        // Validate login
        UserModel user = userService.login(email, password);


        // Login Successful
        if(user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("loggedInUser", user);


            // Redirect based on role
            if("admin".equals(user.getRole())) {

                response.sendRedirect(
                    request.getContextPath() + "/admin/dashboard"
                );

            } else {

                response.sendRedirect(
                    request.getContextPath() + "/member/dashboard"
                );
            }

        }

        
        // Login Failed
        else {

            // Check if account became locked
            if(userService.isAccountLocked(email)) {

                response.sendRedirect(
                    request.getContextPath() + "/login?error=locked"
                );

            } else {

                response.sendRedirect(
                    request.getContextPath() + "/login?error=invalid"
                );
            }
        }
    }
}