package com.example.servlets.User;

import java.io.IOException;
import java.util.List;

import com.example.dao.CartDAO;
import com.example.models.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idMon = request.getParameter("idMon");
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String account_id = (String) session.getAttribute("account_id");

        if (idMon == null || action == null) {
            response.sendRedirect("CartServlet");
            return;
        }

        if (user != null) {
            switch (action) {
                case "plus":
                    CartDAO.updateCartItem(idMon, account_id, 1);
                    break;
                case "minus":
                    CartDAO.updateCartItem(idMon, account_id, -1);
                    CartDAO.removeIfQuantityZero(idMon, account_id);
                    break;
                case "remove":
                    CartDAO.deleteCartItem(idMon, account_id);
                    break;
            }
        } else {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (int i = 0; i < cart.size(); i++) {
                    CartItem item = cart.get(i);
                    if (item.getIdMon().equals(idMon)) {
                        switch (action) {
                            case "plus":
                                item.setSoLuong(item.getSoLuong() + 1);
                                break;
                            case "minus":
                                int newQty = item.getSoLuong() - 1;
                                if (newQty <= 0) cart.remove(i);
                                else item.setSoLuong(newQty);
                                break;
                            case "remove":
                                cart.remove(i);
                                break;
                        }
                        break;
                    }
                }
                session.setAttribute("cart", cart);
            }
        }

        response.sendRedirect("CartServlet");
    }
}

