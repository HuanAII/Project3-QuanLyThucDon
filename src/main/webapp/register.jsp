<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/register.css">
</head>
<body>
    <div class="container">
        <div class="image-section">
            <div class="image-text">
                <h2>Tham gia cùng chúng tôi</h2>
                <p>Tạo tài khoản để trải nghiệm đầy đủ các tính năng và dịch vụ tuyệt vời.</p>
            </div>
        </div>
        <div class="form-section">
            <h2>Đăng ký tài khoản</h2>
            

            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="message error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <form action="register" method="post" onsubmit="return validateForm()">
                <div class="input-group">
                    <input type="text" id="username" name="username" required>
                    <label for="username">Tên đăng nhập</label>
                </div>
                <div class="input-group">
                    <input type="email" id="email" name="email" required>
                    <label for="email">Email</label>
                </div>

                <div class="input-group">
                    <input type="text" id="HoVaTen" name="HoVaTen" required>
                    <label for="HoVaTen">Họ và Tên</label>
                </div>
                
                <div class="input-group">
                    <input type="text" id="phone" name="phone" pattern="[0-9]{10,15}" required>
                    <label for="phone">Số điện thoại</label>
                </div>
                <div class="input-group">
                    <input type="password" id="password" name="password" required onkeyup="checkPasswordStrength()">
                    <label for="password">Mật khẩu</label>
                    <div class="password-strength">
                        <div class="password-strength-bar" id="passwordStrengthBar"></div>
                    </div>
                    <div class="password-info" id="passwordInfo">
                        Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.
                    </div>
                </div>
                <div class="input-group">
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                    <label for="confirmPassword">Xác nhận mật khẩu</label>
                </div>
                <input type="hidden" name="role" value="Khách hàng">
                
                <button type="submit" class="btn">Đăng ký</button>
                <div class="login-link">
                    <p>Đã có tài khoản? <a href="login">Đăng nhập ngay</a></p>
                </div>
            </form>
        </div>
    </div>

    <script>
        function checkPasswordStrength() {
            var password = document.getElementById('password').value;
            var strengthBar = document.getElementById('passwordStrengthBar');
            var passwordInfo = document.getElementById('passwordInfo');
            
            if (password.length > 0) {
                passwordInfo.style.display = 'block';
            } else {
                passwordInfo.style.display = 'none';
            }
            
            var strength = 0;

            if (password.length >= 8) {
                strength += 25;
            }
            
            if (password.match(/[A-Z]/)) {
                strength += 25;
            }
            
            if (password.match(/[0-9]/)) {
                strength += 25;
            }
            

            if (password.match(/[^A-Za-z0-9]/)) {
                strength += 25;
            }
            
            strengthBar.style.width = strength + '%';
        }
        
        function validateForm() {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            var username = document.getElementById('username').value;
            var email = document.getElementById('email').value;
            
            // Kiểm tra tên đăng nhập
            if (username.length < 3) {
                alert('Tên đăng nhập phải có ít nhất 3 ký tự!');
                return false;
            }
            
            // Kiểm tra email hợp lệ
            var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                alert('Email không hợp lệ!');
                return false;
            }
            
            // Kiểm tra mật khẩu trùng khớp
            if (password !== confirmPassword) {
                alert('Mật khẩu xác nhận không khớp!');
                return false;
            }
            
            // Kiểm tra mật khẩu đủ mạnh
            if (password.length < 8) {
                alert('Mật khẩu phải có ít nhất 8 ký tự!');
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>