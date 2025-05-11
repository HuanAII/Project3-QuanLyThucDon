<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.models.User" %>
<%
    // Kiểm tra phiên đăng nhập
    User currentUser = null;
    try {
        currentUser = (User) session.getAttribute("user");
        if(currentUser == null) {
            response.sendRedirect("login");
            return;
        }
    } catch (Exception e) {
        response.sendRedirect("login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: #0a0a0a;
            color: #e0e0e0;
            min-height: 100vh;
        }

        .navbar {
            background-color: #111111;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.5);
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: white;
        }

        .navbar .nav-items {
            display: flex;
            gap: 20px;
        }

        .navbar .nav-items a {
            color: #aaaaaa;
            text-decoration: none;
            transition: color 0.3s;
        }

        .navbar .nav-items a:hover {
            color: white;
        }

        .navbar .nav-items .logout {
            background: rgba(255, 255, 255, 0.1);
            padding: 8px 15px;
            border-radius: 5px;
        }

        .navbar .nav-items .logout:hover {
            background: rgba(255, 255, 255, 0.2);
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px;
        }

        .welcome-card {
            background: rgba(30, 30, 30, 0.6);
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 40px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        .welcome-card h1 {
            font-size: 2.5rem;
            margin-bottom: 15px;
            color: white;
        }

        .welcome-card p {
            font-size: 1.1rem;
            color: #aaaaaa;
            line-height: 1.6;
        }

        .content-section {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin-top: 40px;
        }

        .card {
            background: rgba(20, 20, 20, 0.6);
            border-radius: 15px;
            padding: 25px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .card h2 {
            font-size: 1.5rem;
            color: white;
            margin-bottom: 15px;
            padding-bottom: 10px;
            border-bottom: 1px solid #333;
        }

        .card p {
            color: #aaaaaa;
            line-height: 1.6;
        }

        .user-info {
            line-height: 1.8;
        }

        .user-info span {
            color: white;
            font-weight: 500;
        }

        .badge {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 0.8rem;
            font-weight: 600;
            margin-left: 5px;
        }

        .badge-admin {
            background-color: #f44336;
            color: white;
        }

        .badge-manager {
            background-color: #2196F3;
            color: white;
        }

        .badge-user {
            background-color: #4CAF50;
            color: white;
        }

        footer {
            text-align: center;
            padding: 20px;
            margin-top: 40px;
            background: #111111;
            color: #666;
        }

        @media (max-width: 768px) {
            .navbar {
                flex-direction: column;
                gap: 15px;
            }
            
            .welcome-card h1 {
                font-size: 2rem;
            }
            
            .content-section {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="logo">ANT Bistro</div>
        <div class="nav-items">
            <a href="<%=request.getContextPath()%>/index.html">Trang chủ</a>
            <a href="<%=request.getContextPath()%>/products.jsp">Thực đơn</a>
            <a href="<%=request.getContextPath()%>/booking.jsp">Đặt bàn</a>
            <a href="<%=request.getContextPath()%>/contact.jsp">Liên hệ</a>
            <a href="logout" class="logout">Đăng xuất</a>
        </div>
    </nav>

    <script>
        // Tự động chuyển hướng đến trang chủ sau khi đăng nhập
        window.onload = function() {
            window.location.href = '<%=request.getContextPath()%>/index.html';
        }
    </script>
    
    <div class="container">
        <div class="welcome-card">
            <h1>Chào mừng, <%= currentUser.getUsername() %>!</h1>
            <p>Bạn đã đăng nhập thành công vào hệ thống. Hãy khám phá các tính năng và dịch vụ của chúng tôi.</p>
        </div>
        
        <div class="content-section">
            <div class="card">
                <h2>Tính năng mới</h2>
                <p>Khám phá các tính năng mới nhất được cập nhật trên hệ thống. Chúng tôi luôn cải tiến để mang đến trải nghiệm tốt nhất cho người dùng.</p>
            </div>
            <div class="card">
                <h2>Thông báo</h2>
                <p>Bạn không có thông báo mới nào. Chúng tôi sẽ cập nhật khi có thông tin quan trọng hoặc sự kiện mới.</p>
            </div>
            <div class="card">
                <h2>Thông tin tài khoản</h2>
                <div class="user-info">
                    <p><strong>ID:</strong> <span><%= currentUser.getId() %></span></p>
                    <p><strong>Username:</strong> <span><%= currentUser.getUsername() %></span></p>
                    <p><strong>Email:</strong> <span><%= currentUser.getEmail() %></span></p>
                    <p><strong>Vai trò:</strong> 
                        <span>
                            <%= currentUser.getRole() %>
                            <% if("admin".equals(currentUser.getRole())) { %>
                                <span class="badge badge-admin">Admin</span>
                            <% } else if("manager".equals(currentUser.getRole())) { %>
                                <span class="badge badge-manager">Quản lý</span>
                            <% } else { %>
                                <span class="badge badge-user">Người dùng</span>
                            <% } %>
                        </span>
                    </p>
                    <p><strong>Địa chỉ:</strong> <span><%= currentUser.getAddress() != null ? currentUser.getAddress() : "Chưa cập nhật" %></span></p>
                    <p><a href="#" style="color: #cccccc; text-decoration: none;">Cập nhật thông tin cá nhân</a></p>
                </div>
            </div>
        </div>
    </div>
    
    <footer>
        &copy; <%= new java.util.Date().getYear() + 1900 %> Hệ Thống Quản Lý. Tất cả quyền được bảo lưu.
    </footer>
</body>
</html>