<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý khuyến mãi</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
        }

        h2, h3 {
            color: #333;
        }
        h3 {
            
            margin-top: 20px;
        }


        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        a, button.edit-btn {
            color: #007bff;
            text-decoration: none;
            margin: 0 5px;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 1em;
            padding: 0;
            font-family: inherit;
        }

        a:hover, button.edit-btn:hover {
            text-decoration: underline;
        }

        form {
            margin-top: 40px;
            background-color: #fff;
            padding: 20px;
            border-radius: 6px;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
            width: 100%;
            max-width: 500px;
        }

        form input, form button {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        form button {
            background-color: #28a745;
            color: white;
            border: none;
            font-weight: bold;
            cursor: pointer;
        }

        form button:hover {
            background-color: #218838;
        }

        .actions {
            display: flex;
            gap: 10px;
        }

        .no-data {
            margin-top: 20px;
            font-style: italic;
            color: #666;
        }

        /* Popup modal style */
        .modal {
            display: none; /* hidden by default */
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%; 
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5);
        }

        .modal-content {
            background-color: #fff;
            margin: 10% auto;
            padding: 20px;
            border-radius: 6px;
            width: 90%;
            max-width: 500px;
            box-shadow: 0 0 10px rgba(0,0,0,0.25);
            position: relative;
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 15px;
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close-btn:hover {
            color: black;
        }
    </style>
</head>
<body>

<h2>${message}</h2>

<c:if test="${empty listKhuyenMai}">
    <p class="no-data">Không có khuyến mãi nào.</p>
</c:if>

<c:if test="${not empty listKhuyenMai}">
    <table>
        <thead>
        <tr>
            <th>Pro ID</th>
            <th>Giảm giá (%)</th>
            <th>Mã giảm giá</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="km" items="${listKhuyenMai}">
            <tr>
                <td>${km.proId}</td>
                <td>${km.discount}</td>
                <td>${km.maGiamGia}</td>
                <td>${km.startDate}</td>
                <td>${km.endDate}</td>
                <td class="actions">
                    <!-- Nút sửa dùng button để kích hoạt popup -->
                    <button class="edit-btn" 
                            data-proid="${km.proId}" 
                            data-discount="${km.discount}" 
                            data-magiamgia="${km.maGiamGia}"
                            data-startdate="${km.startDate}"
                            data-enddate="${km.endDate}">✏️ Sửa</button>
                    <a href="${pageContext.request.contextPath}/admin/xoa-khuyenmai?pro_id=${km.proId}" 
                       onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<h3>Thêm khuyến mãi mới</h3>
<form action="${pageContext.request.contextPath}/admin/them-khuyenmai" method="post">
    <input type="number" name="discount" step="0.01" placeholder="Giảm giá (%)" required>
    <input type="text" name="ma_giam_gia" placeholder="Mã giảm giá" required>
    <input type="date" name="start_date" required>
    <input type="date" name="end_date" required>
    <button type="submit">➕ Thêm khuyến mãi</button>
</form>

<!-- Popup modal sửa khuyến mãi -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" id="closeModal">&times;</span>
        <h3>Sửa khuyến mãi</h3>
        <form id="editForm" action="${pageContext.request.contextPath}/admin/sua-khuyenmai" method="post">
            <input type="hidden" name="pro_id" id="editProId">
            <input type="number" name="discount" id="editDiscount" step="0.01" placeholder="Giảm giá (%)" required>
            <input type="text" name="ma_giam_gia" id="editMaGiamGia" placeholder="Mã giảm giá" required>
            <input type="date" name="start_date" id="editStartDate" required>
            <input type="date" name="end_date" id="editEndDate" required>
            <button type="submit">💾 Lưu thay đổi</button>
        </form>
    </div>
</div>

<script>
    // Lấy các phần tử
    const modal = document.getElementById('editModal');
    const closeModalBtn = document.getElementById('closeModal');
    const editForm = document.getElementById('editForm');

    // Các input trong form sửa
    const editProId = document.getElementById('editProId');
    const editDiscount = document.getElementById('editDiscount');
    const editMaGiamGia = document.getElementById('editMaGiamGia');
    const editStartDate = document.getElementById('editStartDate');
    const editEndDate = document.getElementById('editEndDate');

    // Gán sự kiện cho nút sửa từng dòng
    document.querySelectorAll('.edit-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            // Lấy dữ liệu từ thuộc tính data-*
            editProId.value = btn.getAttribute('data-proid');
            editDiscount.value = btn.getAttribute('data-discount');
            editMaGiamGia.value = btn.getAttribute('data-magiamgia');
            editStartDate.value = btn.getAttribute('data-startdate');
            editEndDate.value = btn.getAttribute('data-enddate');

            // Hiện popup
            modal.style.display = 'block';
        });
    });

    // Đóng popup khi click nút x
    closeModalBtn.onclick = function() {
        modal.style.display = "none";
    }

    // Đóng popup khi click bên ngoài modal-content
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>
