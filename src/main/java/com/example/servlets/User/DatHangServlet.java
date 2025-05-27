package com.example.servlets.User;


import com.example.dao.CartDAO;
import com.example.dao.DetailOrderDAO;
import com.example.dao.OrderDAO;
import com.example.dao.ReceiptDAO;
import com.example.models.CartItem;
import com.example.models.HoaDon;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


@WebServlet("/DatHangServlet")
public class DatHangServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hoVaTen = request.getParameter("hoTen");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");
        String phuongThucThanhToan = request.getParameter("payment");

        if (soDienThoai.length() < 10 || soDienThoai.length() > 11) {
            request.setAttribute("error", "Số điện thoại không hợp lệ!");
            System.out.println("Số điện thoại không hợp lệ");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }
        
        if (diaChi.length() < 5) {
            request.setAttribute("error", "Địa chỉ không hợp lệ!");
            System.out.println("Địa chỉ không hợp lệ");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
            
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("error", "Giỏ hàng của bạn đang trống!");
            System.out.println("Giỏ hàng đang trống");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }

        Object user = session.getAttribute("user");
        Integer account_id = null;


        if (user != null) {
            account_id = Integer.parseInt(session.getAttribute("account_id").toString());
        }

            double tongTienSauGiam = 0.0;
            double tongTien = 0.0;

            Object giam = session.getAttribute("tongTienSauGiam");
            Object tong = session.getAttribute("tongTien");

            if (giam instanceof Number) tongTienSauGiam = ((Number) giam).doubleValue();
            if (tong instanceof Number) tongTien = ((Number) tong).doubleValue();

            int total = (int) (tongTienSauGiam != 0.0 ? tongTienSauGiam : tongTien);



        int idDonHang = OrderDAO.addOrder(account_id, total, "CHO_XU_LY", null, hoVaTen, soDienThoai, diaChi);

        if (idDonHang == -1) {
            request.setAttribute("error", "Không thể thêm đơn hàng. Vui lòng thử lại!");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }

                    // Thêm hóa đơn

        HoaDon hoaDon = new HoaDon(idDonHang, phuongThucThanhToan, new java.sql.Date(System.currentTimeMillis()), total);
        if (!ReceiptDAO.addHoaDon(hoaDon)) {
            request.setAttribute("error", "Lỗi khi thêm hóa đơn. Vui lòng thử lại!");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }


        boolean isOrderDetailsAdded = true;
        for (CartItem item : cart) {
            if (!DetailOrderDAO.addOrderDetails(idDonHang, item.getIdMon(), item.getSoLuong())) {
                isOrderDetailsAdded = false;
                break;
            }
        }

        if (isOrderDetailsAdded) {
            session.removeAttribute("cart");
            session.removeAttribute("maGiamGia");
            session.removeAttribute("giamGia");
            session.removeAttribute("tongTienSauGiam");
            session.removeAttribute("discount");
            CartDAO.clearCartByUserId(String.valueOf(account_id));

            System.out.println("Dat don hang thanh cong voi id: " + idDonHang);
            session.setAttribute("addedMsg", "Đặt hàng thành công! Chúng tôi sẽ liên hệ với bạn sớm nhất có thể.");
            response.sendRedirect("products.jsp");
        } else {
            request.setAttribute("error", "Đã xảy ra lỗi khi thêm chi tiết đơn hàng. Vui lòng thử lại!");
            System.out.println("Lỗi khi thêm chi tiết đơn hàng");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
        }
    }
}
