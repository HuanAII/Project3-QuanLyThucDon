<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Category" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.categoryDAO" %>
<%@ page import="java.util.List" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/add_thucdon.css">

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
