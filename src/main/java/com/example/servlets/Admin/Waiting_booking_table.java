package com.example.servlets.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

import com.example.dao.OrderDAO;
import com.example.dao.TableDAO;
import com.example.dao.reservationDAO;
import com.example.models.ReservationItem;
import com.example.models.Table;
import com.example.models.reservation;

@WebServlet("/admin/Waiting_booking_table")
public class Waiting_booking_table extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<reservation> list = reservationDAO.getAllWaitingReservations();
        req.setAttribute("listReservation", list);

        List<Table> listTable = TableDAO.getAllTables();
        req.setAttribute("listTable", listTable);

        req.setAttribute("contentPage", "/WEB-INF/pages/Table_reservation.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));
        String action = request.getParameter("action");
        String message = null;

        if (action != null && !action.isEmpty()) {
            if (action.equals("delete")) {
                System.out.println("Delete reservation with ID: " + reservationId);
                boolean result = reservationDAO.deleteWaitingReservationById(reservationId);
                message = result ? "Xóa yêu cầu thành công!" : "Xóa yêu cầu thất bại.";
            } 
            else if (action.equals("Confirm")) {
                reservation reservation = reservationDAO.getWaitingReservationByIdS(reservationId);
                String idban = request.getParameter("idTable");

                if (reservation != null && idban != null && !idban.isEmpty()) {
                    List<ReservationItem> listReservationItem = reservationDAO.getReservationItemsById(reservationId);
                    
                    boolean orderResult = OrderDAO.addOrderFromWaitingReservation(reservation, listReservationItem, idban);

                    if (orderResult) {
                        boolean deleteResult = reservationDAO.deleteWaitingReservationById(reservationId);
                        message = deleteResult 
                            ? "Cập nhật và tạo đơn hàng thành công!" 
                            : "Tạo đơn hàng thành công nhưng xóa yêu cầu thất bại.";
                    } else {
                        message = "Tạo đơn hàng thất bại.";
                    }

                } else {
                    message = "Không tìm thấy yêu cầu hoặc chưa chọn bàn.";
                }
            }
        }

        List<reservation> list = reservationDAO.getAllWaitingReservations();
        request.setAttribute("listReservation", list);

        List<Table> listTable = TableDAO.getAllTables();
        request.setAttribute("listTable", listTable);

        request.setAttribute("message", message);
        request.setAttribute("contentPage", "/WEB-INF/pages/Table_reservation.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}
