<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký tài khoản</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: #000000;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow: hidden;
            position: relative;
        }

        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: radial-gradient(circle at center, rgba(40, 40, 40, 0.5) 0%, rgba(0, 0, 0, 0.9) 70%);
            z-index: -1;
        }

        .container {
            max-width: 850px;
            width: 100%;
            display: flex;
            background: rgba(15, 15, 15, 0.6);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.5), 0 0 15px rgba(255, 255, 255, 0.1);
            overflow: hidden;
            height: 600px;
            border: 1px solid rgba(255, 255, 255, 0.1);
        }

        .image-section {
            flex: 1;
            background: url('images/register-background.jpg') center/cover;
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 30px;
        }

        .image-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(0, 0, 0, 0.7), rgba(15, 15, 15, 0.8));
        }

        .image-text {
            position: relative;
            z-index: 1;
            color: white;
            text-align: center;
        }

        .image-text h2 {
            font-size: 2.3rem;
            margin-bottom: 15px;
            font-weight: 700;
        }

        .image-text p {
            font-size: 1rem;
            line-height: 1.6;
        }

        .form-section {
            flex: 1;
            background: #0f0f0f;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            color: #e0e0e0;
        }

        .form-section h2 {
            font-size: 1.8rem;
            margin-bottom: 30px;
            color: #ffffff;
            text-align: center;
            text-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .input-group {
            margin-bottom: 20px;
            position: relative;
        }

        .input-group input {
            width: 100%;
            padding: 15px;
            font-size: 1rem;
            border: 1px solid #333;
            background-color: rgba(30, 30, 30, 0.6);
            color: #e0e0e0;
            border-radius: 8px;
            outline: none;
            transition: all 0.3s ease;
        }

        .input-group label {
            position: absolute;
            top: 50%;
            left: 15px;
            transform: translateY(-50%);
            color: #aaa;
            pointer-events: none;
            transition: all 0.3s ease;
        }

        .input-group input:focus,
        .input-group input:valid {
            border-color: #4a4a4a;
            background-color: rgba(40, 40, 40, 0.8);
        }

        .input-group input:focus + label,
        .input-group input:valid + label {
            top: -5px;
            left: 10px;
            font-size: 0.8rem;
            padding: 0 5px;
            background-color: #0f0f0f;
            color: #aaaaaa;
        }

        .btn {
            width: 100%;
            padding: 15px;
            background: linear-gradient(to right, #333333, #111111);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 8px;
            color: white;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-top: 10px;
        }

        .btn:hover {
            background: linear-gradient(to right, #222222, #000000);
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5), 0 0 10px rgba(255, 255, 255, 0.1);
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9rem;
            color: #aaaaaa;
        }

        .login-link a {
            color: #cccccc;
            text-decoration: none;
            font-weight: 600;
        }

        .login-link a:hover {
            color: #ffffff;
            text-decoration: underline;
        }

        .message {
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
        }

        .error-message {
            background-color: rgba(255, 0, 0, 0.1);
            color: #ff6b6b;
            border: 1px solid #ff6b6b;
        }

        .password-strength {
            height: 5px;
            margin-top: 5px;
            background: #333;
            border-radius: 5px;
            overflow: hidden;
        }

        .password-strength-bar {
            height: 100%;
            width: 0;
            background: linear-gradient(to right, #ff4b4b, #ffaf4b, #52ff4b);
            transition: width 0.3s;
        }

        .password-info {
            display: none;
            margin-top: 5px;
            font-size: 0.8rem;
            color: #aaa;
        }

        @media (max-width: 768px) {
            .container {
                flex-direction: column;
                height: auto;
                max-width: 95%;
            }

            .image-section {
                display: none;
            }

            .form-section {
                padding: 30px 20px;
            }
        }
    </style>
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