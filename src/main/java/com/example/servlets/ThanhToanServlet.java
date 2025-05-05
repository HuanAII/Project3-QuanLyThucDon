package com.example.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.productsDAO;
import com.example.models.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ThanhToanServlet")
public class ThanhToanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String id_kh = (String) session.getAttribute("id_kh");
                System.out.println("user: " + user+"dang xem trang thanh toán");
       

        List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");
        request.setAttribute("cart", cart);
        // Chuyển tới trang giỏ hàng JSP
        request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý khi có yêu cầu POST (nếu cần)
        doGet(request, response);
    }
   
}