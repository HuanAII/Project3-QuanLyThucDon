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
    <link rel="stylesheet" href="../assests/css/list_menu.css">


</head>
<body>
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
                            <th width="5%">STT</th>
                            <th width="10%">Mã Món</th>
                            <th width="20%">Tên Món</th>
                            <th width="10%">Danh Mục</th>
                            <th width="10%">Giá</th>
                            <th width="12%">Hình Ảnh</th> <!-- Thêm cột hình ảnh -->
                            <th width="22%">Mô Tả</th>
                            <th width="10%">Đơn Vị Tính</th>
                            <th width="13%">Hành động</th>
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
                            <td><span class="badge badge-primary"><%= a.getIdDanhMuc() %></span></td>
                            <td class="price"><%= String.format("%,d", a.getGia()) %> đ</td>
                            <td>
                                <% if (a.getHinhAnh() != null && !a.getHinhAnh().isEmpty()) { %>
                                    <img src="http://localhost:8080/QuanLyThucDon/uploads/<%=a.getHinhAnh() %>" alt="Hình ảnh" style="width:70px; height:50px; object-fit:cover; border-radius:6px; box-shadow:0 2px 8px rgba(0,0,0,0.13);">
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
        // JavaScript pour ajouter des fonctionnalités supplémentaires si nécessaire
        document.addEventListener('DOMContentLoaded', function() {
            // Vérifier s'il y a des messages de succès ou d'erreur à afficher
            // (Vous pouvez implémenter cette partie selon vos besoins)
        });
    </script>
</body>
</html>