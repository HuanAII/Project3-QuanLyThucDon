<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Chỉnh sửa sản phẩm</h1>
<form action="${pageContext.request.contextPath}/admin/thucdon/editProduct" method="post" enctype="multipart/form-data">
    <!-- ID sản phẩm -->
    <input type="hidden" id="idMon" name="idMon" value="${product.idMon}">
    <div class="form-group">
        <label>ID sản phẩm:</label>
        <span class="readonly-value">${product.idMon}</span>
        <p class="note">ID sản phẩm không thể thay đổi.</p>
    </div>

    <!-- Tên sản phẩm -->
    <div class="form-group">
        <label for="tenMon">Tên sản phẩm:</label>
        <input type="text" id="tenMon" name="tenMon" value="${product.tenMon}" placeholder="Nhập tên sản phẩm" required>
    </div>

    <!-- ID danh mục -->
<!-- Danh mục -->
<div class="form-group">
    <label for="idDanhMuc">Danh mục:</label>
    <select id="idDanhMuc" name="idDanhMuc" required>
        <option value="" disabled>-- Chọn danh mục --</option>
        <c:forEach var="category" items="${categories}">
            <option value="${category.id_danhmuc}" 
                ${category.id_danhmuc == product.idDanhMuc ? "selected" : ""}>
                ${category.name_danhmuc}
            </option>
        </c:forEach>
    </select>
</div>


    <!-- Giá -->
    <div class="form-group">
        <label for="gia">Giá:</label>
        <input type="number" id="gia" name="gia" value="${product.gia}" min="0" step="0.01" placeholder="Nhập giá sản phẩm" required>
    </div>

    <!-- Hình ảnh -->
    <div class="form-group">
        <label for="hinhAnh">Hình ảnh:</label>
        <input type="file" id="hinhAnh" name="hinhAnh" accept="image/*">
        <input type="hidden" name="currentImage" value="${product.hinhAnh}">
        <c:if test="${not empty product.hinhAnh}">
            <div class="image-preview">
                <img src="http://localhost:8080/QuanLyThucDon/uploads/${product.hinhAnh}" alt="Hình ảnh sản phẩm" style="max-width:120px;max-height:120px;">
            </div>
        </c:if>
    </div>

    <!-- Mô tả -->
    <div class="form-group">
        <label for="mota">Mô tả:</label>
        <textarea id="mota" name="mota" rows="4" placeholder="Nhập mô tả sản phẩm" required>${product.mota}</textarea>
    </div>

    <!-- Đơn vị tính -->
    <div class="form-group">
        <label for="donViTinh">Đơn vị tính:</label>
        <input type="text" id="donViTinh" name="donViTinh" value="${product.donViTinh}" placeholder="Nhập đơn vị tính" required>
    </div>

    <!-- Nút Lưu -->
    <button type="submit">Lưu thay đổi</button>
</form>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f9f9f9;
        margin: 0;
        padding: 0;
    }

    h1 {
        text-align: center;
        color: #333;
        margin-top: 30px;
        font-size: 28px;
    }

    form {
        max-width: 600px;
        margin: 30px auto;
        background-color: #ffffff;
        padding: 25px 30px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        border-radius: 12px;
    }

    .form-group {
        margin-bottom: 20px;
    }

    .form-group label {
        display: block;
        font-weight: bold;
        margin-bottom: 6px;
        color: #555;
    }

    .form-group input[type="text"],
    .form-group input[type="number"],
    .form-group input[type="file"],
    .form-group textarea {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        font-size: 15px;
        box-sizing: border-box;
        transition: border-color 0.3s;
    }

    .form-group input:focus,
    .form-group textarea:focus {
        border-color: #4CAF50;
        outline: none;
    }

    .readonly-value {
        display: inline-block;
        padding: 8px 12px;
        background-color: #eee;
        border-radius: 8px;
        font-weight: bold;
        font-size: 15px;
    }

    .note {
        font-size: 12px;
        color: #777;
        margin-top: 4px;
    }

    .image-preview {
        margin-top: 10px;
    }

    .image-preview img {
        border-radius: 8px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
    }

    button[type="submit"] {
        width: 100%;
        padding: 12px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 8px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    button[type="submit"]:hover {
        background-color: #43a047;
    }
</style>
