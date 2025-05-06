package com.example.servlets.Admin;

import java.io.IOException;

import com.example.dao.categoryDAO;
import com.example.models.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thucdon/edit-category")
public class Edit_category_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String categoryId = req.getParameter("id");


        if (categoryId == null || categoryId.isEmpty()) {
            req.setAttribute("error", "ID danh mục không hợp lệ!");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }


        Category category = categoryDAO.getCategoryById(categoryId);


        if (category == null) {
            req.setAttribute("error", "Không tìm thấy danh mục với ID: " + categoryId);
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }


        req.setAttribute("category", category);
        req.setAttribute("contentPage", "/WEB-INF/pages/edit_category.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }



    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String categoryId = req.getParameter("id");
        String name = req.getParameter("name");


        if (categoryId == null || categoryId.isEmpty() || name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            req.setAttribute("contentPage", "/WEB-INF/pages/edit_category.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }


        Category category = new Category(categoryId, name);
        boolean success = categoryDAO.updateCategory(category);

        if (success) {
            req.setAttribute("contentPage", "/WEB-INF/pages/danhmuc.jsp");
           
        } else {
            req.setAttribute("error", "Cập nhật danh mục thất bại!");
            req.setAttribute("contentPage", "/WEB-INF/pages/edit_category.jsp");
        
        }
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}