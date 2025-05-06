package com.example.servlets;

import com.example.dao.productsDAO;
import com.example.models.CartItem;

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
        String email = request.getParameter("email");
        String hoTen = request.getParameter("hoTen");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");
        String ghiChu = request.getParameter("ghiChu");
        String phuongThucThanhToan = request.getParameter("payment");

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("error", "Giỏ hàng của bạn đang trống!");
            System.out.println("Giỏ hàng đang trống");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }

        Object user = session.getAttribute("user");
        Integer id_kh = null;

        productsDAO dao = new productsDAO();

        if (user != null) {
            id_kh = Integer.parseInt(session.getAttribute("id_kh").toString());
        }

        // 🔧 DÙNG TỔNG TIỀN SAU GIẢM (nếu có)
        Double tongTienSauGiam = (Double) session.getAttribute("tongTienSauGiam");
        Double tongTien = (Double) session.getAttribute("tongTien");
        double total = tongTienSauGiam != null ? tongTienSauGiam : (tongTien != null ? tongTien : 0.0);

        // Thêm đơn hàng
        int idDonHang = dao.addOrder(id_kh, total, "Cho xu ly", null, hoTen, soDienThoai, diaChi);

        if (idDonHang == -1) {
            request.setAttribute("error", "Không thể thêm đơn hàng. Vui lòng thử lại!");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }

        boolean isOrderDetailsAdded = true;
        for (CartItem item : cart) {
            if (!dao.addOrderDetails(idDonHang, item.getIdMon(), item.getSoLuong())) {
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

            System.out.println("Dat don hang thanh cong voi id: " + idDonHang);
            response.sendRedirect("products.jsp");
        } else {
            request.setAttribute("error", "Đã xảy ra lỗi khi thêm chi tiết đơn hàng. Vui lòng thử lại!");
            System.out.println("Lỗi khi thêm chi tiết đơn hàng");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
        }
    }
}
