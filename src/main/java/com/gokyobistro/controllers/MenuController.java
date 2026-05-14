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

/*
  Displays menu items with search and sort functionality
 */
@WebServlet("/menu")
public class MenuController extends HttpServlet {
    
    private MenuService menuService = new MenuService();
    
    /*
     displays menu page with search and sort
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String searchKeyword = request.getParameter("search");
        String sortBy = request.getParameter("sort");
        
        // Set default sort if not provided
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "default";
        }
        
        List<MenuModel> menuList;
        
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            // Search menu items with sort
            menuList = menuService.searchMenuItems(searchKeyword, sortBy);
            request.setAttribute("searchKeyword", searchKeyword);
        } else {
            // Get all menu items with sort
            menuList = menuService.getAllMenuItems(sortBy);
        }
        
        request.setAttribute("menuList", menuList);
        request.setAttribute("sortBy", sortBy);
        request.getRequestDispatcher("/WEB-INF/pages/menu.jsp").forward(request, response);
    }
}