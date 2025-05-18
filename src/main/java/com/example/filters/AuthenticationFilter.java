package com.example.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import com.example.models.User;

@WebFilter("/admin/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        System.out.println("Filtering request: " + requestURI); // Debug log

        // Kiểm tra tất cả các URL con của admin
        if (requestURI.contains("/admin/")) {
            // Kiểm tra session tồn tại
            if (session == null || session.getAttribute("user") == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/accessDenied.jsp");
                return;
            }

            // Kiểm tra role
            User user = (User) session.getAttribute("user");
            if (!"admin".equals(user.getRole())) {
                System.out.println("Access denied for user: " + user.getUsername() + " with role: " + user.getRole());
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/accessDenied.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
