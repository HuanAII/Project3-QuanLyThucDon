package com.example.servlets.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.dao.DetailOrderDAO;
import com.example.dao.OrderDAO;
import com.example.dao.productsDAO;
import com.example.models.Product;
import com.example.models.DonHang;
import java.io.IOException;

@WebServlet("/admin/addMoreFood") 
public class Add_more_food extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/admin/datmon");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String[] monIds = request.getParameterValues("tenMon[]");
            String[] quantities = request.getParameterValues("soLuong[]");

            // Kiểm tra dữ liệu đầu vào
            if (monIds == null || quantities == null || monIds.length != quantities.length) {
                response.sendRedirect(request.getContextPath() + "/admin/datmon?error=invalid_data");
                return;
            }

            // Kiểm tra trạng thái đơn hàng
            DonHang donHang = OrderDAO.getOrderById(orderId);
            if (donHang == null) {
                response.sendRedirect(request.getContextPath() + "/admin/datmon?error=order_not_found");
                return;
            }

            if (!"CHO_PHUC_VU".equals(donHang.getStatus())) {
                response.sendRedirect(request.getContextPath() + "/admin/datmon?error=invalid_order_status");
                return;
            }

            double totalAmount = OrderDAO.getTotalByOrderId(orderId);
            boolean hasError = false;

            for (int i = 0; i < monIds.length; i++) {
                String monId = monIds[i];
                int quantity = Integer.parseInt(quantities[i]);

                // Kiểm tra số lượng hợp lệ
                if (quantity <= 0) {
                    hasError = true;
                    continue;
                }

                // Kiểm tra món ăn tồn tại
                Product monAn = productsDAO.getProductByID(monId);
                if (monAn == null) {
                    hasError = true;
                    continue;
                }

                // Kiểm tra món đã tồn tại trong đơn hàng chưa
                if (DetailOrderDAO.isProductInOrder(orderId, monId)) {
                    hasError = true;
                    continue;
                }

                // Thêm món vào đơn hàng
                boolean added = DetailOrderDAO.addOrderDetails(orderId, monId, quantity);
                if (added) {
                    totalAmount += monAn.getGia() * quantity;
                } else {
                    hasError = true;
                }
            }

            if (hasError) {
                response.sendRedirect(request.getContextPath() + "/admin/datmon?error=add_food_failed");
                return;
            }

            // Cập nhật tổng tiền
            boolean updated = OrderDAO.updateTotal(orderId, totalAmount);
            if (!updated) {
                response.sendRedirect(request.getContextPath() + "/admin/datmon?error=update_total_failed");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/admin/datmon?success=add_food_success");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/datmon?error=invalid_number_format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/datmon?error=system_error");
        }
    }
}

