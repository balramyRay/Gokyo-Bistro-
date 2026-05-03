package com.gokyobistro.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.UserService;

@WebServlet("/admin/manageMembers")
public class AdminManageMembersController extends HttpServlet {
    
    private UserService userService = new UserService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel admin = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (admin == null || !"admin".equals(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        String action = request.getParameter("action");
        String userIdStr = request.getParameter("id");
        
        if ("delete".equals(action) && userIdStr != null) {
            int userId = Integer.parseInt(userIdStr);
            
            //  DELETE member 
            boolean deleted = userService.deleteUser(userId);
            
            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/admin/manageMembers?success=deleted");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/manageMembers?error=delete");
            }
            return;
        }
        
        List<UserModel> membersList = userService.getAllMembers();
        request.setAttribute("membersList", membersList);
        request.getRequestDispatcher("/WEB-INF/pages/admin/manageMembers.jsp").forward(request, response);
    }
}