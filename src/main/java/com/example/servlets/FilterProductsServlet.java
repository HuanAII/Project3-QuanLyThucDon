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

@WebServlet("/FilterProductsServlet")
public class FilterProductsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tất cả các giá trị được chọn từ các checkbox (có thể có nhiều giá trị)
        String[] price = request.getParameterValues("price");  
        String sort = request.getParameter("sort");  
        String[] type = request.getParameterValues("type");  
        System.out.println("=== Giá trị người dùng đã chọn ===");

        System.out.print("Price: ");
        if (price != null) {
            for (String p : price) {
                System.out.print(p + " ");
            }
        } else {
            System.out.print("Không có");
        }
        System.out.println();

        System.out.println("Sort: " + (sort != null ? sort : "Không có"));

        System.out.print("Type: ");
        if (type != null) {
            for (String t : type) {
                System.out.print(t + " ");
            }
        } else {
            System.out.print("Không có");
        }
        System.out.println();
        
        // Gọi DAO để lấy danh sách sản phẩm theo các điều kiện lọc
        productsDAO dao = new productsDAO();
        List<Product> list = dao.getAllProductsBySort(price, sort, type);
        
        // Đặt kết quả vào request để gửi tới trang JSP
        request.setAttribute("listP", list);
        System.out.println("Số lượng sản phẩm: " + (list != null ? list.size() : "null"));
        // Forward kết quả tới trang JSP
        request.getRequestDispatcher("/pages/products.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
