<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Table" %>

<%
    Table table = (Table) request.getAttribute("table");
    if (table == null) {
%>
    <p>Không tìm thấy bàn cần sửa.</p>
<%
    return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa bàn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f6f8fa;
            padding: 30px;
        }

        h2 {
            color: #333;
            margin-bottom: 20px;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            width: 400px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        div {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            color: #333;
            font-weight: bold;
        }

        input[type="number"],
        select {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 15px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h2>Chỉnh sửa thông tin bàn ăn</h2>

<form action="edit-table" method="post">
    <input type="hidden" name="idTable" value="<%= table.getIdTable() %>" />

    <div>
        <label for="tableNumber">Số bàn:</label>
        <input type="number" id="tableNumber" name="tableNumber" value="<%= table.getTableNumber() %>" required />
    </div>

    <div>
        <label for="seats">Số ghế:</label>
        <input type="number" id="seats" name="seats" value="<%= table.getSeats() %>" required />
    </div>

    <div>
        <label for="status">Trạng thái:</label>
        <select id="status" name="status" required>
            <option value="Available" <%= "Available".equals(table.getStatus()) ? "selected" : "" %>>Available</option>
            <option value="Reserved" <%= "Reserved".equals(table.getStatus()) ? "selected" : "" %>>Reserved</option>
            <option value="Occupied" <%= "Occupied".equals(table.getStatus()) ? "selected" : "" %>>Occupied</option>
        </select>
    </div>

    <input type="submit" value="Lưu thay đổi" />
</form>
</body>
</html>
