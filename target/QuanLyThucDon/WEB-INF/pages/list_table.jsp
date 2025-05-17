<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.Table" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý bàn - Hệ thống nhà hàng</title>
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
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem 1rem;
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
        }

        .btn-add {
            display: flex;
            align-items: center;
            background-color: var(--primary);
            color: white;
            padding: 0.75rem 1.25rem;
            border-radius: 0.5rem;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.2s ease;
        }

        .btn-add:hover {
            background-color: var(--primary-hover);
            transform: translateY(-1px);
        }

        .btn-add::before {
            content: "+";
            margin-right: 0.5rem;
            font-size: 1.25rem;
            font-weight: 300;
        }

        .tables-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 1rem;
        }

        .table-card {
            background-color: white;
            border-radius: 0.5rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            overflow: hidden;
            font-size: 0.9rem;
        }

        .table-card:hover {
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
            transform: translateY(-4px);
        }

        .table-header {
            background-color: var(--primary-light);
            padding: 0.75rem;
            border-bottom: 1px solid var(--gray-200);
            position: relative;
        }

        .table-status {
            position: absolute;
            top: 0.75rem;
            right: 0.75rem;
            padding: 0.15rem 0.5rem;
            border-radius: 9999px;
            font-size: 0.7rem;
            font-weight: 500;
        }

        .status-available {
            background-color: rgba(16, 185, 129, 0.1);
            color: var(--success);
        }

        .status-occupied {
            background-color: rgba(239, 68, 68, 0.1);
            color: var(--danger);
        }

        .status-reserved {
            background-color: rgba(245, 158, 11, 0.1);
            color: var(--warning);
        }

        .table-icon {
            font-size: 1.2rem;
            margin-bottom: 0.5rem;
        }

        .table-number {
            font-size: 1rem;
            font-weight: 600;
            margin-bottom: 0.15rem;
        }

        .table-id {
            font-size: 0.75rem;
            color: var(--gray-600);
        }

        .table-details {
            padding: 0.75rem;
        }

        .detail-item {
            display: flex;
            align-items: center;
            margin-bottom: 0.4rem;
            color: var(--gray-700);
            font-size: 0.8rem;
        }

        .detail-item:last-child {
            margin-bottom: 0;
        }

        .detail-icon {
            margin-right: 0.5rem;
            font-size: 0.9rem;
            color: var(--primary);
        }

        .table-actions {
            display: flex;
            padding: 0.5rem 0.75rem;
            background-color: var(--gray-100);
            border-top: 1px solid var(--gray-200);
        }

        .btn-action {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0.4rem 0.6rem;
            border-radius: 0.375rem;
            font-size: 0.75rem;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.2s ease;
            flex: 1;
        }

        .btn-edit {
            background-color: white;
            color: var(--gray-700);
            border: 1px solid var(--gray-300);
            margin-right: 0.5rem;
        }

        .btn-edit:hover {
            background-color: var(--gray-100);
            color: var(--gray-800);
        }

        .btn-delete {
            background-color: white;
            color: var(--danger);
            border: 1px solid var(--gray-300);
        }

        .btn-delete:hover {
            background-color: rgba(239, 68, 68, 0.1);
        }

        .btn-icon {
            margin-right: 0.375rem;
        }

        .empty-state {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 4rem 2rem;
            background-color: white;
            border-radius: 0.75rem;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05), 0 1px 2px rgba(0, 0, 0, 0.1);
            text-align: center;
            grid-column: 1 / -1;
        }

        .empty-icon {
            font-size: 3rem;
            margin-bottom: 1.5rem;
            color: var(--gray-400);
        }

        .empty-text {
            font-size: 1.125rem;
            font-weight: 500;
            color: var(--gray-600);
            margin-bottom: 1.5rem;
        }

        .btn-empty-add {
            background-color: var(--primary);
            color: white;
            padding: 0.75rem 1.5rem;
            border-radius: 0.5rem;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.2s ease;
        }

        .btn-empty-add:hover {
            background-color: var(--primary-hover);
        }

        /* Responsive adjustments */
        @media (max-width: 1024px) {
            .tables-grid {
                grid-template-columns: repeat(3, 1fr);
            }
        }
        
        @media (max-width: 768px) {
            .tables-grid {
                grid-template-columns: repeat(2, 1fr);
            }
            
            .page-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 1rem;
            }
            
            .btn-add {
                width: 100%;
                justify-content: center;
            }
        }

        @media (max-width: 480px) {
            .tables-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h1 class="page-title">Danh sách bàn</h1>
            <a href="${pageContext.request.contextPath}/admin/add-table" class="btn-add">Thêm bàn mới</a>
        </div>

        <div class="tables-grid">
            <% 
                List<Table> tables = (List<Table>) request.getAttribute("tables");
                if (tables != null && !tables.isEmpty()) {
                    for (Table table : tables) {
                        // Giả định trạng thái bàn, thay thế bằng thông tin thực tế nếu có
                        String statusClass = "status-available";
                        String statusText = "Trống";
                        
                        // Nếu model Table có trường status, bạn có thể sử dụng nó để xác định trạng thái
                        // if (table.getStatus() == 1) {
                        //     statusClass = "status-occupied";
                        //     statusText = "Đang sử dụng";
                        // } else if (table.getStatus() == 2) {
                        //     statusClass = "status-reserved";
                        //     statusText = "Đã đặt trước";
                        // }
            %>
                <div class="table-card">
                    <div class="table-header">
                        <span class="table-status <%= statusClass %>"><%= statusText %></span>
                        <div class="table-icon">🍽️</div>
                        <h3 class="table-number">Bàn số <%= table.getTableNumber() %></h3>
                        <p class="table-id">Mã bàn: <%= table.getIdTable() %></p>
                    </div>
                    <div class="table-details">
                        <div class="detail-item">
                            <span class="detail-icon">👥</span>
                            <span>Sức chứa: <%= table.getSeats() %> người</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-icon">📍</span>
                            <span>Vị trí: Khu vực chính</span>
                        </div>
                        <div class="detail-item">
                            <span class="detail-icon">📋</span>
                            <span>Đặt bàn: 0 lần hôm nay</span>
                        </div>
                    </div>
                    <div class="table-actions">
                        <a href="${pageContext.request.contextPath}/admin/edit-table?id=<%= table.getIdTable() %>" class="btn-action btn-edit">
                            <span class="btn-icon">✏️</span> Sửa
                        </a>
                        <a href="${pageContext.request.contextPath}/admin/delete-table?id=<%= table.getIdTable() %>" class="btn-action btn-delete">
                            <span class="btn-icon">🗑️</span> Xóa
                        </a>
                    </div>
                </div>
            <% 
                    }
                } else { 
            %>
                <div class="empty-state">
                    <div class="empty-icon">🪑</div>
                    <div class="empty-text">Chưa có bàn nào được thêm!</div>
                    <a href="${pageContext.request.contextPath}/admin/add-table" class="btn-empty-add">Thêm bàn ngay</a>
                </div>
            <% 
                } 
            %>
        </div>
    </div>
</body>
</html>