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
    private static final String IMAGE_DIR = "C:/PBL3/IMG/"; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getPathInfo().substring(1);  

        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name is missing");
            return;
        }

        File imageFile = new File(IMAGE_DIR + fileName);

        // Kiểm tra nếu file tồn tại
        if (imageFile.exists()) {
            String mimeType = getServletContext().getMimeType(imageFile.getName());
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            Files.copy(imageFile.toPath(), response.getOutputStream());  
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);  
        }
    }
}


