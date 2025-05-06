package com.example.servlets.Admin;

import java.io.IOException;

import com.example.dao.categoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/admin/thucdon/delete-category")
public class Delete_category_servlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String id = req.getParameter("id");
        Boolean result = categoryDAO.removeCategory(id);
        if (result){
            req.setAttribute("susscess","Xoa thanh cong !");
        } else{
            req.setAttribute("error", "Xoa that bai !");
        }
        req.setAttribute("contentPage", "/WEB-INF/pages/danhmuc.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
    
}
