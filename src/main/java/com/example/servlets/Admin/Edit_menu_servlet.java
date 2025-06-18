package com.example.servlets.Admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.example.models.Category;

import com.example.dao.categoryDAO;
import com.example.dao.productsDAO;
import com.example.models.Product;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

@WebServlet("/admin/thucdon/editProduct")
@MultipartConfig
public class Edit_menu_servlet extends HttpServlet {

    private String saveImageAndGetPath(Part filePart, String uploadPath, String currentImage, HttpServletRequest req) throws IOException {
        if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName() == null || filePart.getSubmittedFileName().isEmpty()) {
            return currentImage;
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File file = new File(uploadPath, fileName);

        int count = 1;
        while (file.exists()) {
            int dotIndex = fileName.lastIndexOf(".");
            String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
            String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex);
            fileName = baseName + "_" + count + extension;
            file = new File(uploadPath, fileName);
            count++;
        }

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath());
        }

        if (currentImage != null && !currentImage.isEmpty()) {
            File oldFile = new File(req.getServletContext().getRealPath("/") + currentImage);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        return "uploads/" + file.getName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idMonStr = req.getParameter("id");
        Product product = productsDAO.getProductById(idMonStr);
        List<Category> categories = categoryDAO.getAllCategory();
        req.setAttribute("categories", categories);
        req.setAttribute("product", product);
        req.setAttribute("contentPage", "/WEB-INF/pages/edit_menu.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("idMon");
        Product oldProduct = productsDAO.getProductById(id);

        if (oldProduct == null) {
            req.setAttribute("error", "Không tìm thấy sản phẩm để cập nhật!");
            req.setAttribute("contentPage", "/WEB-INF/pages/mathang.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }

        String name = getOrDefault(req.getParameter("tenMon"), oldProduct.getTenMon());
        String idDanhMuc = getOrDefault(req.getParameter("idDanhMuc"), oldProduct.getIdDanhMuc());
        String mota = getOrDefault(req.getParameter("mota"), oldProduct.getMota());
        String donVi = getOrDefault(req.getParameter("donViTinh"), oldProduct.getDonViTinh());
        int gia = parseOrDefault(req.getParameter("gia"), oldProduct.getGia());
        System.out.println(gia);

    
        String uploadDir = "uploads";
        String uploadPath = getServletContext().getRealPath("/") + uploadDir;
        File uploads = new File(uploadPath);
        if (!uploads.exists()) uploads.mkdir();

        Part filePart = req.getPart("hinhAnh");
        String currentImage = req.getParameter("currentImage");
        String img_path = saveImageAndGetPath(filePart, uploadPath, currentImage, req);

        Product updatedProduct = new Product(id, name, idDanhMuc, gia, img_path, mota, donVi);
        boolean result = productsDAO.updateProduct(updatedProduct);

        if (result) {
            req.setAttribute("success", "Cập nhật sản phẩm thành công!");
        } else {
            req.setAttribute("error", "Cập nhật sản phẩm thất bại!");
        }

        req.setAttribute("product", updatedProduct);
        req.setAttribute("contentPage", "/WEB-INF/pages/mathang.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    private String getOrDefault(String input, String defaultValue) {
        return (input == null || input.trim().isEmpty()) ? defaultValue : input.trim();
    }

    private int parseOrDefault(String input, int defaultValue) {
        if (input == null || input.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
