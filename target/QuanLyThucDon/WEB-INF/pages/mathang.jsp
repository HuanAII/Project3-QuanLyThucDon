<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.productsDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thực đơn</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary: #1e88e5;
            --success: #43a047;
            --danger: #e53935;
            --warning: #fb8c00;
            --dark: #212121;
            --light: #f5f5f5;
            --border: #e0e0e0;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Roboto, 'Helvetica Neue', sans-serif;
        }
        
        body {
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
        }
        
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 15px;
        }
        
        .card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
            overflow: hidden;
        }
        
        .card-header {
            background: linear-gradient(135deg, var(--primary), #3949ab);
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .card-header h2 {
            font-size: 1.5rem;
            font-weight: 600;
            margin: 0;
            display: flex;
            align-items: center;
        }
        
        .card-header h2 i {
            margin-right: 10px;
            font-size: 1.3em;
        }
        
        .btn-add {
            background-color: var(--success);
            color: white;
            padding: 10px 16px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
        
        .btn-add:hover {
            background-color: #388e3c;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        
        .btn-add i {
            margin-right: 8px;
        }
        
        .table-responsive {
            overflow-x: auto;
            padding: 0;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 0;
            white-space: nowrap;
        }
        
        table th {
            background-color: #f1f8ff;
            color: var(--dark);
            font-weight: 600;
            text-align: center;
            padding: 15px 10px;
            border-bottom: 2px solid var(--border);
            position: sticky;
            top: 0;
            z-index: 10;
        }
        
        table td {
            padding: 12px 10px;
            text-align: center;
            border-bottom: 1px solid var(--border);
            vertical-align: middle;
        }
        
        table tbody tr:hover {
            background-color: #f5f9ff;
        }
        
        table tbody tr:nth-child(even) {
            background-color: #fcfcfc;
        }
        
        .text-left {
            text-align: left !important;
        }
        
        .product-name {
            font-weight: 600;
            color: var(--primary);
        }
        
        .product-id {
            font-family: monospace;
            color: #666;
            font-size: 0.9em;
        }
        
        .price {
            font-weight: 600;
            color: #f57c00;
        }
        
        .actions {
            display: flex;
            justify-content: center;
            gap: 8px;
        }
        
        .btn-action {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            font-size: 0.85em;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            transition: all 0.2s;
            min-width: 70px;
        }
        
        .btn-edit {
            background-color: var(--primary);
            color: white;
        }
        
        .btn-edit:hover {
            background-color: #1976d2;
            transform: translateY(-2px);
        }
        
        .btn-delete {
            background-color: var(--danger);
            color: white;
        }
        
        .btn-delete:hover {
            background-color: #d32f2f;
            transform: translateY(-2px);
        }
        
        .btn-action i {
            margin-right: 4px;
            font-size: 0.9em;
        }
        
        .description {
            max-width: 250px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            line-height: 1.5;
            margin: 0 auto;
            text-align: left;
        }
        
        .badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 0.75em;
            font-weight: 600;
            text-align: center;
        }
        
        .badge-primary {
            background-color: #e3f2fd;
            color: var(--primary);
        }
        
        .no-data {
            text-align: center;
            padding: 40px 20px;
            color: #999;
            font-style: italic;
        }
        
        /* Pour les écrans mobiles */
        @media (max-width: 768px) {
            .card-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }
            
            .btn-add {
                width: 100%;
                justify-content: center;
            }
            
            .table-responsive {
                margin: 0 -15px;
                width: calc(100% + 30px);
                border-radius: 0;
            }
            
            table th, table td {
                padding: 10px 8px;
                font-size: 0.9em;
            }
            
            .actions {
                flex-direction: column;
                gap: 5px;
            }
            
            .btn-action {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2><i class="fas fa-utensils"></i> Danh sách thực đơn</h2>
                <button class="btn-add" onclick="window.location.href='${pageContext.request.contextPath}/admin/thucdon/addProduct'">
                    <i class="fas fa-plus"></i> Thêm thực đơn mới
                </button>
            </div>
            
            <div class="table-responsive">
                <table>
                    <thead>
                        <tr>
                            <th width="5%">STT</th>
                            <th width="10%">Mã Món</th>
                            <th width="20%">Tên Món</th>
                            <th width="10%">Danh Mục</th>
                            <th width="10%">Giá</th>
                            <th width="12%">Hình Ảnh</th> <!-- Thêm cột hình ảnh -->
                            <th width="22%">Mô Tả</th>
                            <th width="10%">Đơn Vị Tính</th>
                            <th width="13%">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Product> listThucDon = productsDAO.getAllProducts();
                            if (listThucDon != null && !listThucDon.isEmpty()) {
                                int stt = 1;
                                for (Product a : listThucDon) {
                        %>
                        <tr>
                            <td><%= stt++ %></td>
                            <td class="product-id"><%= a.getIdMon() %></td>
                            <td class="text-left product-name"><%= a.getTenMon() %></td>
                            <td><span class="badge badge-primary"><%= a.getIdDanhMuc() %></span></td>
                            <td class="price"><%= String.format("%,d", a.getGia()) %> đ</td>
                            <td>
                                <% if (a.getHinhAnh() != null && !a.getHinhAnh().isEmpty()) { %>
                                    <img src="http://localhost:8080/QuanLyThucDon/uploads/<%=a.getHinhAnh() %>" alt="Hình ảnh" style="width:70px; height:50px; object-fit:cover; border-radius:6px; box-shadow:0 2px 8px rgba(0,0,0,0.13);">
                                <% } else { %>
                                    <span style="color:#888;">Không có ảnh</span>
                                <% } %>
                            </td>
                            <td>
                                <div class="description">
                                    <%= a.getMota() != null && !a.getMota().isEmpty() ? a.getMota() : "<em>Chưa có mô tả</em>" %>
                                </div>
                            </td>
                            <td><%= a.getDonViTinh() %></td>
                            <td>
                                <div class="actions">
                                    <button class="btn-action btn-edit" onclick="window.location.href='${pageContext.request.contextPath}/admin/thucdon/editProduct?id=<%= a.getIdMon() %>'">
                                        <i class="fas fa-edit"></i> Sửa
                                    </button>
                                    <form action="${pageContext.request.contextPath}/admin/thucdon/deleteProduct" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="<%= a.getIdMon() %>">
                                        <button type="submit" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc muốn xóa món này?');">
                                            <i class="fas fa-trash-alt"></i> Xóa
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <% } 
                        } else { %>
                        <tr>
                            <td colspan="9" class="no-data">
                                <i class="fas fa-info-circle"></i> Không có món ăn nào trong thực đơn
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <script>
        // JavaScript pour ajouter des fonctionnalités supplémentaires si nécessaire
        document.addEventListener('DOMContentLoaded', function() {
            // Vérifier s'il y a des messages de succès ou d'erreur à afficher
            // (Vous pouvez implémenter cette partie selon vos besoins)
        });
    </script>
</body>
</html>