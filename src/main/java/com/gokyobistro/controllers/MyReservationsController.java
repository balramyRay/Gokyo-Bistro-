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

/*
  Displays users reservations and handles cancellation
 */
@WebServlet("/member/myReservations")
public class MyReservationsController extends HttpServlet {
    
    private ReservationService reservationService = new ReservationService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        // Get users reservations
        List<ReservationModel> reservations = reservationService.getReservationsByUser(user.getUserId());
        request.setAttribute("reservations", reservations);
        
        request.getRequestDispatcher("/WEB-INF/pages/member/myReservations.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        // Cancel reservation
        String reservationIdStr = request.getParameter("reservationId");
        if (reservationIdStr != null) {
            int reservationId = Integer.parseInt(reservationIdStr);
            reservationService.cancelReservation(reservationId);
        }
        
        response.sendRedirect(request.getContextPath() + "/member/myReservations?success=cancelled");
    }
}