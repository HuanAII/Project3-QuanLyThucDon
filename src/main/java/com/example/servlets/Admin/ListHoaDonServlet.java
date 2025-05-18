package com.example.servlets.Admin;

import java.io.IOException;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.xpath.operations.Or;

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
        System.out.println("hoaDonList : "+hoaDonList);
        request.setAttribute("contentPage", "/WEB-INF/pages/hoadon.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            
            String id = req.getParameter("id");
            
            OrderDAO.deleteFullOrder(id);
            
            
            System.out.println("Deleted order with ID: " + id);
            resp.sendRedirect(req.getContextPath() + "/admin/list-hoadon");
            return;
        }
        super.doPost(req, resp);
    }
}

