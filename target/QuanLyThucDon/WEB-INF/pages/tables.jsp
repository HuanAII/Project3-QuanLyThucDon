
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Table" %>
<%@ page import="com.example.dao.TableDAO" %>
<%@ page import="java.util.List" %>
<style>
    .category-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid #eee;
    }

    .category-header h2 {
        margin: 0;
        color: #333;
        font-size: 1.5em;
        font-weight: 600;
    }

    .action-buttons {
        display: flex;
        gap: 10px;
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
    }

    .btn-add:hover {
        background-color: #45a049;
    }

    .category-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    }

    .category-table th, .category-table td {
        padding: 12px 15px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    .category-table th {
        background-color: #f8f9fa;
        font-weight: bold;
        color: #555;
    }

    .category-table tr:hover {
        background-color: #f5f5f5;
    }

    .btn-edit, .btn-delete {
        display: inline-block;
        padding: 6px 10px;
        border-radius: 4px;
        text-decoration: none;
        margin: 0 5px;
        font-size: 0.9em;
        transition: opacity 0.2s;
    }

    .btn-edit {
        background-color: #2196F3;
        color: white;
    }

    .btn-delete {
        background-color: #f44336;
        color: white;
    }

    .btn-edit:hover, .btn-delete:hover {
        opacity: 0.9;
    }

    #addForm {
        display: none;
        margin-top: 20px;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: #f9f9f9;
        max-width: 500px;
    }

    #addForm form {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    #addForm label {
        font-weight: bold;
        margin-bottom: 5px;
        color: #333;
    }

    #addForm input[type="text"] {
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        font-size: 1em;
    }

    #addForm input[type="submit"] {
        width: 100px;
        padding: 10px;
        border: none;
        border-radius: 5px;
        background-color: #4CAF50;
        color: white;
        font-weight: bold;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    #addForm input[type="submit"]:hover {
        background-color: #45a049;
    }
</style>


<div class="category-header">
    <h2>Danh sách bàn ăn</h2>
    <div class="action-buttons">
        <a class="btn btn-add" onclick="toggleForm()">+ Thêm bàn mới</a>
    </div>
</div>

<table class="category-table">
    <thead>
        <tr>
            <th>STT</th>
            <th>Mã bàn</th>
            <th>Số bàn</th>
            <th>Số ghế</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
    </thead>
    <tbody>
        <%
        int stt = 1;
        List<Table> tables = TableDAO.getAllTables();
        if (tables == null || tables.isEmpty()) {
            out.println("<tr><td colspan='6'>Không có bàn nào trong danh sách.</td></tr>");
        }
            for (Table t : tables) {
        %>
        <tr>
            <td><%= stt++ %></td>
            <td><%= t.getIdTable() %></td>
            <td><%= t.getTableNumber() %></td>
            <td><%= t.getSeats() %></td>
            <td><%= t.getStatus() %></td>
            <td>
                <a class="btn-edit" href="edit-table?id=<%= t.getIdTable() %>">Sửa</a>
                
                <form action="delete-table" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= t.getIdTable() %>" />
                    <button class="btn-delete" type="submit" onclick="return confirm('Bạn có chắc chắn muốn xóa bàn này?');">Xóa</button>
                </form>

            </td>
        </tr>
        <% } %>
    </tbody>
</table>


<div id="addForm">
    <form action="add-table" method="post">
        <div>
            <label for="idTable">ID bàn:</label>
            <input type="text" id="idTable" name="idTable" required />
        </div>
        <div>
            <label for="tableNumber">Số bàn:</label>
            <input type="number" id="tableNumber" name="tableNumber" required />
        </div>
        <div>
            <label for="seats">Số người:</label>
            <input type="number" id="seats" name="seats" required />
        </div>
        <div>
            <label for="status">Trạng thái:</label>
            <select id="status" name="status" required>
                <option value="Available">Available</option>
                <option value="Reserved">Reserved</option>
                <option value="Occupied">Occupied</option>
            </select>
        </div>
        <input type="submit" value="Lưu" />
    </form>
</div>

<% if (request.getAttribute("success") != null) { %>
    <div style="color: green"><%= request.getAttribute("success") %></div>
<% } %>

<% if (request.getAttribute("error") != null) { %>
    <div style="color: red"><%= request.getAttribute("error") %></div>
<% } %>

<script>
    function toggleForm() {
        var form = document.getElementById("addForm");
        form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
    }
</script>
