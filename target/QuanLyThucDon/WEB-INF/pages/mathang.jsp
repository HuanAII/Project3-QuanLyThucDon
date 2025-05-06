
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.productDao" %>
<%@ page import="java.util.List" %>
<style>
    .product-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid #eee;
    }

    .btn-add {
        background-color: #4CAF50;
        color: white;
        padding: 8px 15px;
        border-radius: 4px;
        text-decoration: none;
        font-weight: bold;
        font-size: 0.9em;
        display: inline-flex;
        align-items: center;
        transition: background-color 0.2s;
        border: none;
        cursor: pointer;
    }

    .btn-add:hover {
        background-color: #45a049;
    }

    .btn-action {
        padding: 5px 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.8em;
    }

    .btn-edit {
        background-color: #007bff;
        color: white;
    }

    .btn-edit:hover {
        background-color: #0056b3;
    }

    .btn-delete {
        background-color: #ff4d4f;
        color: white;
    }

    .btn-delete:hover {
        background-color: #e63946;
    }

    .product_img {
        width: 100px;
        height: 80px;
        object-fit: cover;
        border-radius: 8px;
        transition: transform 0.3s ease;
    }

    .product_img:hover {
        transform: scale(1.05);
    }
</style>

<div class="product-header">
    <h2>Danh sách thực đơn</h2>
    <button class="btn-add" onclick="window.location.href='${pageContext.request.contextPath}/admin/thucdon/addProduct'">
        + Thêm thực đơn mới
    </button>
</div>

<table cellpadding="10" cellspacing="0">
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
            List<Product> listThucDon = productDao.getAllProducts();
            int stt = 1;
            for (Product a : listThucDon) {
                String hinhAnh = "https://drive.google.com/uc?export=view&id=" + a.getHinhAnh();
        %>
        <tr>
            <td><%= stt++ %></td>
            <td><%= a.getIdMon() %></td>
            <td><%= a.getTenMon() %></td>
            <td><%= a.getIdDanhMuc() %></td>
            <td><%= a.getGia() %></td>
            <td>
                <img src="${pageContext.request.contextPath}/assets/img/mon_an.jpg" class="product_img" alt="Hình ảnh">
            </td>
            <td><%= a.getMota() %></td>
            <td><%= a.getDonViTinh() %></td>
            <td>
                <!-- Nút Sửa -->
                <button class="btn-action btn-edit" onclick="window.location.href='${pageContext.request.contextPath}/admin/thucdon/editProduct?id=<%= a.getIdMon() %>'">
                    Sửa
                </button>
                <!-- Nút Xóa -->
                <form action="${pageContext.request.contextPath}/admin/thucdon/deleteProduct" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= a.getIdMon() %>">
                    <button type="submit" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc muốn xóa món này?');">
                        Xóa
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>