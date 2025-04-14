package com.example.servlets;

import com.example.dao.UserDAO;
import com.example.utils.EmailUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ResetPasswordServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String token = request.getParameter("token");
            if (token != null) {
                request.setAttribute("token", token);
                request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in doGet", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            UserDAO userDAO = new UserDAO();

            if ("request".equals(action)) {
                handlePasswordResetRequest(request, response, userDAO);
            } else if ("reset".equals(action)) {
                handlePasswordReset(request, response, userDAO);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in doPost", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handlePasswordResetRequest(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws ServletException, IOException, ClassNotFoundException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        if (userDAO.updateResetToken(email, token)) {
            String resetLink = String.format("%s://%s:%d%s/reset-password?token=%s",
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    request.getContextPath(),
                    token);

            if (EmailUtils.sendResetPasswordEmail(email, resetLink)) {
                request.setAttribute("message", "Link reset đã được gửi tới email của bạn");
            } else {
                request.setAttribute("error", "Không thể gửi email. Vui lòng thử lại sau");
            }
        } else {
            request.setAttribute("error", "Email không tồn tại");
        }
        request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
    }

    private void handlePasswordReset(HttpServletRequest request,
            HttpServletResponse response,
            UserDAO userDAO)
            throws ServletException, IOException, ClassNotFoundException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        if (userDAO.resetPassword(token, password)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?message=Password reset successfully");
        } else {
            request.setAttribute("error", "Token không hợp lệ hoặc đã hết hạn");
            request.getRequestDispatcher("/resetPassword.jsp").forward(request, response);
        }
    }
}
