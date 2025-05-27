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

@WebServlet("/admin/xoa-khuyenmai")
public class Delete_KhuyenMai extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String proId = req.getParameter("pro_id");
        if (proId == null || proId.trim().isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu mã sản phẩm để xóa.");
            return;
        }

        try {
            boolean success = KhuyenMaiDAO.delete(proId);
            if (success) {
                // Xóa thành công thì redirect về danh sách khuyến mãi
                res.sendRedirect(req.getContextPath() + "/admin/list-khuyenmai");
            } else {
                // Xóa không thành công, load lại danh sách và hiện trang danh sách với lỗi
                List<KhuyenMai> danhSach = KhuyenMaiDAO.getAll();
                req.setAttribute("listKhuyenMai", danhSach);
                req.setAttribute("errorMessage", "Xóa khuyến mãi thất bại.");
                req.setAttribute("contentPage", "/WEB-INF/pages/KhuyenMai.jsp");
                req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu xảy ra lỗi, cũng load danh sách và forward với thông báo lỗi
            List<KhuyenMai> danhSach = KhuyenMaiDAO.getAll();
            req.setAttribute("listKhuyenMai", danhSach);
            req.setAttribute("errorMessage", "Xóa khuyến mãi thất bại do lỗi hệ thống.");
            req.setAttribute("contentPage", "/WEB-INF/pages/KhuyenMai.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, res);
        }
    }
}
