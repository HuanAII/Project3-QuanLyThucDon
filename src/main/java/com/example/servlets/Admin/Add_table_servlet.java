package com.example.servlets.Admin;

import java.io.IOException;

import com.example.dao.TableDAO;
import com.example.models.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/add-table")
public class Add_table_servlet extends HttpServlet {



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idTable = request.getParameter("idTable");
        int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
        int seats = Integer.parseInt(request.getParameter("seats"));
        Table newTable = new Table(idTable, tableNumber, seats);
        boolean result = TableDAO.addTable(newTable);
        if (result) {
            request.setAttribute("addedMsg", "Thêm bàn thành công!");
        } else {
            request.setAttribute("addedMsg", "Thêm bàn thất bại!");
        }
        request.setAttribute("contentPage", "/WEB-INF/pages/tables.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}
