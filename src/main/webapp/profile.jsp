<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.User" %>
<%
    User user = (User) request.getAttribute("user");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin cá nhân</title>
    <style>
        body { font-family: Arial; background: #f7f7f7; }
        .profile-container { max-width: 500px; margin: 40px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px #ccc; }
        h2 { text-align: center; }
        .form-group { margin-bottom: 18px; }
        label { display: block; margin-bottom: 6px; font-weight: bold; }
        input[type="text"], input[type="email"], input[type="password"] { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn { background: #007bff; color: #fff; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .message { color: green; text-align: center; margin-bottom: 15px; }
        .error { color: red; text-align: center; margin-bottom: 15px; }
    </style>
</head>
<body>

        <jsp:include page="menu.jsp" />
        
    <div class="profile-container">
    <h2>Thông tin cá nhân</h2>
    <% if (message != null && !message.isEmpty()) { %>
        <div class="<%= message.contains("thất bại") || message.contains("không khớp") || message.contains("không đúng") ? "error" : "message" %>"><%= message %></div>
    <% } %>
    <form method="post" action="profile">
        <div class="form-group">
            <label for="username">Tên đăng nhập</label>
            <input type="text" id="username" name="username" value="<%= user != null ? user.getUsername() : "" %>" required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="<%= user != null ? user.getEmail() : "" %>" required>
        </div>
        <div class="form-group">
            <label for="hoVaTen">Họ và tên</label>
            <input type="text" id="hoVaTen" name="hoVaTen" value="<%= user != null ? user.getHoVaTen() : "" %>" required>
        </div>
        <div class="form-group">
            <label for="sdt">Số điện thoại</label>
            <input type="text" id="sdt" name="sdt" value="<%= user != null ? user.getSdt() : "" %>" required>
        </div>
        <hr>
        <h3>Đổi mật khẩu</h3>
        <div class="form-group">
            <label for="password">Mật khẩu hiện tại</label>
            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu hiện tại">
        </div>
        <div class="form-group">
            <label for="newPassword">Mật khẩu mới</label>
            <input type="password" id="newPassword" name="newPassword" placeholder="Nhập mật khẩu mới">
        </div>
        <div class="form-group">
            <label for="confirmPassword">Xác nhận mật khẩu mới</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Xác nhận mật khẩu mới">
        </div>
        <div style="text-align:center;">
            <button type="submit" class="btn">Cập nhật thông tin</button>
        </div>
    </form>
</div>
</body>
</html> 