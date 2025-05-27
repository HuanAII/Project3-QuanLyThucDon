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
        List<Table> bookedTables = TableDAO.getBookedTables(); 
        List<Table> availableTables = TableDAO.getAvailableTables(); 

        System.out.println("Booked Tables: " + bookedTables.size());
        System.out.println("Available Tables: " + availableTables.size());

        req.setAttribute("bookedTables", bookedTables);
        req.setAttribute("availableTables", availableTables);

        req.setAttribute("contentPage", "/WEB-INF/pages/list_table.jsp");

        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String action = req.getParameter("action");

    if (action == null) {
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin action");
        return;
    }

    String message = null;
    try {
        switch (action) {
            case "add": {
                String idTable = req.getParameter("idTable");
                Table checkSame = TableDAO.getTableById(idTable);
                if (checkSame != null) {
                   message = "ID bàn ăn đã tồn tại!";
                    break;
                }

                int seats = Integer.parseInt(req.getParameter("seats"));
                int numberTable = Integer.parseInt(req.getParameter("tableNumber"));

                if (seats <= 0) {
                    message = "Không gian bàn không hợp lệ!";
                    break;
                }
                if (numberTable < 0) {
                    message = "Định danh bàn không hợp lệ!";
                    break;
                }

                boolean saveTable = TableDAO.addTable(new Table(idTable, seats, numberTable));
                message = saveTable ? "Lưu bàn thành công" : "Lưu bàn thất bại";
                break;
            }

            case "edit": {
                System.out.println("haaaaaaaaaaaaaaaaaaaaaaa");
                String idTable = req.getParameter("idTable");
                int tableNumber = Integer.parseInt(req.getParameter("tableNumber"));
                int seats = Integer.parseInt(req.getParameter("seats"));

                boolean updated = TableDAO.updateTable(new Table(idTable, tableNumber, seats));
                message = updated ? "Cập nhật bàn thành công!" : "Cập nhật bàn thất bại!";
                break;
            }

            case "delete": {
                String deleteId = req.getParameter("id");
                boolean success = TableDAO.deleteTable(deleteId);
                message = success ? "Xóa bàn ăn thành công!" : "Xóa bàn ăn không thành công!";
                break;
            }

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hành động không hợp lệ");
                return;
        }
    } catch (Exception e) {
        e.printStackTrace();
        message = "Đã xảy ra lỗi khi xử lý yêu cầu!";
    }

    List<Table> bookedTables = TableDAO.getBookedTables();
    List<Table> availableTables = TableDAO.getAvailableTables();

    req.setAttribute("message", message);
    req.setAttribute("bookedTables", bookedTables);
    req.setAttribute("availableTables", availableTables);
    req.setAttribute("contentPage", "/WEB-INF/pages/list_table.jsp");
    req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
}

}
