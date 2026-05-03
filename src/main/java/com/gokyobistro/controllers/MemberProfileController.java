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

@WebServlet("/member/profile")
public class MemberProfileController extends HttpServlet {
    
    private UserService userService = new UserService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null || !"member".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        // Get fresh user data from database
        UserModel freshUser = userService.getUserById(user.getUserId());
        request.setAttribute("user", freshUser);
        request.getRequestDispatcher("/WEB-INF/pages/member/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel sessionUser = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (sessionUser == null || !"member".equals(sessionUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            
            UserModel user = new UserModel();
            user.setUserId(sessionUser.getUserId());
            user.setFullName(fullName);
            user.setPhone(phone);
            user.setAddress(address);
            
            if (password != null && !password.isEmpty()) {
                user.setPassword(password);
            }
            
            boolean updated = userService.updateUserProfile(user);
            
            if (updated) {
                // Update session data
                sessionUser.setFullName(fullName);
                sessionUser.setPhone(phone);
                sessionUser.setAddress(address);
                session.setAttribute("loggedInUser", sessionUser);
                
                response.sendRedirect(request.getContextPath() + "/member/profile?success=updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/member/profile?error=db");
            }
        }
    }
}