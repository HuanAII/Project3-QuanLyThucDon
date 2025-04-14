package com.example.servlets;

import java.io.IOException;
import java.util.List;

import com.example.dao.categoryDAO;
import com.example.models.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thucdon/*")
public class Admin_thucdon_controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null || path.equals("/") || path.isEmpty()) {
            path = "/mathang";
        }

        String contentPage;

        switch (path) {
            case "/danhmuc":
                List<Category> categories = categoryDAO.getAllCategory();
                contentPage = "/WEB-INF/pages/danhmuc.jsp";
                req.setAttribute("listOfCategories", categories);
                break;
            case "/mathang":
                contentPage = "/WEB-INF/pages/mathang.jsp";
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

        req.setAttribute("contentPage", contentPage);
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
