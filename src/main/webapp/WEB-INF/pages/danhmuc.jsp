<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Category" %>
<%@ page import="com.example.dao.categoryDAO" %>
<%@ page import="java.util.List" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/danhmuc.css">

<div class="category-header">
    <h2>Danh sách danh mục</h2>
    <div class="action-buttons">
        <a  class="btn btn-add" onclick="toggleForm()">+ Thêm danh mục mới</a>
    </div>
</div>

<table class="category-table">
    <thead>
        <tr>
            <th>STT</th>
            <th>Mã danh mục</th>
            <th>Tên danh mục</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<Category> listDanhMuc = categoryDAO.getAllCategory();
            int stt = 1;
            for (Category a : listDanhMuc) {
        %>
        <tr>
            <td><%= stt++ %></td>
            <td><%= a.getId_danhmuc() %></td>
            <td><%= a.getName_danhmuc() %></td>
            <td>
                <a class="btn-edit" href="edit-category?id=<%= a.getId_danhmuc() %>">Sửa</a>
                <a class="btn-delete" href="delete-category?id=<%= a.getId_danhmuc() %>"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">Xóa</a>
            </td>
        </tr>
        <% } %>
    </tbody>
</table>

<div id="addForm">
    <form action="add-category" method="post">
        <div>
            <label for="id">ID danh mục:   </label>
            <input type="text" id="id" name="id_danhmuc" required />
        </div>

        <div>
            <label for="name">Tên danh mục:</label>
            <input type="text" id="name" name="name_danhmuc" required />
        </div>

        <input type="submit" value="Lưu" />
    </form>
</div>

<% if (request.getAttribute("success") != null) { %>
    <div style="color:red"><%= request.getAttribute("success") %></div>
<% } %>

<% if (request.getAttribute("error") != null) { %>
    <div style="color:red"><%= request.getAttribute("error") %></div>
<% } %>

<script>
    function toggleForm() {
        var form = document.getElementById("addForm");
        if (form.style.display === "none" || form.style.display === "") {
            form.style.display = "block";
        } else {
            form.style.display = "none";
        }
    }
</script>
