// package com.example.servlets.Admin;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.annotation.WebServlet;
// import java.io.IOException;
// import java.util.List;

// import com.example.dao.OrderDAO;
// import com.example.dao.TableDAO;
// import com.example.dao.reservationDAO;
// import com.example.models.ReservationItem;
// import com.example.models.Table;
// import com.example.models.reservation;

// @WebServlet("/admin/Waiting_booking_table")
// public class ReservationHistory extends HttpServlet {
//     private static final long serialVersionUID = 1L;

//     @Override
//     protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//             throws ServletException, IOException {

//         List<reservation> listRes = reservationDAO.


//         System.out.println("Table size : "+ listTable.size());
//         req.setAttribute("contentPage", "/WEB-INF/pages/Table_reservation.jsp");
//         req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
//     }

//     @Override
// protected void doPost(HttpServletRequest request, HttpServletResponse response)
//         throws ServletException, IOException {
//     request.setCharacterEncoding("UTF-8");
//     response.setCharacterEncoding("UTF-8");

//     String message = null;

//     try {
//         int reservationId = Integer.parseInt(request.getParameter("reservationId"));
//         String action = request.getParameter("action");

//         if (action != null && !action.isEmpty()) {
//             switch (action) {
//                 case "delete":
//                     System.out.println("Deleting reservation with ID: " + reservationId);
//                     boolean deleteResult = reservationDAO.deleteWaitingReservationById(reservationId);
//                     message = deleteResult ? "Xóa yêu cầu thành công!" : "Xóa yêu cầu thất bại.";
//                     break;

//                 case "confirm":
//                     reservation reservation = reservationDAO.getWaitingReservationByIdS(reservationId);
//                     String tableId = request.getParameter("tableId");

//                     if (reservation != null && tableId != null && !tableId.isEmpty()) {
//                         List<ReservationItem> listReservationItem = reservationDAO.getReservationItemsById(reservationId);

//                         boolean orderResult = OrderDAO.addOrderFromWaitingReservation(reservation, listReservationItem, tableId);
//                         if (orderResult) {
//                             boolean deleteWaitingRes = reservationDAO.deleteWaitingReservationById(reservationId);
//                             boolean saveReservation = reservationDAO.saveReservationFromWaitingReservation(reservation, tableId);
//                             System.out.println(saveReservation);
//                             message = deleteWaitingRes && saveReservation
//                                 ? "Cập nhật và tạo đơn hàng thành công!" 
//                                 : "Xác nhận không thành công !";
//                         } else {
//                             message = "Tạo đơn hàng thất bại.";
//                         }
//                     } else {
//                         message = "Không tìm thấy yêu cầu hoặc chưa chọn bàn.";
//                     }
//                     break;

//                 default:
//                     message = "Hành động không hợp lệ.";
//                     break;
//             }
//         } else {
//             message = "Thiếu hành động.";
//         }
//     } catch (NumberFormatException e) {
//         message = "ID đặt bàn không hợp lệ.";
//         e.printStackTrace();
//     } catch (Exception e) {
//         message = "Lỗi xử lý yêu cầu: " + e.getMessage();
//         e.printStackTrace();
//     }
//     List<reservation> listReservation = reservationDAO.getAllWaitingReservations();
//     request.setAttribute("listReservation", listReservation);

//     List<Table> listTable = TableDAO.getAllTables();
//     request.setAttribute("listTable", listTable);

//     request.setAttribute("message", message);
//     request.setAttribute("contentPage", "/WEB-INF/pages/Table_reservation.jsp");
//     request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
// }
// }
