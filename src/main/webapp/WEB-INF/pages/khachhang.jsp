<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý khách hàng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        :root {
            --primary: #4F46E5;
            --primary-hover: #4338CA;
            --primary-light: #EEF2FF;
            --danger: #EF4444;
            --danger-hover: #DC2626;
            --warning: #F59E0B;
            --success: #10B981;
            --gray-100: #F3F4F6;
            --gray-200: #E5E7EB;
            --gray-300: #D1D5DB;
            --gray-600: #4B5563;
            --gray-700: #374151;
            --gray-800: #1F2937;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #F9FAFB;
            color: var(--gray-800);
            line-height: 1.5;
            padding: 1.5rem;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 2rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid var(--gray-200);
        }

        .page-title {
            font-size: 1.75rem;
            font-weight: 600;
            color: var(--gray-800);
            margin-bottom: 1.5rem;
        }

        /* Alert messages */
        .alert-error {
            background-color: rgba(239, 68, 68, 0.1);
            color: var(--danger);
            padding: 1rem;
            border-radius: 0.5rem;
            margin-bottom: 1.5rem;
            border-left: 4px solid var(--danger);
        }

        /* Form styling */
        .form-section {
            background-color: white;
            border-radius: 0.5rem;
            padding: 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.1);
        }

        .form-title {
            font-size: 1.25rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: var(--gray-800);
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 1rem;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        .form-label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: var(--gray-700);
            font-size: 0.875rem;
        }

        .form-control {
            width: 100%;
            padding: 0.6rem 0.75rem;
            border: 1px solid var(--gray-300);
            border-radius: 0.375rem;
            font-size: 0.9rem;
            transition: border-color 0.2s ease;
        }

        .form-control:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
        }

        .form-actions {
            display: flex;
            gap: 0.75rem;
            margin-top: 1.5rem;
        }

        /* Buttons */
        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.6rem 1rem;
            font-weight: 500;
            font-size: 0.9rem;
            border: none;
            border-radius: 0.375rem;
            cursor: pointer;
            transition: all 0.2s ease;
            text-decoration: none;
        }

        .btn-primary {
            background-color: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background-color: var(--primary-hover);
        }

        .btn-secondary {
            background-color: white;
            color: var(--gray-700);
            border: 1px solid var(--gray-300);
        }

        .btn-secondary:hover {
            background-color: var(--gray-100);
        }

        .btn-danger {
            background-color: white;
            color: var(--danger);
            border: 1px solid var(--gray-300);
        }

        .btn-danger:hover {
            background-color: rgba(239, 68, 68, 0.1);
        }

        /* Table styling */
        .table-container {
            background-color: white;
            border-radius: 0.5rem;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.1);
        }

        .table-responsive {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: var(--gray-100);
            font-weight: 600;
            color: var(--gray-700);
            text-align: left;
            padding: 0.75rem 1rem;
            border-bottom: 1px solid var(--gray-200);
            font-size: 0.875rem;
        }

        td {
            padding: 0.75rem 1rem;
            border-bottom: 1px solid var(--gray-200);
            color: var(--gray-700);
            font-size: 0.9rem;
        }

        tr:last-child td {
            border-bottom: none;
        }

        tr:hover td {
            background-color: var(--gray-100);
        }

        .table-actions {
            display: flex;
            gap: 0.5rem;
        }

        .action-btn {
            padding: 0.4rem 0.75rem;
            font-size: 0.8rem;
        }

        /* Responsive adjustments */
        @media (max-width: 992px) {
            .form-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 768px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            
            .page-header {
                flex-direction: column;
                align-items: flex-start;
            }
            
            .table-responsive {
                overflow-x: auto;
            }
        }
    </style>
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
            <form action="${pageContext.request.contextPath}/admin/khachhang" method="post">
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