package com.example.servlets.Admin;

import java.io.IOException;
import java.util.List;

import com.example.dao.TableDAO;
import com.example.models.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/tables")
public class TableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Lấy tất cả bàn đã đặt
        List<Table> bookedTables = TableDAO.getBookedTables(); // Giả sử bạn có method này

        // Lấy tất cả bàn chưa đặt
        List<Table> availableTables = TableDAO.getAvailableTables(); // Giả sử bạn có method này

        System.out.println("Booked Tables: " + bookedTables.size());
        System.out.println("Available Tables: " + availableTables.size());

        req.setAttribute("bookedTables", bookedTables);
        req.setAttribute("availableTables", availableTables);

        req.setAttribute("contentPage", "/WEB-INF/pages/list_table.jsp");

        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
