package com.example.servlets.Admin;
import java.io.IOException;

import com.example.dao.productsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thucdon/deleteProduct")
public class Admin_delete_menu extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String menuId = req.getParameter("id");
        req.setCharacterEncoding("UTF-8");
        try {
            Boolean result = productsDAO.deleteProduct(menuId);

            if (result) {
                req.setAttribute("success", "Xóa thực đơn thành công!");
            } else {
                req.setAttribute("error", "Không thể xóa thực đơn với ID: " + menuId);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            req.setAttribute("error", "Đã xảy ra lỗi trong quá trình xóa thực đơn!");
        }

        req.setAttribute("contentPage", "/WEB-INF/pages/mathang.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}