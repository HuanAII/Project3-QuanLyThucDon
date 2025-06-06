package com.example.servlets.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.CartDAO;
import com.example.dao.productsDAO;
import com.example.models.CartItem;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String idMon = request.getParameter("idMon");
        int soLuong = 1;

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String account_id = (String) session.getAttribute("account_id");
        

        if (user != null) {
            CartDAO.AddToCart(idMon, account_id, soLuong);
            System.out.println(">> USER: " + user + " them SP: " + idMon);
        } else {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) cart = new ArrayList<>();
            
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getIdMon().equals(idMon)) {
                    item.setSoLuong(item.getSoLuong() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {

                Product mon = productsDAO.getProductByID(idMon);
                if (mon != null) {
                    CartItem item = new CartItem(
                        mon.getIdMon(),
                        mon.getTenMon(),
                        mon.getHinhAnh(),
                        mon.getGia(),
                        soLuong
                    );
                    cart.add(item);
                }
            }
            session.setAttribute("cart", cart);
            System.out.println(">> KHACH chua dang nhap them SP: " + idMon);
        }
        request.getSession().setAttribute("addedMsg", "Đã thêm sản phẩm vào giỏ hàng!");
        response.sendRedirect("products.jsp");
    }
}