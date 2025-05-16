<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.Table" %>

<div class="page-header">
    <h1 class="page-title">Danh sách bàn</h1>
    <a href="${pageContext.request.contextPath}/admin/add-table" class="btn-add">+ Thêm bàn</a>
</div>

<div class="tables-container">
    <%
        List<Table> tables = (List<Table>) request.getAttribute("tables");
        if (tables != null && !tables.isEmpty()) {
            for (Table table : tables) {
    %>
        <div class="table-card">
            <div class="table-header">
                <div class="table-icon">🍽️</div>
                <h3 class="table-number">Bàn số <%= table.getTableNumber() %></h3>
                <p class="table-id">Mã bàn: <%= table.getIdTable() %></p>
            </div>
            <div class="table-details">
                <div class="detail-item"><span class="detail-icon">👥</span>Sức chứa: <%= table.getSeats() %> người</div>
            </div>
            <div class="table-actions">
                <a href="${pageContext.request.contextPath}/admin/edit-table?id=<%= table.getIdTable() %>" class="btn-action btn-edit">✏️ Sửa</a>
                <a href="${pageContext.request.contextPath}/admin/delete-table?id=<%= table.getIdTable() %>" class="btn-action btn-delete">🗑️ Xóa</a>
            </div>
        </div>
    <%
            }
        } else {
    %>
        <div class="empty-state">
            <div class="empty-icon">🪑</div>
            <div class="empty-text">Chưa có bàn nào được thêm!</div>
        </div>
    <%
        }
    %>
</div>
