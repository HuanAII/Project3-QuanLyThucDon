package com.example.servlets.Admin;

import java.io.IOException;
import java.util.List;

import com.example.dao.OrderDAO;
import com.example.models.DonHang;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/datmon")
public class Request_food_booking extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String status = req.getParameter("status");  
        List<DonHang> list;
        if (status!=null && !status.trim().isEmpty()) {
            System.out.println("Status: " + status);
            list = OrderDAO.getOdersByStatus(status);
            System.out.println("List size: " + list.size());
        } else {
            list = OrderDAO.getAllOrders();
        }
        req.setAttribute("listDH", list);
        req.setAttribute("contentPage", "/WEB-INF/pages/request_food_booking.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

@Override 
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
    req.setCharacterEncoding("UTF-8"); 
    resp.setCharacterEncoding("UTF-8"); 

    String orderId = req.getParameter("orderId"); 
    String action = req.getParameter("action"); 
    String status = req.getParameter("status"); 

    String message = null; 

    if (action != null && orderId != null) { 
        if (action.equals("delete")) { 
            boolean result = OrderDAO.deleteOrder(orderId); 
            message = result ? "Xóa đơn hàng thành công!" : "Xóa đơn hàng thất bại."; 
        } else if (action.equals("UpdateStatus")) { 
            boolean result = OrderDAO.updateOrderStatus(orderId , status); 
            System.out.println("orderId: " + orderId); 
            System.out.println("status: " + status); 
            message = result ? "Cập nhật trạng thái thành công!" : "Cập nhật trạng thái thất bại."; 
        } 
    } 

    List<DonHang> list = OrderDAO.getAllOrders(); 
    req.setAttribute("listDH", list); 
    req.setAttribute("message", message); 

    req.setAttribute("contentPage", "/WEB-INF/pages/request_food_booking.jsp"); 
    req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp); 
} 
}