package com.example.servlets.Admin;

import com.example.dao.DatBanDAO;
import com.example.dao.OrderDAO;
import com.example.dao.ReceiptDAO;
import com.example.dao.reservationDAO;
import com.example.models.HoaDon;
import com.example.models.reservation;

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

  
     String  orderId = request.getParameter("orderId");
        String paymentMethod = request.getParameter("paymentMethod");
        double total = Double.parseDouble(request.getParameter("total"));
        String idTable = request.getParameter("idTable");
        System.out.println("Tran Van Huan " + idTable);


        HoaDon hoaDon = new HoaDon(
            Integer.parseInt(orderId),
            paymentMethod,
            new Date(System.currentTimeMillis()),
            total
        );

        if (ReceiptDAO.addHoaDon(hoaDon)) {
            OrderDAO.updateOrderStatus(orderId, "DA_HOAN_THANH");
            Date date = OrderDAO.getDateOfOrderById(Integer.parseInt(orderId));

            DatBanDAO.updateTableStatus(idTable, date, "DA_THANH_TOAN");
            response.sendRedirect(request.getContextPath() + "/admin/datmon");
        } else {
            request.setAttribute("error", "Không thể xử lý thanh toán. Vui lòng thử lại!");
            request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
        }
    }
} 