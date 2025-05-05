<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Quản Trị</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
            color: #333;
        }
        h1 {
            font-size: 2em;
            text-align: center;
            color: #1a73e8;
            padding: 20px;
            background-color: #fff;
            border-bottom: 2px solid #ddd;
            margin-bottom: 20px;
        }
        .container {
            display: flex;
            flex-direction: Row;
            margin: 20px;
        }
        .menu {
            background-color: #fff;
            padding: 20px;
            width: 20%;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .menu ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
        }
        .menu li {
            margin: 10px 0;
        }
        .menu a {
            color: #007bff;
            text-decoration: none;
            font-size: 1.1em;
            display: block;
            padding: 8px 16px;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }
        .menu a:hover {
            background-color: #007bff;
            color: white;
        }

        /* Submenu style */
        .submenu {
            display: none;
            background-color: #f0f4ff;
            border-left: 3px solid #007bff;
            margin-left: 10px;
            padding-left: 15px;
            padding-top: 8px;
            padding-bottom: 8px;
            border-radius: 4px;
            font-size: 0.95em;
        }

        .submenu li {
            margin: 6px 0;
        }

        .submenu a {
            font-size: 1em;
            padding: 6px 12px;
            color: #333;
            background-color: transparent;
        }

        .submenu a:hover {
            background-color: #e6f0ff;
            color: #007bff;
        }

        .content {
            background-color: #fff;
            padding: 20px;
            width: 75%;
            margin-left: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
    </style>
    <script>
        function toggleSubMenu() {
            const sub = document.getElementById("thucdon-submenu");
            sub.style.display = sub.style.display === "block" ? "none" : "block";
        }
    </script>
</head>
<body>
<h1>Quản Trị Nhà Hàng</h1>

<div class="container">
    <!-- Menu -->
    <div class="menu">
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/thongke">Thống kê</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/datban">Đặt bàn</a></li>

            <li>
                <a href="javascript:void(0);" onclick="toggleSubMenu()">Thực đơn ▾</a>
                <ul id="thucdon-submenu" class="submenu">
                    <li><a href="${pageContext.request.contextPath}/admin/thucdon/mathang">Danh sách thực đơn</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/thucdon/danhmuc">Danh mục</a></li>
                </ul>
            </li>

            <li><a href="${pageContext.request.contextPath}/admin/hoadon">Hóa đơn</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/nhanvien">Nhân viên</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/khachhang">Khách hàng</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/hethong">Hệ thống</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/thietlap">Thiết lập nhà hàng</a></li>
        </ul>
    </div>

    <!-- Content -->
    <div class="content">
        <jsp:include page="${contentPage}" />
    </div>
</div>
</body>
</html>
