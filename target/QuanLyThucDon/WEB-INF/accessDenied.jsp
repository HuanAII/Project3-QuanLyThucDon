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
    </style>
</head>
<body>
    <div class="box">
        <h1>Truy cập bị từ chối</h1>
        <p>Bạn không có quyền truy cập chức năng này!</p>
        <a href="${pageContext.request.contextPath}/login.jsp">Quay về trang đăng nhập</a>
    </div>
</body>
</html> 