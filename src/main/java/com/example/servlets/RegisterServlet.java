package com.example.servlets;

import java.io.IOException;

import com.example.dao.UserDAO;
import com.example.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String HoVaTen = request.getParameter("HoVaTen");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");
        String phone = request.getParameter("phone");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (username == null || username.trim().length() < 3) {
            request.setAttribute("errorMessage", "Tên đăng nhập phải có ít nhất 3 ký tự!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("errorMessage", "Email không đúng định dạng!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (password.length() < 8) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 8 ký tự!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (phone == null || !phone.matches("\\d{10,15}")) {
            request.setAttribute("errorMessage", "Số điện thoại không hợp lệ! Vui lòng nhập từ 10 đến 15 chữ số.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (role == null || role.isEmpty()) {
            role = "Khách hàng";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setSdt(phone);  
        newUser.setHoVaTen(HoVaTen);

        if (userDAO.registerUser(newUser)) {
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Username hoặc Email đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
