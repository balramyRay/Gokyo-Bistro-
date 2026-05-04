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

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Authentication Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

      
        // Allow static resources
        
        if (path.startsWith("/css/")
                || path.startsWith("/images/")
                || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

     
        // Public pages (NO login required)
       
        boolean isPublic =
                path.equals("/") ||
                path.equals("/index.jsp") ||
                path.equals("/home") ||
                path.equals("/login") ||
                path.equals("/register") ||
                path.equals("/menu") ||
                path.equals("/about") ||
                path.equals("/contact");

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        //  Check login session
     
        HttpSession session = req.getSession(false);
        UserModel user = (session != null)
                ? (UserModel) session.getAttribute("loggedInUser")
                : null;

        // Not logged in → redirect to login
        if (user == null) {
            res.sendRedirect(contextPath + "/login?error=unauthorized");
            return;
        }

        
        // 4. Role-based access control
   
        if (path.startsWith("/admin/")) {
            if (!"admin".equals(user.getRole())) {
                res.sendRedirect(contextPath + "/member/dashboard");
                return;
            }
        }

        if (path.startsWith("/member/")) {
            if (!"member".equals(user.getRole())) {
                res.sendRedirect(contextPath + "/admin/dashboard");
                return;
            }
        }

    
        // 5. Allow request
       
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("Authentication Filter Destroyed");
    }
}