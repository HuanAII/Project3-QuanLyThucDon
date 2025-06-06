<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý khuyến mãi</title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/khuyenmai.css">
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
