package com.example.servlets;

import java.io.IOException;

import com.example.dao.UserDAO;
import com.example.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin từ form đăng nhập
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember");

        // Kiểm tra đăng nhập bằng username hoặc email
        User user = null;

        // Kiểm tra xem input là email hay username
        if (usernameOrEmail.contains("@")) {
            // Đăng nhập bằng email
            user = userDAO.checkLoginByEmail(usernameOrEmail, password);
        } else {
            // Đăng nhập bằng username
            user = userDAO.checkLoginByUsername(usernameOrEmail, password);
        }

        if (user != null) {
            // Đăng nhập thành công, tạo session
            HttpSession session = request.getSession();
            String username = user.getUsername().toString();
            String userId = user.getId().toString();
            session.setAttribute("user", username);
            session.setAttribute("account_id", userId);
            session.setAttribute("role", user.getRole());

            // Nếu "Ghi nhớ đăng nhập" được chọn, tạo cookie
            if (rememberMe != null) {
                Cookie userCookie = new Cookie("userLogin", usernameOrEmail);
                userCookie.setMaxAge(24 * 60 * 60); 
                response.addCookie(userCookie);
            } else {
            
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("userLogin")) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            break;
                        }
                    }
                }
            }

            // Chuyển hướng dựa trên vai trò của người dùng
            if ("Quản lý".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/thongke_thucdon");
            } else {
                // Người dùng thông thường
                response.sendRedirect("index.jsp");
            }
        } else {
            // Đăng nhập thất bại, quay lại trang đăng nhập với thông báo lỗi
            request.setAttribute("errorMessage", "Username/Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển đến trang đăng nhập
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
