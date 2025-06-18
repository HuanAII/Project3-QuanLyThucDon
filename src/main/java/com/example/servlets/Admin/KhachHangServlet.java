package com.example.servlets.Admin;

import com.example.dao.UserDAO;
import com.example.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/customers")
public class KhachHangServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra phân quyền admin
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;
        if (role == null || !role.equals("Quản lý")) {
            request.getRequestDispatcher("/WEB-INF/accessDenied.jsp").forward(request, response);
            return;
        }
        List<User> customerList = userDAO.getAllCustomers();
        request.setAttribute("customerList", customerList);
        String editId = request.getParameter("editId");
        if (editId != null) {
            User editUser = null;
            for (User u : customerList) {
                if (u.getId().equals(editId)) {
                    editUser = u;
                    break;
                }
            }
            request.setAttribute("editUser", editUser);
        } else {
            request.setAttribute("editUser", new User());
        }
        
         request.setAttribute("contentPage", "/WEB-INF/pages/khachhang.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;
        if (role == null || !role.equals("Quản lý")) {
            request.getRequestDispatcher("/WEB-INF/accessDenied.jsp").forward(request, response);
            return;
        }

        String deleteId = request.getParameter("deleteId");
        if (deleteId != null) {
            userDAO.deleteCustomer(deleteId);
            response.sendRedirect(request.getContextPath() + "/admin/khachhang");
            return;
        }
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hoVaTen = request.getParameter("HoVaTen");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setHoVaTen(hoVaTen);
        user.setEmail(email);
        user.setSdt(sdt);
        user.setRole("Khách hàng");
        boolean success;
        if (id == null || id.isEmpty()) {
            success = userDAO.addCustomer(user);
            if (!success) {
                request.setAttribute("errorMessage", "Tên đăng nhập hoặc email đã tồn tại!");
                doGet(request, response);
                return;
            }
        } else {
            success = userDAO.updateCustomer(user);
            if (!success) {
                request.setAttribute("errorMessage", "Không thể cập nhật. Tên đăng nhập hoặc email đã tồn tại!");
                doGet(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin/khachhang");
    }
} 