<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý khách hàng</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f0f0f0; }
        .form-section { margin: 20px 0; }
        .btn { padding: 6px 12px; border: none; background: #1a73e8; color: #fff; border-radius: 4px; cursor: pointer; }
        .btn-danger { background: #e53935; }
    </style>
</head>
<body>
<h2>Quản lý khách hàng</h2>
<c:if test="${not empty errorMessage}">
    <div style="color: red; margin-bottom: 10px;">${errorMessage}</div>
</c:if>

<!-- Form thêm/sửa khách hàng -->
<div class="form-section">
    <form action="${pageContext.request.contextPath}/admin/khachhang" method="post">
        <input type="hidden" name="id" value="${editUser.id}" />
        <input type="text" name="username" placeholder="Tên đăng nhập" value="${editUser.username}" required />
        <input type="password" name="password" placeholder="Mật khẩu" value="${editUser.password}" required />
        <input type="text" name="HoVaTen" placeholder="Họ và tên" value="${editUser.hoVaTen}" required />
        <input type="email" name="email" placeholder="Email" value="${editUser.email}" required />
        <input type="text" name="sdt" placeholder="Số điện thoại" value="${editUser.sdt}" required />
        <button class="btn" type="submit">${editUser.id == null ? 'Thêm mới' : 'Cập nhật'}</button>
        <c:if test="${editUser.id != null}">
            <a href="${pageContext.request.contextPath}/admin/khachhang" class="btn">Hủy</a>
        </c:if>
    </form>
</div>

<!-- Danh sách khách hàng -->
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Tên đăng nhập</th>
            <th>Họ và tên</th>
            <th>Email</th>
            <th>Số điện thoại</th>
            <th>Thao tác</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${customerList}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.hoVaTen}</td>
            <td>${user.email}</td>
            <td>${user.sdt}</td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/khachhang" method="get" style="display:inline;">
                    <input type="hidden" name="editId" value="${user.id}" />
                    <button class="btn" type="submit">Sửa</button>
                </form>
                <form action="${pageContext.request.contextPath}/admin/khachhang" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc muốn xóa khách hàng này?');">
                    <input type="hidden" name="deleteId" value="${user.id}" />
                    <button class="btn btn-danger" type="submit">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html> 