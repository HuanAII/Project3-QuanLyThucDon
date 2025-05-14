package com.example.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {
    private static final String IMAGE_DIR = "D:/PBL3/IMG/";  // Thư mục lưu ảnh

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getPathInfo().substring(1);  // Lấy tên file từ URL

        // Đảm bảo fileName không trống
        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is missing");
            return;
        }

        File imageFile = new File(IMAGE_DIR + fileName);

        // Kiểm tra nếu file tồn tại
        if (imageFile.exists()) {
            String mimeType = getServletContext().getMimeType(imageFile.getName());
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream"); // Đặt loại MIME phù hợp
            Files.copy(imageFile.toPath(), response.getOutputStream());  // Gửi file đến client
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);  // Trả lỗi nếu không tìm thấy file
        }
    }
}


