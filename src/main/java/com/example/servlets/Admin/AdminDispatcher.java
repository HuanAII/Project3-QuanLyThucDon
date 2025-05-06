package com.example.servlets.Admin;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo(); 

        if (path == null || path.equals("/") || path.isEmpty()) {
            path = "/thongke"; //mac dinh
        }

        String page;
        switch (path) {
            case "/thucdon":
                page = "/WEB-INF/pages/thucdon.jsp";
                break;
            case "/hoadon":
                page = "/WEB-INF/pages/hoadon.jsp";
                break;
            case "/datban":
                page = "/WEB-INF/pages/datban.jsp";
                break;
            case "/nhanvien":
                page = "/WEB-INF/pages/nhanvien.jsp";
                break;
            case "/khachhang":
                page = "/WEB-INF/pages/khachhang.jsp";
                break;
            case "/hethong":
                page = "/WEB-INF/pages/hethong.jsp";
                break;
            case "/thietlap":
                page = "/WEB-INF/pages/thietlap.jsp";
                break;
            case "/thongke":
            default:
                page = "/WEB-INF/pages/thongke.jsp";
                break;
        }

        request.setAttribute("contentPage", page);
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}