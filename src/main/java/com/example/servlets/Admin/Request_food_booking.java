package com.example.servlets.Admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.dao.DatBanDAO;
import com.example.dao.OrderDAO;
import com.example.dao.TableDAO;
import com.example.dao.productsDAO;
import com.example.dao.reservationDAO;
import com.example.models.DonHang;
import com.example.models.Product;
import com.example.models.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/datmon")
public class Request_food_booking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String status = req.getParameter("status");
        List<DonHang> list;

        if (status != null && !status.trim().isEmpty()) {
            list = OrderDAO.getOdersByStatus(status);
        } else {
            list = OrderDAO.getAllOrders();
        }

        for (DonHang dh : list) {
            Date date = (Date) dh.getDate();
            Time time = reservationDAO.getGioDatByOrderIdDateAndTable(date ,dh.getIdTable());
            System.out.println("Date: " + date + ", Time: " + time);
            if (time != null) {
                dh.setTime(time);
            } else {
                dh.setTime(new Time(0, 0, 0)); 
            }
            System.out.println("Updated DonHang: " + dh.getTime());
        }
  
        req.setAttribute("listDH", list);

        Date today = Date.valueOf(LocalDate.now());
        System.out.println("Today date: " + today);

        try {
            List<Table> emptyTables = TableDAO.getAvailableTablesByDate(today);
            System.out.println("Kich thuoc emptytable "+emptyTables.size());
            req.setAttribute("emptyTable", emptyTables);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("emptyTable", new ArrayList<>());
        }
        List<Product> listMon = productsDAO.getAllProducts();
        req.setAttribute("listMon", listMon);
        req.setAttribute("contentPage", "/WEB-INF/pages/request_food_booking.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String orderId = req.getParameter("orderId");
        String action = req.getParameter("action");
        String status = req.getParameter("status");
   

        String message = null;

        if (action != null && orderId != null) {
            if (action.equals("delete")) {
                 Date date = OrderDAO.getDateOfOrderById(Integer.parseInt(orderId));
                String idTable = OrderDAO.getIdTableByOrderId(orderId);
                DatBanDAO.updateTableStatus(idTable, date, "DA_HUY");
                boolean result = OrderDAO.deleteOrder(orderId);
        

                message = result ? "Xóa đơn hàng thành công!" : "Xóa đơn hàng thất bại.";
            } else if (action.equals("UpdateStatus")) {
                if (status == "DA_HOAN_THANH"){
  
                }
                boolean result = OrderDAO.updateOrderStatus(orderId, status);
                message = result ? "Cập nhật trạng thái thành công!" : "Cập nhật trạng thái thất bại.";
            } 

        }
        List<DonHang> list = OrderDAO.getAllOrders();
        req.setAttribute("listDH", list);
        req.setAttribute("message", message);

        Date today = Date.valueOf(LocalDate.now());

        try {
            
            List<Table> emptyTables = TableDAO.getAvailableTablesByDate(today);
            System.out.println("today date: " + today);
            for ( Table table : emptyTables) {
                System.out.println("Table ID: " + table.getIdTable() + ", Seats: " + table.getSeats() + ", Number: " + table.getTableNumber());
            }

            req.setAttribute("emptyTable", emptyTables);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("emptyTable", Collections.emptyList());
        }
        List<Product> listMon = productsDAO.getAllProducts();
         req.setAttribute("listMon", listMon);

        req.setAttribute("contentPage", "/WEB-INF/pages/request_food_booking.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}

