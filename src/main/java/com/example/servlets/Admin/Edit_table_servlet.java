package com.example.servlets.Admin;
import java.io.IOException;
import com.example.dao.TableDAO;
import com.example.models.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/admin/edit-table")
public class Edit_table_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Edit_table_servlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idTable = request.getParameter("id");
        Table table = TableDAO.getTableById(idTable);
        request.setAttribute("table", table);
        request.setAttribute("contentPage", "/WEB-INF/pages/edit_table_form.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idTable = request.getParameter("idTable");
        int tableNumber = Integer.parseInt(request.getParameter("tableNumber"));
        int seats = Integer.parseInt(request.getParameter("seats"));
        Table newTable = new Table(idTable, seats, tableNumber);
        boolean result = TableDAO.updateTable(newTable);

        if (result) {
            request.setAttribute("success", "Cập nhật bàn thành công!");
        } else {
            request.setAttribute("error", "Cập nhật bàn thất bại!");
        }
        request.setAttribute("contentPage", "/WEB-INF/pages/tables.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
    
}
