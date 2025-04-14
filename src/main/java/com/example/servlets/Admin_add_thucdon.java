package com.example.servlets;

import java.io.IOException;

import com.example.dao.productDao;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thucdon/addProduct")
public class Admin_add_thucdon  extends  HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //lay du lieu duoc post len
        String id = req.getParameter("idMon");
        String name = req.getParameter("tenMon");
        String id_danhmuc = req.getParameter("idDanhMuc");
        Double gia = Double.parseDouble(req.getParameter("gia"));
        String mota = req.getParameter("mota");
        String donVi = req.getParameter("donViTinh");
        String img_path = req.getParameter("hinhAnh");
        Product product = new Product(id,name , id_danhmuc, gia ,img_path, mota, donVi);

        Boolean result = productDao.addProduct(product);
        if (!result) {
            req.setAttribute("error", "Thêm danh mục thất bại!");
        } else {
            req.setAttribute("success", "Thêm danh mục thành công!");
        }
        req.setAttribute("contentPage", "/WEB-INF/pages/add_thucdon.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
