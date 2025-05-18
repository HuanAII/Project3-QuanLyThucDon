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
    <div class="form-group">
        <label for="idDanhMuc">ID danh mục:</label>
        <input type="text" id="idDanhMuc" name="idDanhMuc" value="${product.idDanhMuc}" placeholder="Nhập ID danh mục" required>
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
                <img src="${pageContext.request.contextPath}/${product.hinhAnh}" alt="Hình ảnh sản phẩm" style="max-width:120px;max-height:120px;">
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
    .form-group {
        margin-bottom: 15px;
    }

    .form-group input,
    .form-group textarea {
        width: 100%;
        border: 1px solid #ddd;
        border-radius: 6px;
        font-size: 14px;
    }

    .readonly-value {
        display: inline-block;
        padding: 8px 12px;
        background-color: #e8e8e8;
        border-radius: 6px;
        font-weight: bold;
    }

    h1 {
        text-align: center;
        margin-bottom: 20px;
        font-size: 24px;
    }

    button {
        display: block;
        width: 100%;
        padding: 10px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 6px;
        font-weight: bold;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s;
    }

    button:hover {
        background-color: #45a049;
    }

    .note {
        font-size: 12px;
        color: #666;
    }

    .image-preview {
        margin-top: 8px;
        margin-bottom: 8px;
    }
</style>