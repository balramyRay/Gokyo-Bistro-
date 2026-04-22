package com.gokyobistro.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.gokyobistro.model.MenuModel;
import com.gokyobistro.service.MenuService;

/**
 * Menu Controller Servlet
 * 
 * Purpose: Displays menu items with search functionality
 
 */
@WebServlet("/menu")
public class MenuController extends HttpServlet {
    
    private MenuService menuService = new MenuService();
    
    /**
     * Handles GET request - displays menu page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchKeyword = request.getParameter("search");
        List<MenuModel> menuList;
        
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            // Search menu items
            menuList = menuService.searchMenuItems(searchKeyword);
            request.setAttribute("searchKeyword", searchKeyword);
        } else {
            // Get all menu items
            menuList = menuService.getAllMenuItems();
        }
        
        request.setAttribute("menuList", menuList);
        request.getRequestDispatcher("/WEB-INF/pages/menu.jsp").forward(request, response);
    }
}