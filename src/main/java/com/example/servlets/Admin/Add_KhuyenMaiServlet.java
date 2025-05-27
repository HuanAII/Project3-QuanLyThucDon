package com.example.servlets.Admin;

import com.example.dao.KhuyenMaiDAO;
import com.example.models.KhuyenMai;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/admin/them-khuyenmai")
public class Add_KhuyenMaiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            double discount = Double.parseDouble(req.getParameter("discount"));
            String maGiamGia = req.getParameter("ma_giam_gia");

            // Chuyển từ String sang LocalDate
            LocalDate startDate = LocalDate.parse(req.getParameter("start_date"));
            LocalDate endDate = LocalDate.parse(req.getParameter("end_date"));

            KhuyenMai km = new KhuyenMai();
            km.setDiscount(discount);
            km.setMaGiamGia(maGiamGia);
            km.setStartDate(startDate);
            km.setEndDate(endDate);

            KhuyenMaiDAO.insert(km); // ID tự tăng, không cần set

            resp.sendRedirect("list-khuyenmai");
        } catch (Exception e) {
            throw new ServletException("Lỗi thêm khuyến mãi", e);
        }
    }
}
