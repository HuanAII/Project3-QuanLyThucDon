package com.example.servlets.Admin;

import java.io.IOException;

import com.example.dao.TableDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/delete-table")
public class Delete_table_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Delete_table_servlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableId = request.getParameter("id");
        boolean result = TableDAO.deleteTable(tableId);
        if (result) {
            request.setAttribute("success", "Xóa bàn thành công!");
        } else {
            request.setAttribute("error", "Xóa bàn thất bại!");
        }
        request.setAttribute("contentPage", "/WEB-INF/pages/tables.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}
