package com.gokyobistro.controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.gokyobistro.model.MenuModel;
import com.gokyobistro.model.UserModel;
import com.gokyobistro.service.MenuService;
import com.gokyobistro.util.ImageUtil;

/*
 CRUD operations for menu items
 */
@WebServlet("/admin/manageMenu")

public class ManageMenuController extends HttpServlet {
    
    private MenuService menuService = new MenuService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check admin access
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        String action = request.getParameter("action");
        String menuIdStr = request.getParameter("id");
        
        if ("edit".equals(action) && menuIdStr != null) {
            // Show edit form
            int menuId = Integer.parseInt(menuIdStr);
            MenuModel menuItem = menuService.getMenuItemById(menuId);
            request.setAttribute("menuItem", menuItem);
            request.getRequestDispatcher("/WEB-INF/pages/admin/editMenu.jsp").forward(request, response);
            return;
        } else if ("delete".equals(action) && menuIdStr != null) {
            // Delete menu item
            int menuId = Integer.parseInt(menuIdStr);
            menuService.deleteMenuItem(menuId);
            response.sendRedirect(request.getContextPath() + "/admin/manageMenu?success=deleted");
            return;
        }
        
        // Get all menu items
        List<MenuModel> menuList = menuService.getAllMenuItems();
        request.setAttribute("menuList", menuList);
        request.getRequestDispatcher("/WEB-INF/pages/admin/manageMenu.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        UserModel user = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            // Add new menu item
            String itemName = request.getParameter("itemName");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String availability = request.getParameter("availability");
            
            MenuModel menu = new MenuModel();
            menu.setItemName(itemName);
            menu.setCategory(category);
            menu.setDescription(description);
            menu.setPrice(Double.parseDouble(priceStr));
            menu.setAvailability(availability);
            
                      
            boolean added = menuService.addMenuItem(menu);
            if (added) {
                response.sendRedirect(request.getContextPath() + "/admin/manageMenu?success=added");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/manageMenu?error=db");
            }
        } else if ("update".equals(action)) {
            // Update existing menu item
            String menuIdStr = request.getParameter("menuId");
            String itemName = request.getParameter("itemName");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String availability = request.getParameter("availability");
            
            MenuModel menu = new MenuModel();
            menu.setMenuId(Integer.parseInt(menuIdStr));
            menu.setItemName(itemName);
            menu.setCategory(category);
            menu.setDescription(description);
            menu.setPrice(Double.parseDouble(priceStr));
            menu.setAvailability(availability);
            
            boolean updated = menuService.updateMenuItem(menu);
            if (updated) {
                response.sendRedirect(request.getContextPath() + "/admin/manageMenu?success=updated");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/manageMenu?error=db");
            }
        }
    }
}