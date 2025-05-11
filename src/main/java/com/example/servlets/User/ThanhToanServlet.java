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
    String id_kh = (String) session.getAttribute("id_kh");
    System.out.println("user: " + user + " đang xem trang thanh toán");

    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    request.setAttribute("cart", cart);

    // Ép kiểu và kiểm tra tổng tiền
    Double tongTienObj = (Double) session.getAttribute("tongTien");
    double tongTien = (tongTienObj != null) ? tongTienObj : 0;

    if (tongTien <= 0) {
        request.setAttribute("thongBao", "Giỏ hàng của bạn đang trống hoặc có lỗi. Vui lòng thêm sản phẩm trước khi thanh toán.");
        request.getRequestDispatcher("cart.jsp").forward(request, response);
        return;
    }
    String userValue = (String) session.getAttribute("user"); // Có thể là username hoặc email
    if (userValue != null) {
        UserDAO userDAO = new UserDAO();
        User userInfo = userDAO.getUserByUsernameOrEmail(userValue);
        session.setAttribute("userInfo", userInfo);
}


    // Nếu hợp lệ, chuyển đến trang thanh toán
    request.getRequestDispatcher("thanhToan.jsp").forward(request, response);
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý khi có yêu cầu POST (nếu cần)
        doGet(request, response);
    }
   
}