package com.example.servlets.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.CartDAO;
import com.example.models.CartItem;
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
        String account_id = (String) session.getAttribute("account_id");

        if (user != null) {
            System.out.println(">> USER: " + user + " dang xem gio hang");
            List<CartItem> cartFromDB = CartDAO.getCartByUserId(account_id);
            System.out.println(">> So luong sp trong gio hang: " + cartFromDB.size());

            session.setAttribute("cart", cartFromDB);

        } else {
            System.out.println(">> KHACH chua dang nhap dang xem gio hang");
            List<CartItem> cartFromSession = (List<CartItem>) session.getAttribute("cart");
            if (cartFromSession == null) cartFromSession = new ArrayList<>();

            System.out.println(">> So luong sp trong gio hang: " + cartFromSession.size());
            session.setAttribute("cart", cartFromSession);
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart"); 
        if (cart == null) {
            cart = new ArrayList<>(); 
        }

        CartItem cartItem = new CartItem();
        System.out.println(">> Cart: " + cart);
        int tongTien = cartItem.tinhTongTien(cart); 
        session.setAttribute("tongTien", tongTien); 
        System.out.println(">> Tong tien: " + tongTien);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
