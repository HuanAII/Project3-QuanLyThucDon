<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Truy cập bị từ chối</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8d7da; color: #721c24; text-align: center; padding-top: 100px; }
        .box { background: #fff; border: 1px solid #f5c6cb; display: inline-block; padding: 40px 60px; border-radius: 8px; }
        h1 { color: #c82333; }
        a { color: #0056b3; text-decoration: none; }
        a:hover { text-decoration: underline; }
        .error-code { font-size: 72px; color: #dc3545; margin: 0; }
        .message { margin: 20px 0; }
        .back-btn { 
            display: inline-block;
            padding: 10px 20px;
            background: #0056b3;
            color: white;
            border-radius: 5px;
            margin-top: 20px;
        }
        .back-btn:hover {
            background: #003d82;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="box">
        <p class="error-code">403</p>
        <h1>Truy cập bị từ chối</h1>
        <p class="message">Bạn không có quyền truy cập trang này.<br>Vui lòng đăng nhập với tài khoản có quyền phù hợp.</p>
        <a href="${pageContext.request.contextPath}/" class="back-btn">Về trang chủ</a>
    </div>
</body>
</html>