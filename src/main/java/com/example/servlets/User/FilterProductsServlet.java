package com.example.servlets.User;

import java.io.IOException;
import java.util.List;

import com.example.dao.productsDAO;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FilterProductsServlet")
public class FilterProductsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
        String categoryId = request.getParameter("categoryId");

        System.out.println("Category ID được chọn: " + categoryId);

       
        List<Product> productList = productsDAO.getProductsByCategory(categoryId);

       
        request.setAttribute("listP", productList);
        request.setAttribute("selectedCategory", categoryId); 
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
