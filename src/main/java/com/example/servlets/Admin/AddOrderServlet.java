package com.example.servlets.Admin;

import com.example.dao.OrderDAO;
import com.example.dao.productsDAO;
import com.example.dao.DetailOrderDAO;
import com.example.dao.DatBanDAO; 
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/admin/addOrder")
public class AddOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String tenKH = request.getParameter("tenKH");
        String sdt = request.getParameter("sdt");
        String idTable = request.getParameter("idTable");
        String diaChi = "Tại Nhà Hàng"; 
        String status = request.getParameter("status");

        double total = 0.0;

        String[] tenMonArray = request.getParameterValues("tenMon[]");
        String[] soLuongArray = request.getParameterValues("soLuong[]");

        if (tenMonArray != null && soLuongArray != null) {
            for (int i = 0; i < tenMonArray.length; i++) {
                String idMon = tenMonArray[i];
                int soLuong = Integer.parseInt(soLuongArray[i]);
                Product monAn = productsDAO.getProductByID(idMon);

                total += soLuong * monAn.getGia();
            }
        }

        // Tạo đơn hàng
        int idDonHang = OrderDAO.addOrder(null, total, status, idTable, tenKH, sdt, diaChi);

        if (idDonHang != -1) {
            // Thêm chi tiết đơn hàng
            if (tenMonArray != null && soLuongArray != null) {
                for (int i = 0; i < tenMonArray.length; i++) {
                    String idMon = tenMonArray[i];
                    int soLuong = Integer.parseInt(soLuongArray[i]);
                    DetailOrderDAO.addOrderDetails(idDonHang, idMon, soLuong);
                }
            }

            Date today = Date.valueOf(LocalDate.now());
            boolean updated = DatBanDAO.updateTableStatus(idTable, today, "DANG_SU_DUNG");
            if (!updated) {
                DatBanDAO.insertDatBan(idTable, tenKH, sdt, today, "DANG_SU_DUNG");
            }

            response.sendRedirect(request.getContextPath() + "/admin/datmon");
        } else {
            request.setAttribute("error", "Không thể thêm đơn hàng. Vui lòng thử lại!");
            request.setAttribute("contentPage", "/WEB-INF/pages/Request_food_booking.jsp");
            request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
        }
    }
}
