package com.example.servlets.Admin;

import java.io.IOException;
import java.util.List;

import com.example.dao.OrderDAO;
import com.example.models.HoaDon;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/list-hoadon")
public class ListHoaDonServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
        List<HoaDon> hoaDonList = OrderDAO.getAllHoaDon(); 
        System.out.println("size : "+hoaDonList.size());
        request.setAttribute("hoaDonList", hoaDonList);

        request.setAttribute("contentPage", "/WEB-INF/pages/hoadon.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }
}

