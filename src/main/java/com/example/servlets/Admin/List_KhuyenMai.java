package com.example.servlets.Admin;

import com.example.dao.KhuyenMaiDAO;
import com.example.models.KhuyenMai;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/list-khuyenmai")
public class List_KhuyenMai extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<KhuyenMai> danhSach = KhuyenMaiDAO.getAll();
            System.out.println("Số lượng khuyến mãi: " + danhSach.size());

            req.setAttribute("listKhuyenMai", danhSach);
            req.setAttribute("message", "Danh sách khuyến mãi");  // Bạn có thể thay đổi hoặc bỏ nếu không cần
            req.setAttribute("contentPage", "/WEB-INF/pages/KhuyenMai.jsp");

            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Lỗi khi lấy danh sách khuyến mãi", e);
        }
    }
}
