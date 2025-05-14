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

        // Lấy categoryId từ request
        String categoryId = request.getParameter("categoryId");

        System.out.println("Category ID được chọn: " + categoryId);

        // Gọi DAO để lấy danh sách sản phẩm theo categoryId
        List<Product> productList = productsDAO.getProductsByCategory(categoryId);

        // Gửi dữ liệu sang JSP
        request.setAttribute("listP", productList);
        request.setAttribute("selectedCategory", categoryId); // nếu cần hiển thị lại đã chọn
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
