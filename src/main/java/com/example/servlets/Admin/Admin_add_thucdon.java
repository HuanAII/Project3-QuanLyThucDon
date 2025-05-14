package com.example.servlets.Admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("idMon");
        String name = req.getParameter("tenMon");
        String id_danhmuc = req.getParameter("idDanhMuc");
        int gia = Integer.parseInt(req.getParameter("gia").trim());
        String mota = req.getParameter("mota");
        String donVi = req.getParameter("donViTinh");

        // Lấy phần ảnh
        Part filePart = req.getPart("hinhAnh");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Đường dẫn cố định nơi lưu trữ ảnh (ví dụ: C:/project-images/uploads/)
        String uploadDir = "D:PBL3/IMG/uploads"; // Đường dẫn đến thư mục uploads trên máy chủ
        
        // Tạo thư mục nếu chưa tồn tại
        File uploadsFolder = new File(uploadDir);
        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }

        File imageFile = new File(uploadsFolder, fileName);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, imageFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi khi lưu ảnh: " + e.getMessage());
            req.setAttribute("contentPage", "/WEB-INF/pages/add_thucdon.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }

        // Dùng đường dẫn tương đối để lưu trong DB (dùng cho hiển thị ảnh)
        String img_path = "uploads/" + fileName; // Dùng "uploads/" để lưu trong DB và hiển thị

        Product product = new Product(id, name, id_danhmuc, gia, img_path, mota, donVi);
        boolean result = productsDAO.addProduct(product);

        if (!result) {
            req.setAttribute("error", "Thêm món ăn thất bại!");
        } else {
            req.setAttribute("success", "Thêm món ăn thành công!");
        }

        req.setAttribute("contentPage", "/WEB-INF/pages/add_thucdon.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
