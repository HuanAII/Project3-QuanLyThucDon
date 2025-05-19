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
        --primary-color: #4a6fa5;
        --primary-hover: #3a5982;
        --secondary-color: #6c757d;
        --success-color: #28a745;
        --success-hover: #218838;
        --danger-color: #dc3545;
        --danger-hover: #c82333;
        --light-bg: #f8f9fa;
        --border-radius: 8px;
        --box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        --transition: all 0.3s ease;
    }

    body {
        margin: 0;
        font-family: 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
        background-color: var(--light-bg);
        color: #333;
    }

    .container {
        max-width: 1280px;
        margin: 0 auto;
        padding: 20px;
    }

    .page-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid #eee;
    }

    .page-title {
        font-size: 24px;
        font-weight: 600;
        color: var(--primary-color);
        margin: 0;
    }

    .filter-bar {
        background-color: white;
        padding: 15px;
        border-radius: var(--border-radius);
        box-shadow: var(--box-shadow);
        margin-bottom: 25px;
        display: flex;
        justify-content: flex-end;
    }

    .filter-form {
        display: flex;
        gap: 12px;
        align-items: center;
        flex-wrap: wrap;
    }

    .filter-form input,
    .filter-form select {
        padding: 10px 14px;
        border-radius: var(--border-radius);
        border: 1px solid #ddd;
        font-size: 14px;
        transition: var(--transition);
        min-width: 180px;
    }

    .filter-form input:focus,
    .filter-form select:focus {
        outline: none;
        border-color: var(--primary-color);
        box-shadow: 0 0 0 2px rgba(74, 111, 165, 0.2);
    }

    .btn {
        padding: 10px 16px;
        border: none;
        border-radius: var(--border-radius);
        cursor: pointer;
        font-weight: 500;
        transition: var(--transition);
        font-size: 14px;
    }

    .btn-primary {
        background-color: var(--primary-color);
        color: white;
    }

    .btn-primary:hover {
        background-color: var(--primary-hover);
    }

    .btn-success {
        background-color: var(--success-color);
        color: white;
    }

    .btn-success:hover {
        background-color: var(--success-hover);
    }

    .btn-danger {
        background-color: var(--danger-color);
        color: white;
    }

    .btn-danger:hover {
        background-color: var(--danger-hover);
    }

    .order-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: 20px;
    }

    .order-card {
        background-color: white;
        border-radius: var(--border-radius);
        box-shadow: var(--box-shadow);
        padding: 20px;
        transition: var(--transition);
    }

    .order-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    }

    .order-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;
    }

    .order-id {
        font-size: 18px;
        font-weight: 600;
        color: var(--primary-color);
        margin: 0;
    }

    .order-date {
        font-size: 14px;
        color: var(--secondary-color);
    }

    .order-info p {
        margin: 8px 0;
        font-size: 14px;
        display: flex;
        justify-content: space-between;
    }

    .order-info strong {
        color: #555;
    }

    .order-items {
        margin: 15px 0;
        padding: 12px;
        background-color: #f9f9f9;
        border-radius: 6px;
        max-height: 120px;
        overflow-y: auto;
    }

    .order-items h4 {
        margin: 0 0 8px 0;
        font-size: 15px;
        color: #444;
    }

    .order-items ul {
        padding-left: 20px;
        margin: 0;
    }

    .order-items li {
        font-size: 13px;
        margin-bottom: 4px;
        color: #555;
    }

    .status-tag {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 600;
        text-transform: uppercase;
    }

    .status-CHO_XU_LY { 
        background-color: #e9ecef;
        color: #495057;
    }

    /* request_food.css */
.container {
    width: 95%;
    max-width: 1400px;
    margin: 0 auto;
    padding: 20px;
}

.page-header {
    margin-bottom: 30px;
    border-bottom: 1px solid #e0e0e0;
    padding-bottom: 15px;
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.page-header h1 {
    margin: 0;
    color: #333;
    font-size: 1.8rem;
}

.header-actions {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 15px;
}

.add-order-btn {
    padding: 10px 15px;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    text-decoration: none;
    display: inline-flex;
    align-items: center;
    gap: 8px;
    transition: background-color 0.3s;
    cursor: pointer;
}

.add-order-btn:hover {
    background-color: #218838;
}

.filter-bar {
    display: flex;
    justify-content: flex-start;
    margin-bottom: 20px;
}

.filter-form {
    display: flex;
    gap: 10px;
    align-items: center;
}

.filter-form select {
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background-color: #fff;
    font-size: 14px;
}

.btn {
    padding: 8px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s;
}

.btn-primary {
    background-color: #007bff;
    color: white;
}

.btn-primary:hover {
    background-color: #0069d9;
}

.btn-success {
    background-color: #28a745;
    color: white;
}

.btn-success:hover {
    background-color: #218838;
}

.btn-danger {
    background-color: #dc3545;
    color: white;
}

.btn-danger:hover {
    background-color: #c82333;
}

.btn-secondary {
    background-color: #6c757d;
    color: white;
}

.btn-secondary:hover {
    background-color: #5a6268;
}

.empty-state {
    text-align: center;
    padding: 30px;
    background-color: #f8f9fa;
    border-radius: 8px;
    color: #6c757d;
}

.order-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
    gap: 20px;
}

