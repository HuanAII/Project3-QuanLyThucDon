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



@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String idMon = request.getParameter("idMon");
        int soLuong = 1;

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        String id_kh = (String) session.getAttribute("id_kh");
        

        if (user != null) {
            // Người dùng đã đăng nhập → Lưu vào DB
            productsDAO dao = new productsDAO();
            dao.AddToCart(idMon, id_kh, soLuong);
            System.out.println(">> USER: " + user + " thêm SP: " + idMon);
        } else {
            // Chưa đăng nhập → lưu vào session dạng List<CartItem>
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
                // Lấy thông tin món từ DB
                productsDAO dao = new productsDAO();
                Product mon = dao.getProductByID(idMon);
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

        response.sendRedirect("/ProductsServlet");
    }
}