<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Table" %>
<%@ page import="com.example.dao.TableDAO" %>
<%@ page import="java.util.List" %>
<style>
    /* Header Section */
    .page-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
    }
    
    .page-title {
        font-size: 1.8rem;
        font-weight: 600;
        color: #2d3142;
        margin: 0;
    }
    
    .btn-add {
        background-color: var(--primary-color, #ff6b35);
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 8px;
        font-weight: 500;
        display: flex;
        align-items: center;
        gap: 8px;
        transition: all 0.3s ease;
        cursor: pointer;
    }
    
    .btn-add:hover {
        background-color: #e55a2b;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }
    
    /* Tables Grid Layout */
    .tables-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        gap: 25px;
        margin-bottom: 30px;
    }
    
    .table-card {
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
        overflow: hidden;
        transition: all 0.3s ease;
        position: relative;
        border: 1px solid #eaedf3;
    }
    
    .table-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
    }
    
    /* Table Status Indicators */
    .table-status {
        position: absolute;
        top: 15px;
        right: 15px;
        font-size: 0.75rem;
        padding: 5px 10px;
        border-radius: 15px;
        font-weight: 600;
        text-transform: uppercase;
    }
    
    .status-available {
        background-color: #e3f9e5;
        color: #31b237;
    }
    
    .status-reserved {
        background-color: #fff8e6;
        color: #ffa940;
    }
    
    .status-occupied {
        background-color: #feecef;
        color: #f5222d;
    }
    
    /* Table Card Components */
    .table-header {
        background-color: #f8f9fa;
        padding: 20px;
        border-bottom: 1px solid #eaedf3;
        position: relative;
    }
    
    .table-icon {
        width: 60px;
        height: 60px;
        background-color: var(--primary-light, #ffe0d5);
        color: var(--primary-color, #ff6b35);
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 1.8rem;
        margin-bottom: 15px;
    }
    
    .table-number {
        font-size: 1.4rem;
        font-weight: 600;
        color: #2d3142;
        margin: 0 0 5px 0;
    }
    
    .table-id {
        font-size: 0.85rem;
        color: #6c757d;
        margin: 0;
    }
    
    .table-details {
        padding: 20px;
    }
    
    .detail-item {
        display: flex;
        align-items: center;
        margin-bottom: 12px;
        font-size: 0.95rem;
    }
    
    .detail-icon {
        color: var(--primary-color, #ff6b35);
        margin-right: 10px;
        width: 20px;
        text-align: center;
    }
    
    .table-actions {
        padding: 15px 20px;
        display: flex;
        justify-content: space-between;
        border-top: 1px solid #eaedf3;
    }
    
    .btn-action {
        padding: 8px 15px;
        border-radius: 6px;
        font-weight: 500;
        font-size: 0.9rem;
        text-decoration: none;
        transition: all 0.2s ease;
        display: inline-flex;
        align-items: center;
        gap: 5px;
    }
    
    .btn-edit {
        background-color: #e6f7ff;
        color: #1890ff;
    }
    
    .btn-edit:hover {
        background-color: #bae7ff;
    }
    
    .btn-delete {
        background-color: #fff1f0;
        color: #ff4d4f;
    }
    
    .btn-delete:hover {
        background-color: #ffccc7;
    }
    
    /* Empty State */
    .empty-state {
        text-align: center;
        padding: 40px 20px;
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
    }
    
    .empty-icon {
        font-size: 3rem;
        color: #d9dde3;
        margin-bottom: 15px;
    }
    
    .empty-text {
        font-size: 1.1rem;
        color: #6c757d;
        margin-bottom: 20px;
    }
    
    /* Add Table Form */
    .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        align-items: center;
        justify-content: center;
        z-index: 1000;
        visibility: hidden;
        opacity: 0;
        transition: all 0.3s ease;
    }
    
    .modal-overlay.active {
        visibility: visible;
        opacity: 1;
    }
    
    .modal-content {
        background-color: white;
        border-radius: 12px;
        width: 100%;
        max-width: 500px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
        transform: translateY(-20px);
        transition: all 0.3s ease;
    }
    
    .modal-overlay.active .modal-content {
        transform: translateY(0);
    }
    
    .modal-header {
        padding: 20px 25px;
        border-bottom: 1px solid #eaedf3;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    
    .modal-title {
        font-size: 1.2rem;
        font-weight: 600;
        color: #2d3142;
        margin: 0;
    }
    
    .modal-close {
        background: none;
        border: none;
        font-size: 1.5rem;
        color: #6c757d;
        cursor: pointer;
        transition: color 0.2s ease;
    }
    
    .modal-close:hover {
        color: #ff4d4f;
    }
    
    .modal-body {
        padding: 25px;
    }
    
    .form-group {
        margin-bottom: 20px;
    }
    
    .form-label {
        display: block;
        margin-bottom: 8px;
        font-weight: 500;
        color: #2d3142;
    }
    
    .form-control {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #dde1e7;
        border-radius: 8px;
        font-size: 1rem;
        transition: border-color 0.2s ease;
    }
    
    .form-control:focus {
        border-color: var(--primary-color, #ff6b35);
        outline: none;
    }
    
    .form-select {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #dde1e7;
        border-radius: 8px;
        font-size: 1rem;
        appearance: none;
        background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' fill='%236c757d' viewBox='0 0 16 16'%3E%3Cpath d='M8 11.5l-6-6L3.5 4 8 8.5 12.5 4 14 5.5l-6 6z'/%3E%3C/svg%3E");
        background-repeat: no-repeat;
        background-position: right 15px center;
        background-size: 12px;
    }
    
    .modal-footer {
        padding: 15px 25px;
        border-top: 1px solid #eaedf3;
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }
    
    .btn-cancel {
        padding: 10px 20px;
        border-radius: 8px;
        font-weight: 500;
        background-color: #f1f3f5;
        color: #495057;
        border: none;
        cursor: pointer;
        transition: all 0.2s ease;
    }
    
    .btn-cancel:hover {
        background-color: #e9ecef;
    }
    
    .btn-save {
        padding: 10px 25px;
        border-radius: 8px;
        font-weight: 500;
        background-color: var(--primary-color, #ff6b35);
        color: white;
        border: none;
        cursor: pointer;
        transition: all 0.2s ease;
    }
    
    .btn-save:hover {
        background-color: #e55a2b;
    }
    
    /* Alert Messages */
    .alert {
        padding: 15px 20px;
        border-radius: 8px;
        margin-bottom: 25px;
        position: relative;
        display: flex;
        align-items: center;
    }
    
    .alert-success {
        background-color: #e3f9e5;
        color: #31b237;
    }
    
    .alert-error {
        background-color: #feecef;
        color: #f5222d;
    }
    
    .alert-icon {
        margin-right: 10px;
        font-size: 1.1rem;
    }
    
    /* Responsive Design */
    @media (max-width: 768px) {
        .tables-container {
            grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
            gap: 15px;
        }
        
        .page-header {
            flex-direction: column;
            align-items: flex-start;
            gap: 15px;
        }
    }
    
    @media (max-width: 480px) {
        .tables-container {
            grid-template-columns: 1fr;
        }
    }
</style>

<!-- Page Header -->
<div class="page-header">
    <h2 class="page-title">Quản lý bàn ăn</h2>
    <button class="btn-add" onclick="openModal()">
        <i class="fas fa-plus"></i> Thêm bàn mới
    </button>
</div>

<!-- Alert Messages -->
<% if (request.getAttribute("success") != null) { %>
<div class="alert alert-success">
    <i class="fas fa-check-circle alert-icon"></i>
    <%= request.getAttribute("success") %>
</div>
<% } %>

<% if (request.getAttribute("error") != null) { %>
<div class="alert alert-error">
    <i class="fas fa-exclamation-circle alert-icon"></i>
    <%= request.getAttribute("error") %>
</div>
<% } %>

<!-- Tables Grid -->
<div class="tables-container">
    <%
    List<Table> tables = TableDAO.getAllTables();
    if (tables == null || tables.isEmpty()) {
    %>
    <div class="empty-state">
        <div class="empty-icon">
            <i class="fas fa-chair"></i>
        </div>
        <p class="empty-text">Chưa có bàn nào trong danh sách</p>
        <button class="btn-add" onclick="openModal()">
            <i class="fas fa-plus"></i> Thêm bàn mới
        </button>
    </div>
    <% } else {
        for (Table t : tables) {
            String statusClass = "";
            if (t.getStatus().equalsIgnoreCase("Available")) {
                statusClass = "status-available";
            } else if (t.getStatus().equalsIgnoreCase("Reserved")) {
                statusClass = "status-reserved";
            } else if (t.getStatus().equalsIgnoreCase("Occupied")) {
                statusClass = "status-occupied";
            }
    %>
    <div class="table-card">
        <div class="table-header">
            <div class="table-status <%= statusClass %>"><%= t.getStatus() %></div>
            <div class="table-icon">
                <i class="fas fa-chair"></i>
            </div>
            <h3 class="table-number">Bàn số <%= t.getTableNumber() %></h3>
            <p class="table-id">ID: <%= t.getIdTable() %></p>
        </div>
        <div class="table-details">
            <div class="detail-item">
                <i class="fas fa-users detail-icon"></i>
                <span>Số ghế: <%= t.getSeats() %></span>
            </div>
            <div class="detail-item">
                <i class="fas fa-info-circle detail-icon"></i>
                <span>Trạng thái: <%= t.getStatus() %></span>
            </div>
        </div>
        <div class="table-actions">
            <a href="edit-table?id=<%= t.getIdTable() %>" class="btn-action btn-edit">
                <i class="fas fa-edit"></i> Sửa
            </a>
            <button class="btn-action btn-delete" onclick="confirmDelete('<%= t.getIdTable() %>')">
                <i class="fas fa-trash-alt"></i> Xóa
            </button>
        </div>
    </div>
    <% } } %>
</div>

<!-- Add Table Modal -->
<div class="modal-overlay" id="addTableModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 class="modal-title">Thêm bàn mới</h3>
            <button class="modal-close" onclick="closeModal()">&times;</button>
        </div>
        <form action="add-table" method="post">
            <div class="modal-body">
                <div class="form-group">
                    <label for="idTable" class="form-label">ID bàn</label>
                    <input type="text" id="idTable" name="idTable" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="tableNumber" class="form-label">Số bàn</label>
                    <input type="number" id="tableNumber" name="tableNumber" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="seats" class="form-label">Số ghế</label>
                    <input type="number" id="seats" name="seats" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="status" class="form-label">Trạng thái</label>
                    <select id="status" name="status" class="form-select" required>
                        <option value="Available">Available</option>
                        <option value="Reserved">Reserved</option>
                        <option value="Occupied">Occupied</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-cancel" onclick="closeModal()">Hủy</button>
                <button type="submit" class="btn-save">Lưu bàn</button>
            </div>
        </form>
    </div>
</div>

<!-- Delete Table Form (Hidden) -->
<form id="deleteForm" action="delete-table" method="post" style="display: none;">
    <input type="hidden" id="deleteId" name="id" value="">
</form>

<script>
    // Modal functions
    function openModal() {
        document.getElementById('addTableModal').classList.add('active');
        document.body.style.overflow = 'hidden';
    }
    
    function closeModal() {
        document.getElementById('addTableModal').classList.remove('active');
        document.body.style.overflow = 'auto';
    }
    
    // Delete confirmation
    function confirmDelete(id) {
        if (confirm('Bạn có chắc chắn muốn xóa bàn này?')) {
            document.getElementById('deleteId').value = id;
            document.getElementById('deleteForm').submit();
        }
    }
    
    // Close modal when clicking outside
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('addTableModal');
        if (event.target === modal) {
            closeModal();
        }
    });
    
    // Prevent modal close when clicking inside modal content
    document.querySelector('.modal-content').addEventListener('click', function(event) {
        event.stopPropagation();
    });
</script>