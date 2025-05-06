<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quên Mật Khẩu</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <div class="container">
        <h2>Quên Mật Khẩu</h2>
        <form action="reset-password" method="post">
            <input type="hidden" name="action" value="request">
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" required>
            </div>
            <button type="submit">Gửi Link Reset</button>
        </form>
    </div>
</body>
</html>
