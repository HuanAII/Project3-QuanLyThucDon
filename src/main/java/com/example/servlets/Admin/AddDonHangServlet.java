// package com.example.servlets.Admin;

// import java.io.IOException;
// import java.util.List;
// import java.util.ArrayList;

// import com.example.dao.DetailOrderDAO;
// import com.example.dao.OrderDAO;
// import com.example.dao.productsDAO;
// import com.example.models.Product;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.WebServlet;
// import jakarta.servlet.http.HttpServlet;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @WebServlet("/donhang/add")
// public class AddDonHangServlet extends HttpServlet {
//     private static final long serialVersionUID = 1L;

//     private productsDAO productsDAO = new productsDAO();

//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         request.setCharacterEncoding("UTF-8");
//         response.setContentType("text/html;charset=UTF-8");

//         try {
//             // Lấy dữ liệu từ form
//             String tenKH = request.getParameter("tenKH");
//             String sdt = request.getParameter("sdt");
//             String diaChi = request.getParameter("diaChi");
//             String idTable = request.getParameter("idTable");
//             String status = request.getParameter("status");

//             String[] tenMonArr = request.getParameterValues("tenMon[]");
//             String[] soLuongArr = request.getParameterValues("soLuong[]");

//             double total = 0;
//             List<Product> productList = new ArrayList<>();

//             if (tenMonArr != null && soLuongArr != null) {
//                 for (int i = 0; i < tenMonArr.length; i++) {
//                     String idMon = tenMonArr[i];
//                     int soLuong = Integer.parseInt(soLuongArr[i]);

//                     Product product = productsDAO.getProductByID(idMon);
//                     if (product != null) {
//                         total += soLuong * product.getGia();
//                         productList.add(product);
//                     }
//                 }
//             }
//             int idDonHang = OrderDAO.addOrder(null, total, status, idTable, tenKH, sdt, diaChi);

//             if (idDonHang > 0) {
//                 boolean allSuccess = true;

//                 for (Product p : productList) {
//                     boolean success = DetailOrderDAO.addOrderDetails(idDonHang, p.getTenMon(), p.getSoLuong());
//                     if (!success) {
//                         allSuccess = false;
//                         break;
//                     }
//                 }

//                 if (allSuccess) {
//                     response.sendRedirect(request.getContextPath() + "/admin/datmon?msg=add_success");
//                 } else {
//                     request.setAttribute("error", "Lưu chi tiết đơn hàng thất bại!");
//                     forwardToErrorPage(request, response);
//                 }

//             } else {
//                 request.setAttribute("error", "Thêm đơn hàng thất bại!");
//                 forwardToErrorPage(request, response);
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//             request.setAttribute("error", "Lỗi xử lý đơn hàng: " + e.getMessage());
//             forwardToErrorPage(request, response);
//         }
//     }

//     private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         request.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(request, response);
//     }
// }
