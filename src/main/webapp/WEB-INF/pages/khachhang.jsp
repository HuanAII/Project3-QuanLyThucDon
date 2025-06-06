<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý khách hàng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/khachhang.css">
</head>
<body>
    <div class="container">
        <h2 class="page-title">Quản lý khách hàng</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>
        
        <!-- Form thêm/sửa khách hàng -->
        <div class="form-section">
            <h3 class="form-title">${editUser.id == null ? 'Thêm mới khách hàng' : 'Cập nhật thông tin khách hàng'}</h3>
            <form action="${pageContext.request.contextPath}/admin/customers" method="post">
                <input type="hidden" name="id" value="${editUser.id}" />
                
                <div class="form-grid">
                    <div class="form-group">
                        <label class="form-label" for="username">Tên đăng nhập</label>
                        <input class="form-control" type="text" id="username" name="username" value="${editUser.username}" required />
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="password">Mật khẩu</label>
                        <input class="form-control" type="password" id="password" name="password" value="${editUser.password}" required />
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="hoVaTen">Họ và tên</label>
                        <input class="form-control" type="text" id="hoVaTen" name="HoVaTen" value="${editUser.hoVaTen}" required />
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="email">Email</label>
                        <input class="form-control" type="email" id="email" name="email" value="${editUser.email}" required />
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="sdt">Số điện thoại</label>
                        <input class="form-control" type="text" id="sdt" name="sdt" value="${editUser.sdt}" required />
                    </div>
                </div>
                
                <div class="form-actions">
                    <button class="btn btn-primary" type="submit">
                        ${editUser.id == null ? 'Thêm mới' : 'Cập nhật'}
                    </button>
                    <c:if test="${editUser.id != null}">
                        <a href="${pageContext.request.contextPath}/admin/customers" class="btn btn-secondary">Hủy</a>
                    </c:if>
                </div>
            </form>
        </div>
        
        <!-- Danh sách khách hàng -->
        <div class="table-container">
            <div class="table-responsive">
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
                                    <div class="table-actions">
                                        <form action="${pageContext.request.contextPath}/admin/customers" method="get" style="display:inline;">
                                            <input type="hidden" name="editId" value="${user.id}" />
                                            <button class="btn btn-secondary action-btn" type="submit">Sửa</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/admin/customers" method="post" style="display:inline;" onsubmit="return confirm('Bạn có chắc muốn xóa khách hàng này?');">
                                            <input type="hidden" name="deleteId" value="${user.id}" />
                                            <button class="btn btn-danger action-btn" type="submit">Xóa</button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>