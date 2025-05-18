package com.example.servlets.Admin;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.OrderDAO;
import com.example.models.ChiTietDonHang;
import com.example.models.DonHang;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/donhang/add")
public class AddDonHangServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set UTF-8 để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Lấy dữ liệu từ form
            String tenKH = request.getParameter("tenKH");
            String sdt = request.getParameter("sdt");
            String diaChi = request.getParameter("diaChi");
            String idTable = request.getParameter("idTable");
            String status = request.getParameter("status");
            double total = Double.parseDouble(request.getParameter("total"));
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            //Date date = new Date(); 
            List<ChiTietDonHang> chiTietList = new ArrayList<>();
           // boolean inserted = OrderDAO.insertDonHang(date , );

            // if (inserted) {
            //     response.sendRedirect("success.jsp");
            // } else {
            //     request.setAttribute("error", "Không thêm được đơn hàng.");
            // }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi thêm đơn hàng: " + e.getMessage());
             request.setAttribute("contentPage", "/WEB-INF/pages/request_food_booking.jsp");
            request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
        }
    }
}
