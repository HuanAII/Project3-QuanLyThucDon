package com.example.servlets.Admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.dao.productDao;
import com.example.models.Product;

import jakarta.servlet.http.Part;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/thucdon/editProduct")
public class edit_menu extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Product product = productDao.getProductById(id);
        if (product == null) {
            req.setAttribute("error", "Không tìm thấy sản phẩm với ID: " + id);
            req.setAttribute("contentPage", "/WEB-INF/pages/edit_menu.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("product", product);
        req.setAttribute("contentPage", "/WEB-INF/pages/edit_menu.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("idMon");
        if (id == null || id.trim().isEmpty()) {
            req.setAttribute("error", "ID sản phẩm không hợp lệ!");
            req.setAttribute("contentPage", "/WEB-INF/pages/edit_menu.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }
        Product oldProduct = productDao.getProductById(id);
        if (oldProduct == null) {
            req.setAttribute("error", "Không tìm thấy sản phẩm để cập nhật!");
            req.setAttribute("contentPage", "/WEB-INF/pages/edit_menu.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }

        String name = req.getParameter("tenMon");
        if (name == null || name.trim().isEmpty()) {
            name = oldProduct.getTenMon();
        }

        String idDanhMuc = req.getParameter("idDanhMuc");
        if (idDanhMuc == null || idDanhMuc.trim().isEmpty()) {
            idDanhMuc = oldProduct.getIdDanhMuc();
        }

        String giaStr = req.getParameter("gia");
        Double gia;
        if (giaStr == null || giaStr.trim().isEmpty()) {
            gia = oldProduct.getGia();
        } else {
            try {
                gia = Double.parseDouble(giaStr.trim());
            } catch (NumberFormatException e) {
                gia = oldProduct.getGia();
            }
        }

        String mota = req.getParameter("mota");
        if (mota == null || mota.trim().isEmpty()) {
            mota = oldProduct.getMota();
        }

        String donVi = req.getParameter("donViTinh");
        if (donVi == null || donVi.trim().isEmpty()) {
            donVi = oldProduct.getDonViTinh();
        }

        String uploadDir = "uploads";
        String uploadPath = getServletContext().getRealPath("/") + uploadDir;
        File uploads = new File(uploadPath);
        if (!uploads.exists()) {
            uploads.mkdir();
        }

        String currentImage = req.getParameter("currentImage");
        String img_path = currentImage;

        Part filePart = req.getPart("hinhAnh");
        boolean isImageChanged = false;

        if (filePart != null && filePart.getSize() > 0
            && filePart.getSubmittedFileName() != null
            && !filePart.getSubmittedFileName().isEmpty()) {

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File file = new File(uploads, fileName);
            int count = 1;
            while (file.exists()) {
                int dotIndex = fileName.lastIndexOf(".");
                String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
                String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex);
                String newFileName = baseName + "_" + count + extension;
                file = new File(uploads, newFileName);
                count++;
            }
            // Lưu file mới
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath());
            }
            img_path = uploadDir + "/" + file.getName();
            isImageChanged = true;

            // Xóa ảnh cũ nếu có
            if (currentImage != null && !currentImage.isEmpty()) {
                File oldFile = new File(getServletContext().getRealPath("/") + currentImage);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }

        Product product = new Product(id, name, idDanhMuc, gia, img_path, mota, donVi);
        Boolean result = productDao.updateProduct(product);

        if (!result) {
            req.setAttribute("error", "Cập nhật sản phẩm thất bại!");
        } else {
            req.setAttribute("success", "Cập nhật sản phẩm thành công!");
        }
        // Gửi lại dữ liệu sản phẩm mới nhất cho trang edit
        req.setAttribute("product", productDao.getProductById(id));
        req.setAttribute("contentPage", "/WEB-INF/pages/edit_menu.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}