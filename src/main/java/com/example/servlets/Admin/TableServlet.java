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
        List<Table> tables = TableDAO.getAllTables();
        System.out.println("Tables: " + tables.size());
        req.setAttribute("tables", tables);
        req.setAttribute("contentPage", "/WEB-INF/pages/list_table.jsp");

        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
