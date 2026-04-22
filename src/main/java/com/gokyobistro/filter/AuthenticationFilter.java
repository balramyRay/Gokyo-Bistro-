package com.gokyobistro.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.gokyobistro.model.UserModel;

/**
 * Authentication Filter
 * 
 * Purpose: Checks if user is logged in before allowing access to protected pages
 * 
 * Lecture Reference: Week 6 - Redirect Management (Filter)
 * DSA Instructions: Section 4f - Redirect Management
 * 
 * URL Pattern: /* (all URLs)
 * 
 * Protected URLs (require login):
 * - /member/*
 * - /admin/*
 * 
 * Public URLs (no login required):
 * - /home
 * - /login
 * - /register
 * - /about
 * - /contact
 * - /menu
 * - /css/*
 * - /images/*
 * 
 * @author Your Name
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
        System.out.println("Authentication Filter Initialized");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Cast to HTTP-specific objects
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Get the requested URL
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        // List of public URLs (no login required)
        boolean isPublicUrl = 
            path.equals("/home") ||
            path.equals("/login") ||
            path.equals("/register") ||
            path.equals("/about") ||
            path.equals("/contact") ||
            path.equals("/menu") ||
            path.startsWith("/css/") ||
            path.startsWith("/images/") ||
            path.equals("/logout");
        
        // If it's a public URL, allow access immediately
        if (isPublicUrl) {
            chain.doFilter(request, response);
            return;
        }
        
        // Check if user is logged in
        HttpSession session = httpRequest.getSession(false);
        UserModel loggedInUser = (session != null) ? (UserModel) session.getAttribute("loggedInUser") : null;
        
        if (loggedInUser == null) {
            // Not logged in - redirect to login page
            httpResponse.sendRedirect(contextPath + "/login?error=unauthorized");
            return;
        }
        
        // Check role-based access for admin pages
        if (path.startsWith("/admin/")) {
            if (!"admin".equals(loggedInUser.getRole())) {
                // Member trying to access admin page - redirect to member dashboard
                httpResponse.sendRedirect(contextPath + "/member/dashboard");
                return;
            }
        }
        
        // Check role-based access for member pages
        if (path.startsWith("/member/")) {
            if (!"member".equals(loggedInUser.getRole())) {
                // Admin trying to access member page - redirect to admin dashboard
                httpResponse.sendRedirect(contextPath + "/admin/dashboard");
                return;
            }
        }
        
        // User is authenticated and authorized - proceed to requested resource
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Cleanup code if needed
        System.out.println("Authentication Filter Destroyed");
    }
}