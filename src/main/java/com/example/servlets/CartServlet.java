package com.example.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.productsDAO;
import com.example.models.CartItem;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String id_kh = (String) session.getAttribute("id_kh");

        if (user != null) {
            // Người DÙNG đã đăng nhập — Lấy dữ liệu từ DB
            System.out.println(">> USER: " + user + " dang xem gio hang");
            productsDAO dao = new productsDAO();
            List<CartItem> cartFromDB = dao.getCartByUserId(id_kh);
            session.setAttribute("cart", cartFromDB);

        } else {
            // Người dùng KHÔNG đăng nhập — lấy giỏ hàng từ session
            System.out.println(">> KHACH chua dang nhap dang xem gio hang");
            List<CartItem> cartFromSession = (List<CartItem>) session.getAttribute("cart");
            if (cartFromSession == null) cartFromSession = new ArrayList<>();
            session.setAttribute("cart", cartFromSession);
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart"); // Lấy giỏ hàng từ session
        if (cart == null) {
            cart = new ArrayList<>(); // Nếu giỏ hàng null, khởi tạo giỏ hàng rỗng
        }

        CartItem cartItem = new CartItem();
        double tongTien = cartItem.tinhTongTien(cart); // Tính tổng tiền từ giỏ hàng
        session.setAttribute("tongTien", tongTien); // Lưu tổng tiền vào session
        System.out.println(">> Tong tien: " + tongTien);

        // Chuyển tới trang giỏ hàng JSP
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
