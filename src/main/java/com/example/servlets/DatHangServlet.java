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
        // Lấy thông tin từ form thanh toán
        String email = request.getParameter("email");
        String hoTen = request.getParameter("hoTen");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");
        String ghiChu = request.getParameter("ghiChu");
        String phuongThucThanhToan = request.getParameter("payment");

        // Lấy thông tin giỏ hàng từ session
        List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");

        // Kiểm tra giỏ hàng có rỗng không
        if (cart == null || cart.isEmpty()) {
            request.setAttribute("error", "Giỏ hàng của bạn đang trống!");
            System.out.println("Giỏ hàng đang trống");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin người dùng từ session
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        Integer id_kh = null;

        productsDAO dao = new productsDAO();

        // Nếu người dùng đã đăng nhập, lấy id_kh từ session
        if (user != null) {
            id_kh = Integer.parseInt(session.getAttribute("id_kh").toString());
        }

        // Tính tổng tiền
        double total = session.getAttribute("tongTien") != null ? (double) session.getAttribute("tongTien") : 0.0;

        // Thêm đơn hàng và lấy idDonHang vừa được tạo
        int idDonHang = dao.addOrder(id_kh, total, "Cho xu ly", null, hoTen, soDienThoai, diaChi);

        if (idDonHang == -1) {
            request.setAttribute("error", "Không thể thêm đơn hàng. Vui lòng thử lại!");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
            return;
        }

        // Thêm chi tiết đơn hàng
        boolean isOrderDetailsAdded = true;
        for (CartItem item : cart) {
            if (!dao.addOrderDetails(idDonHang, item.getIdMon(), item.getSoLuong())) {
                isOrderDetailsAdded = false;
                break;
            }
        }

        if (isOrderDetailsAdded) {
            // Xóa giỏ hàng sau khi đặt hàng thành công
            session.removeAttribute("cart");

            // Chuyển hướng đến trang xác nhận đặt hàng
            System.out.println("Dat don hang thanh con voi id : " + idDonHang);
            response.sendRedirect("ProductsServlet");
        } else {
            // Nếu có lỗi trong quá trình thêm chi tiết đơn hàng
            request.setAttribute("error", "Đã xảy ra lỗi khi thêm chi tiết đơn hàng. Vui lòng thử lại!");
            System.out.println("Đã xảy ra lỗi khi thêm chi tiết đơn hàng");
            request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
        }
    }
}
