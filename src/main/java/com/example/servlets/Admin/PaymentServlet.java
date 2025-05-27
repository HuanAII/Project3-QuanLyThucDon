package com.example.servlets.Admin;

import com.example.dao.OrderDAO;
import com.example.dao.ReceiptDAO;
import com.example.models.HoaDon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/admin/payment")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

  
        String orderId = request.getParameter("orderId");
        String paymentMethod = request.getParameter("paymentMethod");
        double total = Double.parseDouble(request.getParameter("total"));


        HoaDon hoaDon = new HoaDon(
            Integer.parseInt(orderId),
            paymentMethod,
            new Date(System.currentTimeMillis()),
            total
        );

        // Thêm hóa đơn vào database
        if (ReceiptDAO.addHoaDon(hoaDon)) {
            // Cập nhật trạng thái đơn hàng thành "Đã hoàn thành"
            OrderDAO.updateOrderStatus(orderId, "DA_HOAN_THANH");
            response.sendRedirect(request.getContextPath() + "/admin/datmon");
        } else {
            request.setAttribute("error", "Không thể xử lý thanh toán. Vui lòng thử lại!");
            request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
        }
    }
} 