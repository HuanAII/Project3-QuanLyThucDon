package com.example.servlets.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.example.dao.TableDAO;
import com.example.dao.reservationDAO;
import com.example.models.Table;

@WebServlet("/Filter_Table_Servlet")
public class Filter_Table_Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dateParam = req.getParameter("date"); // Ngày định dạng "yyyy-MM-dd"
        java.sql.Date date = null;

        try {
            if (dateParam != null && !dateParam.isEmpty()) {
                LocalDate localDate = LocalDate.parse(dateParam, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                date = java.sql.Date.valueOf(localDate);
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        // Nếu chưa nhập ngày, mặc định lấy ngày hiện tại
        if (date == null) {
            date = new java.sql.Date(System.currentTimeMillis());
            dateParam = new SimpleDateFormat("yyyy-MM-dd").format(date);
        }

        // Lấy đối tượng bàn đã đặt
        List<Table> bookedTables = TableDAO.getBookedTablesByDate(date);

        // Lấy bàn còn trống theo ngày đó
        List<Table> availableTables = TableDAO.getAvailableTablesByDate(date);

        req.setAttribute("bookedTables", bookedTables);
        req.setAttribute("availableTables", availableTables);
        req.setAttribute("selectedDate", dateParam);

        req.setAttribute("contentPage", "/WEB-INF/pages/list_table.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
