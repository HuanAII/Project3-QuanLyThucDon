package com.example.servlets.Admin;

import java.io.IOException;

import com.example.dao.categoryDAO;
import com.example.models.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thucdon/add-category")
public class Amin_themdanhmuc extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_danhmuc = request.getParameter("id_danhmuc");
        String name_danhmuc = request.getParameter("name_danhmuc");
        if (id_danhmuc == null || id_danhmuc.length() != 6) {
            request.setAttribute("error", "ID danh mục phải đúng 6 ký tự.");
            request.setAttribute("contentPage", "/WEB-INF/pages/danhmuc.jsp");
            request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
            return;
        }
        if (!id_danhmuc.matches("DM\\d{4}")) {
            
            request.setAttribute("error", "ID phải có định dạng DMxxxx (4 chữ số).");
            request.setAttribute("contentPage", "/WEB-INF/pages/danhmuc.jsp");
            request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
            return;
        }
        Category category = new Category(id_danhmuc, name_danhmuc);
        
        Boolean result = categoryDAO.addCategory(category);
        if (!result) {
            request.setAttribute("error", "ID danh mục đã tồn tại !!");
        } else {
            request.setAttribute("success", "Thêm danh mục thành công!");
        }
        request.setAttribute("contentPage", "/WEB-INF/pages/danhmuc.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}
