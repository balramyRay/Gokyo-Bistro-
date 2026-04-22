package com.gokyobistro.controllers;

import java.io.IOException;
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
  Purpose: Handles table booking
 */
@WebServlet("/member/bookTable")
public class BookTableController extends HttpServlet {
    
    private ReservationService reservationService = new ReservationService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check login
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/member/bookTable.jsp").forward(request, response);
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
        
        // Get form parameters
        String reservationDate = request.getParameter("reservationDate");
        String reservationTime = request.getParameter("reservationTime");
        String tableNumberStr = request.getParameter("tableNumber");
        String guestsStr = request.getParameter("numberOfGuests");
        
        if (reservationDate == null || reservationTime == null || tableNumberStr == null || guestsStr == null) {
            response.sendRedirect(request.getContextPath() + "/member/bookTable?error=missing");
            return;
        }
        
        int tableNumber = Integer.parseInt(tableNumberStr);
        int numberOfGuests = Integer.parseInt(guestsStr);
        
        // Check if table is available
        if (!reservationService.isTableAvailable(tableNumber, reservationDate, reservationTime)) {
            response.sendRedirect(request.getContextPath() + "/member/bookTable?error=unavailable");
            return;
        }
        
        // Create reservation
        ReservationModel reservation = new ReservationModel();
        reservation.setUserId(user.getUserId());
        reservation.setReservationDate(reservationDate);
        reservation.setReservationTime(reservationTime);
        reservation.setTableNumber(tableNumber);
        reservation.setNumberOfGuests(numberOfGuests);
        
        boolean booked = reservationService.bookTable(reservation);
        
        if (booked) {
            response.sendRedirect(request.getContextPath() + "/member/myReservations?success=booked");
        } else {
            response.sendRedirect(request.getContextPath() + "/member/bookTable?error=db");
        }
    }
}