package com.example.servlets.User;

import com.example.dao.reservationDAO;
import com.example.dao.reservation_item_DAO;
import com.example.models.CartItem;
import com.example.models.ReservationItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/BookingTableServlet")
public class bookingTableServlet extends HttpServlet {
   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    int guests = Integer.parseInt(request.getParameter("guests"));
    String date = request.getParameter("date");
    String time = request.getParameter("time");
    String message = request.getParameter("message");

    String foods = "";
    List<CartItem> cart = (List<CartItem>) request.getSession().getAttribute("cart");
    if (cart != null && !cart.isEmpty()) {
        StringBuilder foodsBuilder = new StringBuilder();
        for (CartItem item : cart) {
            foodsBuilder.append(item.getTenMon())
                        .append("(")
                        .append(item.getSoLuong())
                        .append("), ");
        }
        if (foodsBuilder.length() > 2) {
            foodsBuilder.setLength(foodsBuilder.length() - 2); 
        }
        foods = foodsBuilder.toString();
    }

    int account_id = -1;
    Object accObj = request.getSession().getAttribute("account_id");
    if (accObj != null) {
        try {
            account_id = Integer.parseInt(accObj.toString());
        } catch (NumberFormatException e) {
            System.err.println("Không thể parse account_id từ session: " + e.getMessage());
        }
    }

    String resultMessage;

    try {
        int idSaveWaitingReservation = reservationDAO.saveReservationReturnId(account_id, name, phone, date, time, message, foods, guests);

        if (idSaveWaitingReservation != -1) {
            if (cart != null && !cart.isEmpty()) {
                for (CartItem item : cart) {
                    ReservationItem reservation_item = new ReservationItem(
                            idSaveWaitingReservation,
                            item.getIdMon(),
                            item.getTenMon(),
                            item.getSoLuong(),
                            item.getGia()
                    );
                    reservation_item_DAO.save(reservation_item);
                }
                request.getSession().removeAttribute("cart"); 
            }
            resultMessage = "Đặt bàn thành công! Chúng tôi sẽ liên hệ với bạn.";
        } else {
            resultMessage = "Không thể lưu thông tin đặt bàn!";
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        resultMessage = "Lỗi kết nối cơ sở dữ liệu!";
    } catch (SQLException e) {
        e.printStackTrace();
        resultMessage = "Đã xảy ra lỗi trong quá trình xử lý!";
    }

    request.getSession().setAttribute("bookingMessage", resultMessage);
    response.sendRedirect("booking.jsp");
}
}
