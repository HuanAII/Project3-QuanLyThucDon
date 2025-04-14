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

        // Lấy thông tin từ form đăng ký
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");
        String role = request.getParameter("role");

        // Kiểm tra mật khẩu xác nhận
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra username có đúng định dạng không (có thể thêm các kiểm tra khác)
        if (username.length() < 3) {
            request.setAttribute("errorMessage", "Tên đăng nhập phải có ít nhất 3 ký tự!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra email có đúng định dạng không
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("errorMessage", "Email không đúng định dạng!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Kiểm tra độ mạnh của mật khẩu (ít nhất 8 ký tự)
        if (password.length() < 8) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 8 ký tự!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Nếu không có role được chọn, mặc định là 'user'
        if (role == null || role.isEmpty()) {
            role = "user";
        }

        // Tạo đối tượng User mới
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAddress(address);
        newUser.setRole(role);

        // Đăng ký user mới
        if (userDAO.registerUser(newUser)) {
            // Đăng ký thành công, chuyển đến trang đăng nhập với thông báo
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            // Đăng ký thất bại, thông báo lỗi (có thể do username hoặc email đã tồn tại)
            request.setAttribute("errorMessage", "Username hoặc Email đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển đến trang đăng ký
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
