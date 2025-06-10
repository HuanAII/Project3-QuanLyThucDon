<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
    <div class="container">
        <div class="image-section">
            <div class="image-text">
            </div>
        </div>
        <div class="form-section">
            <h2>Đăng nhập</h2>
            
            <%-- Hiển thị thông báo thành công --%>
            <% if(request.getAttribute("message") != null) { %>
                <div class="message success-message">
                    <%= request.getAttribute("message") %>
                </div>
            <% } %>
            
            <%-- Hiển thị thông báo lỗi --%>
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="message error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <form action="login" method="post">
                <div class="input-group">
                    <input type="text" id="usernameOrEmail" name="usernameOrEmail" required>
                    <label for="usernameOrEmail">Username hoặc Email</label>
                </div>
                <div class="input-group">
                    <input type="password" id="password" name="password" required>
                    <label for="password">Mật khẩu</label>
                </div>
                <div class="remember-forgot">
                    <div class="remember">
                        <input type="checkbox" id="remember" name="remember">
                        <label for="remember">Ghi nhớ đăng nhập</label>
                    </div>
                    <div class="forgot">
                        <a href="forgotPassword">Quên mật khẩu?</a>
                    </div>
                </div>
                <button type="submit" class="btn">Đăng nhập</button>
                <div class="register">
                    <p>Chưa có tài khoản? <a href="register">Đăng ký ngay</a></p>
                </div>
                <div class="back-home">
                    <p><a href="user?page=trangchu" class="btn-back">Về trang chủ</a></p>
                </div>

            </form>
        </div>
    </div>

    <script>
        function socialLogin(platform) {
            // chuyển hướng đến endpoint xác thực tương ứng
            window.location.href = 'socialLogin?type=' + platform;
        }
        // Tự động điền username hoặc email nếu có cookie
        window.onload = function() {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = cookies[i].trim();
                if (cookie.startsWith('userLogin=')) {
                    var userLogin = cookie.substring('userLogin='.length, cookie.length);
                    document.getElementById('usernameOrEmail').value = userLogin;
                    document.getElementById('remember').checked = true;
                    break;
                }
            }
        };
    </script>
</body>
</html>