.order-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 15px;
    background-color: #fff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    transition: transform 0.2s, box-shadow 0.2s;
}

.order-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.order-id {
    font-size: 1.2rem;
    margin: 0;
    color: #333;
}

.order-date {
    color: #6c757d;
    font-size: 0.9rem;
}

.order-info {
    margin-bottom: 15px;
}

.order-info p {
    margin: 8px 0;
    display: flex;
    justify-content: space-between;
}

.order-info strong {
    color: #555;
}

.status-tag {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 0.85rem;
    font-weight: 600;
}

.status-CHO_XU_LY {
    background-color: #ffc107;
    color: #212529;
}

.status-DANG_CHUAN_BI {
    background-color: #17a2b8;
    color: white;
}

.status-CHO_THANH_TOAN {
    background-color: #6610f2;
    color: white;
}

.status-DA_HOAN_THANH {
    background-color: #28a745;
    color: white;
}

.order-items {
    margin-bottom: 15px;
}

.order-items h4 {
    margin-top: 0;
    margin-bottom: 10px;
    font-size: 1rem;
    color: #333;
}

.order-items ul {
    list-style-type: none;
    padding-left: 0;
    margin: 0;
}

.order-items li {
    padding: 6px 0;
    border-bottom: 1px solid #f1f1f1;
    font-size: 0.9rem;
}

.order-items li:last-child {
    border-bottom: none;
}

.order-actions {
    margin-top: 15px;
    padding-top: 15px;
    border-top: 1px solid #eee;
}

.action-form {
    display: flex;
    gap: 10px;
    margin-bottom: 10px;
}

.action-form select {
    flex-grow: 1;
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    background-color: #fff;
}

.action-row {
    display: flex;
    justify-content: flex-end;
    margin-top: 10px;
}

/* Modal styles */
.modal {
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
    background-color: #fff;
    margin: 5% auto;
    padding: 20px;
    border-radius: 8px;
    width: 80%;
    max-width: 700px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    animation: modal-appear 0.3s ease;
}

@keyframes modal-appear {
    from {
        opacity: 0;
        transform: translateY(-50px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
}

.modal-header h2 {
    margin: 0;
    color: #333;
    font-size: 1.5rem;
}

.close {
    color: #aaa;
    font-size: 28px;
    font-weight: bold;
    cursor: pointer;
}

.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
}

.modal-body {
    padding: 15px 0;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: 500;
    color: #333;
}

.form-control {
    width: 100%;
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 14px;
}

.food-items-section {
    margin: 20px 0;
    padding: 15px;
    background-color: #f9f9f9;
    border-radius: 8px;
}

.food-items-section h3 {
    margin-top: 0;
    margin-bottom: 15px;
    font-size: 1.2rem;
    color: #333;
}

.food-item {
    display: grid;
    grid-template-columns: 3fr 1fr 2fr 40px;
    gap: 10px;
    margin-bottom: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
    align-items: end;
}

.food-item:last-child {
    margin-bottom: 0;
    padding-bottom: 0;
    border-bottom: none;
}

.remove-item {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 38px;
    padding: 0;
    width: 38px;
}

.form-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
    padding-top: 15px;
    border-top: 1px solid #eee;
}

@media (max-width: 768px) {
    .order-grid {
        grid-template-columns: 1fr;
    }
    
    .action-form {
        flex-direction: column;
    }
    
    .action-form select, 
    .action-form button {
        width: 100%;
    }
    
    .modal-content {
        width: 95%;
        margin: 10% auto;
    }
    
    .food-item {
        grid-template-columns: 1fr;
    }
}
    
    .status-DANG_CHUAN_BI { 
        background-color: #cce5ff;
        color: #004085;
    }
    
    .status-CHO_THANH_TOAN { 
        background-color: #fff3cd;
        color: #856404;
    }
    
    .status-DA_HOAN_THANH { 
        background-color: #d4edda;
        color: #155724;
    }

    .order-actions {
        display: grid;
        grid-template-columns: 1fr;
        gap: 10px;
        margin-top: 15px;
    }

    .action-row {
        display: flex;
        gap: 8px;
    }

    .action-form {
        flex: 1;
    }

    .action-form select {
        width: 100%;
        padding: 8px 12px;
        border-radius: 6px;
        border: 1px solid #ddd;
        margin-bottom: 8px;
        font-size: 14px;
    }

    .empty-state {
        text-align: center;
        padding: 40px;
        background-color: white;
        border-radius: var(--border-radius);
        box-shadow: var(--box-shadow);
    }

    .empty-state p {
        font-size: 16px;
        color: var(--secondary-color);
    }

    @media (max-width: 768px) {
        .page-header {
            flex-direction: column;
            align-items: flex-start;
            gap: 15px;
        }
        
        .filter-bar {
            justify-content: flex-start;
        }
        
        .filter-form {
            flex-direction: column;
            align-items: stretch;
        }
        
        .order-grid {
            grid-template-columns: 1fr;
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