package com.example.servlets.Admin;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra phân quyền admin
        jakarta.servlet.http.HttpSession session = request.getSession(false);
        String username = null;
        String role = null;
        if (session != null) {
            username = (String) session.getAttribute("user");
            // Lấy role từ DB hoặc session (nếu đã lưu)
            // Ở đây giả sử đã lưu role vào session khi đăng nhập
            // Nếu chưa, cần lấy từ DB
            // Ví dụ: session.setAttribute("role", user.getRole()); khi đăng nhập
            role = (String) session.getAttribute("role");
        }
        if (username == null || role == null || !role.equals("Quản lý")) {
            request.getRequestDispatcher("/WEB-INF/accessDenied.jsp").forward(request, response);
            return;
        }

        String path = request.getPathInfo(); 

        if (path == null || path.equals("/") || path.isEmpty()) {
            path = "/thongke_thucdon.jsp"; //mac dinh
        }

        String page;
        switch (path) {
            case "/thucdon":
                page = "/WEB-INF/pages/thucdon.jsp";
                break;
            case "/hoadon":
                page = "/WEB-INF/pages/hoadon.jsp";
                break;
            case "/datban":
                page = "/WEB-INF/pages/datban.jsp";
                break;
            case "/nhanvien":
                page = "/WEB-INF/pages/nhanvien.jsp";
                break;
            case "/khachhang":
                // Chuyển tiếp tới servlet quản lý khách hàng
                request.getRequestDispatcher("/admin/customers").forward(request, response);
                return;
            case "/hethong":
                page = "/WEB-INF/pages/hethong.jsp";
                break;
            case "/thietlap":
                page = "/WEB-INF/pages/thietlap.jsp";
                break;
            case "/thongke":
            default:
                page = "/WEB-INF/pages/thongke.jsp";
                break;
        }

        request.setAttribute("contentPage", page);
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}