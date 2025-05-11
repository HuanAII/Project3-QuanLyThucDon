package com.example.servlets.User;

import java.io.IOException;
import java.util.List;



import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import com.example.dao.productsDAO;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ProductsServlet")
public class ProductsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         HttpSession session = request.getSession();
        session.setAttribute("user", "KhangHy");
        session.setAttribute("id_kh", "1");
        System.out.println("username: " + session.getAttribute("user")+"dang dang nhap");

        productsDAO dao = new productsDAO();
        List<Product> list = dao.getAllProducts();
        request.setAttribute("listP", list);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
