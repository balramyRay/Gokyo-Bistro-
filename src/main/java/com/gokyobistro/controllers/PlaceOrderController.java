package com.gokyobistro.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.MenuModel;
import com.gokyobistro.model.OrderModel;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.MenuService;
import com.gokyobistro.service.OrderService;

/*
  Handles food ordering
 */
@WebServlet("/member/placeOrder")
public class PlaceOrderController extends HttpServlet {
    
    private MenuService menuService = new MenuService();
    private OrderService orderService = new OrderService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        String menuIdStr = request.getParameter("menuId");
        if (menuIdStr != null) {
            int menuId = Integer.parseInt(menuIdStr);
            MenuModel menuItem = menuService.getMenuItemById(menuId);
            request.setAttribute("menuItem", menuItem);
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/member/placeOrder.jsp").forward(request, response);
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
        String menuIdStr = request.getParameter("menuId");
        String quantityStr = request.getParameter("quantity");
        String deliveryAddress = request.getParameter("deliveryAddress");
        
        int menuId = Integer.parseInt(menuIdStr);
        int quantity = Integer.parseInt(quantityStr);
        
        // Get menu item price
        MenuModel menuItem = menuService.getMenuItemById(menuId);
        double totalPrice = menuItem.getPrice() * quantity;
        
        // Create order
        OrderModel order = new OrderModel();
        order.setUserId(user.getUserId());
        order.setMenuId(menuId);
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setDeliveryAddress(deliveryAddress);
        
        boolean placed = orderService.placeOrder(order);
        
        if (placed) {
            response.sendRedirect(request.getContextPath() + "/member/myOrders?success=placed");
        } else {
            response.sendRedirect(request.getContextPath() + "/member/placeOrder?error=db&menuId=" + menuId);
        }
    }
}