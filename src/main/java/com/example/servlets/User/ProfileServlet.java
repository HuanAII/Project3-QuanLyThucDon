package com.example.servlets.User;

import com.example.dao.UserDAO;
import com.example.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (session != null) ? (String) session.getAttribute("account_id") : null;
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        User user = userDAO.getUserById(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userId = (session != null) ? (String) session.getAttribute("account_id") : null;
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String hoVaTen = request.getParameter("hoVaTen");
        String sdt = request.getParameter("sdt");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        User user = userDAO.getUserById(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setHoVaTen(hoVaTen);
        user.setSdt(sdt);
        boolean infoUpdated = userDAO.updateUser(user);
        boolean passwordUpdated = false;
        String message = "";

        // Xử lý cập nhật mật khẩu nếu có
        if (password != null && !password.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                message = "Mật khẩu mới và xác nhận không khớp!";
            } else {
                passwordUpdated = userDAO.changePassword(userId, password, newPassword);
                if (!passwordUpdated) {
                    message = "Mật khẩu cũ không đúng hoặc lỗi khi đổi mật khẩu!";
                } else {
                    message = "Cập nhật thông tin và mật khẩu thành công!";
                }
            }
        } else if (infoUpdated) {
            message = "Cập nhật thông tin thành công!";
        } else {
            message = "Cập nhật thông tin thất bại!";
        }

        // Set thông báo và chuyển hướng về trang profile
        request.setAttribute("message", message);
        request.setAttribute("user", user);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
} 