package com.example.servlets.Admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.dao.productDao;
import com.example.dao.productsDAO;
import com.example.models.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;



@WebServlet("/admin/thucdon/addProduct")
@MultipartConfig
public class Admin_add_thucdon extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("contentPage", "/WEB-INF/pages/add_thucdon.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("idMon");
        String name = req.getParameter("tenMon");
        String id_danhmuc = req.getParameter("idDanhMuc");
        Integer gia = Integer.parseInt(req.getParameter("gia").trim());
        String mota = req.getParameter("mota");
        String donVi = req.getParameter("donViTinh");

   
        Part filePart = req.getPart("hinhAnh");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();


        String uploadDir = "uploads";
        String uploadPath = getServletContext().getRealPath("/") + uploadDir;


        File uploads = new File(uploadPath);
        if (!uploads.exists()) {
            uploads.mkdir();
        }

        File file = new File(uploads, fileName);
        int count = 1;
        while (file.exists()) {
            String newFileName = fileName.replace(".", "_" + count + ".");
            file = new File(uploads, newFileName);
            count++;
        }
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }
        String img_path = uploadDir + "/" + file.getName();
        Product product = new Product(id, name, id_danhmuc, gia, img_path, mota, donVi);
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