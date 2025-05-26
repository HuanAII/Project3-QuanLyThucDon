package com.example.servlets.Admin;
import java.io.IOException;
import java.util.List;
import com.example.dao.DetailOrderDAO;
import com.example.dao.ReceiptDAO;
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
        List<HoaDon> hoaDonList = ReceiptDAO.getAllHoaDon(); 
        System.out.println("size : "+hoaDonList.size());
        request.setAttribute("hoaDonList", hoaDonList);
        System.out.println("hoaDonList : "+hoaDonList);
        request.setAttribute("contentPage", "/WEB-INF/pages/hoadon.jsp");
        request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
    }


    @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html;charset=UTF-8");

    String action = req.getParameter("action");
    String message = null;

    try {
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin action");
            return;
        }

        switch (action) {
            case "delete":
                int deleteId = Integer.parseInt(req.getParameter("id"));
                boolean deletedDetail = DetailOrderDAO.deleteOrderDetailsByOrderId(deleteId);
                boolean deleted = ReceiptDAO.deleteHoaDon(deleteId);
                message = deleted && deletedDetail ? "Xóa hóa đơn thành công!" : "Xóa hóa đơn không thành công!";
                break;
            default:
                message = "Hành động không hợp lệ!";
                break;
        }

    } catch (Exception e) {
        e.printStackTrace();
        message = "Đã xảy ra lỗi trong quá trình xử lý!";
    }

     List<HoaDon> hoaDonList = ReceiptDAO.getAllHoaDon(); 
        System.out.println("size : "+hoaDonList.size());
        req.setAttribute("hoaDonList", hoaDonList);
    req.setAttribute("message",message );
    req.setAttribute("contentPage", "/WEB-INF/pages/hoadon.jsp");
    req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
}
}

