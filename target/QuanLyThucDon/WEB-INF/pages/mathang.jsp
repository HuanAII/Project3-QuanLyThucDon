<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.productsDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thực đơn</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/list_menu.css">
</head>
<body>

    <%
        String addedMsg = (String) request.getAttribute("addedMsg");
        if (addedMsg != null) {
    %>
        <div id="toast-notification"><%= addedMsg %></div>
    <%
            session.removeAttribute("addedMsg");
        }
    %>

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2><i class="fas fa-utensils"></i> Danh sách thực đơn</h2>
                <button class="btn-add" onclick="window.location.href='${pageContext.request.contextPath}/admin/thucdon/addProduct'">
                    <i class="fas fa-plus"></i> Thêm thực đơn mới
                </button>
            </div>
            
            <div class="table-responsive">
                <table>
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã Món</th>
                            <th>Tên Món</th>
                            <th>Danh Mục</th>
                            <th>Giá</th>
                            <th>Hình Ảnh</th>
                            <th>Mô Tả</th>
                            <th>Đơn Vị Tính</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Product> listThucDon = productsDAO.getAllProducts();
                            if (listThucDon != null && !listThucDon.isEmpty()) {
                                int stt = 1;
                                for (Product a : listThucDon) {
                        %>
                        <tr>
                            <td><%= stt++ %></td>
                            <td class="product-id"><%= a.getIdMon() %></td>
                            <td class="text-left product-name"><%= a.getTenMon() %></td>
                            <td><%= a.getIdDanhMuc() %></td>
                            <td class="price"><%= String.format("%,d", a.getGia()) %> VNĐ</td>
                            <td>
                                <% if (a.getHinhAnh() != null && !a.getHinhAnh().isEmpty()) { %>
                                    <img src="http://localhost:8080/QuanLyThucDon/uploads/<%= a.getHinhAnh() %>" alt="Hình ảnh" style="width:70px; height:50px; object-fit:cover; border-radius:6px;">
                                <% } else { %>
                                    <span style="color:#888;">Không có ảnh</span>
                                <% } %>
                            </td>
                            <td>
                                <div class="description">
                                    <%= a.getMota() != null && !a.getMota().isEmpty() ? a.getMota() : "<em>Chưa có mô tả</em>" %>
                                </div>
                            </td>
                            <td><%= a.getDonViTinh() %></td>
                            <td>
                                <div class="actions">
                                    <button class="btn-action btn-edit" onclick="window.location.href='${pageContext.request.contextPath}/admin/thucdon/editProduct?id=<%= a.getIdMon() %>'">
                                        <i class="fas fa-edit"></i> Sửa
                                    </button>
                                    <form action="${pageContext.request.contextPath}/admin/thucdon/deleteProduct" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="<%= a.getIdMon() %>">
                                        <button type="submit" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc muốn xóa món này?');">
                                            <i class="fas fa-trash-alt"></i> Xóa
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <% } 
                        } else { %>
                        <tr>
                            <td colspan="9" class="no-data">
                                <i class="fas fa-info-circle"></i> Không có món ăn nào trong thực đơn
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
        <script>
            // Tự động ẩn thông báo sau 3 giây
            setTimeout(function () {
                var toast = document.getElementById('toast-notification');
                if (toast) {
                    toast.style.display = 'none';
                }
            }, 3000);
        </script>
</body>
</html>
