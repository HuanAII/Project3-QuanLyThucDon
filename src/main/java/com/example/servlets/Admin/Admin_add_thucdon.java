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
        String mota = req.getParameter("mota");
        String donVi = req.getParameter("donViTinh");

        int gia;
        try {
            gia = Integer.parseInt(req.getParameter("gia").trim());
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Giá món ăn không hợp lệ!");
            forwardWithProduct(req, resp, new Product(id, name, id_danhmuc, 0, "", mota, donVi));
            return;
        }

        // Kiểm tra ID hợp lệ
        if (id == null || id.length() != 6) {
            req.setAttribute("error", "ID món ăn phải có đúng 6 ký tự!");
            forwardWithProduct(req, resp, new Product(id, name, id_danhmuc, gia, "", mota, donVi));
            return;
        }

        if (gia < 0) {
            req.setAttribute("error", "Giá món ăn phải lớn hơn hoặc bằng 0!");
            forwardWithProduct(req, resp, new Product(id, name, id_danhmuc, gia, "", mota, donVi));
            return;
        }

        // Kiểm tra ID đã tồn tại chưa
        Product existing = productsDAO.getProductByID(id);
        if (existing != null) {
            req.setAttribute("error", "ID món ăn đã tồn tại!");
            forwardWithProduct(req, resp, new Product(id, name, id_danhmuc, gia, "", mota, donVi));
            return;
        }

        // Xử lý ảnh
        Part filePart = req.getPart("hinhAnh");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadDir = "C:/PBL3/IMG/uploads";  // Đường dẫn tuyệt đối đến thư mục ảnh

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
            forwardWithProduct(req, resp, new Product(id, name, id_danhmuc, gia, "", mota, donVi));
            return;
        }

        String img_path = "uploads/" + fileName;

        // Thêm sản phẩm
        Product product = new Product(id, name, id_danhmuc, gia, img_path, mota, donVi);
        boolean result = productsDAO.addProduct(product);

        if (result) {
            req.setAttribute("addedMsg", "Thêm món ăn thành công!");
        
        } else {
            req.setAttribute("error", "Thêm món ăn thất bại!");
            req.setAttribute("product", product);
        }

        req.setAttribute("contentPage", "/WEB-INF/pages/mathang.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    private void forwardWithProduct(HttpServletRequest req, HttpServletResponse resp, Product product) throws ServletException, IOException {
        req.setAttribute("product", product);
        req.setAttribute("contentPage", "/WEB-INF/pages/add_thucdon.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
