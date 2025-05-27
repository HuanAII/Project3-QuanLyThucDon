<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Category" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.categoryDAO" %>
<%@ page import="java.util.List" %>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 20px;
    }

    h2 {
        text-align: center;
        color: #333;
        margin-bottom: 30px;
    }

    form {
        max-width: 600px;
        margin: 0 auto;
        padding: 30px;
        background-color: #ffffff;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    label {
        display: block;
        margin-bottom: 8px;
        font-weight: 600;
        color: #444;
    }

    input[type="text"],
    input[type="number"],
    input[type="file"],
    textarea,
    select {
        width: 100%;
        padding: 10px 12px;
        margin-bottom: 20px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
        transition: border-color 0.3s;
    }

    input[type="text"]:focus,
    input[type="number"]:focus,
    input[type="file"]:focus,
    textarea:focus,
    select:focus {
        border-color: #4CAF50;
        outline: none;
    }

    textarea {
        resize: vertical;
        min-height: 100px;
    }

    input[type="submit"] {
        background-color: #4CAF50;
        color: white;
        padding: 12px 20px;
        font-size: 16px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: background-color 0.3s ease;
        display: block;
        width: 100%;
        font-weight: bold;
    }

    input[type="submit"]:hover {
        background-color: #43a047;
    }
    #toast-notification {
    position: absolute;
    top: 10px;
    right: 30px;
    background-color:rgb(255, 46, 46);
    color: white;
    padding: 12px 20px;
    border-radius: 5px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
    z-index: 9999;
    opacity: 1;
    transition: opacity 0.5s ease-out;
    font-weight: bold;
    }

    #toast-notification-success {
        position: absolute;
    top: 10px;
    right: 30px;
  background-color:rgb(29, 177, 63);
    color: white;
    padding: 12px 20px;
    border-radius: 5px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
    z-index: 9999;
    opacity: 1;
    transition: opacity 0.5s ease-out;
    font-weight: bold;
    }
</style>

<h2>Thêm món ăn</h2>
    <%
        String addedMsg = (String) request.getAttribute("error");
        if (addedMsg != null) {
    %>
        <div id="toast-notification"><%= addedMsg %></div>
    <%
            session.removeAttribute("error");
        }
    %>

        <%
        String success = (String) request.getAttribute("success");
        if (success != null) {
    %>
        <div id="toast-notification-success"><%= success %></div>
    <%
            session.removeAttribute("success");
        }
    %>

<% 
    Product product = (Product) request.getAttribute("product");
    List<Category> listDanhMuc = categoryDAO.getAllCategory();
%>

<form action="${pageContext.request.contextPath}/admin/thucdon/addProduct" method="post" enctype="multipart/form-data">
    <label for="idMon">Mã món ăn</label>
    <input type="text" id="idMon" name="idMon" value="${product.idMon}" required>

    <label for="tenMon">Tên món ăn</label>
    <input type="text" id="tenMon" name="tenMon" value="${product.tenMon}" required>

    <label for="idDanhMuc">Danh mục</label>
        <select id="idDanhMuc" name="idDanhMuc" required>
            <option value="">-- Chọn danh mục --</option>
            <% for (Category danhMuc : listDanhMuc) { 
                boolean isSelected = product != null && danhMuc.getId_danhmuc() == product.getIdDanhMuc();
            %>
                <option value="<%= danhMuc.getId_danhmuc() %>" <%= isSelected ? "selected" : "" %>>
                    <%= danhMuc.getName_danhmuc() %>
                </option>
            <% } %>
        </select>

    <label for="gia">Giá</label>
    <input type="number" id="gia" name="gia" value="${product.gia}" required>

    <label for="donViTinh">Đơn vị tính</label>
    <input type="text" id="donViTinh" name="donViTinh" value="${product.donViTinh}" required>

    <label for="mota">Mô tả</label>
    <textarea id="mota" name="mota" required>${product.mota}</textarea>

    <label for="hinhAnh">Ảnh món ăn</label>
    <input type="file" id="hinhAnh" name="hinhAnh" accept="image/*" 
           <c:if test="${product == null || product.hinhAnh == null}"></c:if>

    <input type="submit" value="Thêm món ăn">
</form>
