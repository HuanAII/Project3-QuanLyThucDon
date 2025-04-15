package com.example.servlets;

import java.io.IOException;
import java.util.List;

import com.example.dao.productsDAO;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ProductsServlet")
public class ProductsServlet extends HttpServlet {

    

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productsDAO dao = new productsDAO();
        List<Product> list = dao.getAllProducts();
        request.setAttribute("listP", list);

                        // HttpSession session = request.getSession();
                        //     session.setAttribute("id_kh", "1"); 
                        //     session.setAttribute("user", "khanghy");

        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
