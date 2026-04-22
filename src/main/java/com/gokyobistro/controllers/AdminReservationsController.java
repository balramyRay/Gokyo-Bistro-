package com.gokyobistro.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.ReservationModel;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.ReservationService;

/**
  View all customer reservations (Admin only)
 */
@WebServlet("/admin/reservations")
public class AdminReservationsController extends HttpServlet {
    
    private ReservationService reservationService = new ReservationService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        List<ReservationModel> reservations = reservationService.getAllReservations();
        request.setAttribute("reservations", reservations);
        
        request.getRequestDispatcher("/WEB-INF/pages/admin/reservations.jsp").forward(request, response);
    }
}