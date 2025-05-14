package com.example.servlets.User;

import java.io.IOException;
import java.util.List;

import com.example.dao.UserDAO;
import com.example.models.CartItem;
import com.example.models.User;

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
    System.out.println("user: " + user + " đang xem trang thanh toán");
    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    request.setAttribute("cart", cart);


    Integer tongTienObj = (Integer) session.getAttribute("tongTien");
    int tongTien = (tongTienObj != null) ? tongTienObj : 0;

    if (tongTien <= 0) {
        request.setAttribute("thongBao", "Giỏ hàng của bạn đang trống hoặc có lỗi. Vui lòng thêm sản phẩm trước khi thanh toán.");
        request.getRequestDispatcher("cart.jsp").forward(request, response);
        return;
    }
    String userValue = (String) session.getAttribute("user"); 
    if (userValue != null) {
        UserDAO userDAO = new UserDAO();
        User userInfo = userDAO.getUserByUsernameOrEmail(userValue);
        System.out.println("Thông tin người dùng: " + userInfo);
        session.setAttribute("userInfo", userInfo);
}

    request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
   
}