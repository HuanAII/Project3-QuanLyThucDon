<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<style>
    :root {
        --primary-color: #4a6fa5;
        --primary-hover: #3a5982;
        --secondary-color: #6c757d;
        --success-color: #28a745;
        --light-bg: #f8f9fa;
        --border-radius: 8px;
        --box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        --transition: all 0.3s ease;
    }

    body {
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
        margin-bottom: 25px;
        padding-bottom: 15px;
        border-bottom: 1px solid #e9ecef;
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

    .reservation-table-container {
        background-color: white;
        border-radius: var(--border-radius);
        box-shadow: var(--box-shadow);
        padding: 20px;
        overflow-x: auto;
    }

    .reservation-table {
        width: 100%;
        border-collapse: collapse;
        font-size: 14px;
    }

    .reservation-table th {
        background-color: #f8f9fa;
        color: #495057;
        font-weight: 600;
        text-align: left;
        padding: 12px 15px;
        border-bottom: 2px solid #e9ecef;
    }

    .reservation-table td {
        padding: 12px 15px;
        border-bottom: 1px solid #e9ecef;
        color: #333;
        vertical-align: middle;
    }

    .reservation-table tr:last-child td {
        border-bottom: none;
    }

    .reservation-table tr:hover {
        background-color: #f8f9fa;
    }

    .phone-number {
        font-family: monospace;
        font-size: 14px;
    }

    .guest-count {
        font-weight: 500;
        text-align: center;
    }

    .reservation-date,
    .reservation-time {
        white-space: nowrap;
    }

    .empty-state {
        text-align: center;
        padding: 40px;
    }

    .empty-state p {
        font-size: 16px;
        color: var(--secondary-color);
    }

    .badge {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 600;
    }

    .badge-primary {
        background-color: #cfe2ff;
        color: #084298;
    }

    .badge-secondary {
        background-color: #e2e3e5;
        color: #41464b;
    }

    .note-text {
        max-width: 150px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        display: inline-block;
        vertical-align: middle;
        cursor: pointer;
    }

    .note-text.expanded {
        white-space: normal;
        word-wrap: break-word;
        max-width: 350px;
    }
    
    /* Modal Styles */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.5);
        overflow: auto;
    }
    
    .modal-content {
        position: relative;
        background-color: #fff;
        margin: 10% auto;
        padding: 20px;
        border-radius: var(--border-radius);
        box-shadow: var(--box-shadow);
        width: 80%;
        max-width: 500px;
        animation: modalopen 0.3s;
    }
    
    @keyframes modalopen {
        from {opacity: 0; transform: translateY(-60px);}
        to {opacity: 1; transform: translateY(0);}
    }
    
    .close-modal {
        position: absolute;
        right: 15px;
        top: 10px;
        font-size: 24px;
        font-weight: bold;
        color: #aaa;
        cursor: pointer;
    }
    
    .close-modal:hover {
        color: black;
    }
    
    .modal-header {
        padding-bottom: 10px;
        margin-bottom: 15px;
        border-bottom: 1px solid #e9ecef;
    }
    
    .modal-header h4 {
        margin: 0;
        color: var(--primary-color);
    }
    
    .table-selection {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
        gap: 10px;
        margin: 20px 0;
    }
    
    .table-option {
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: var(--border-radius);
        text-align: center;
        cursor: pointer;
        transition: var(--transition);
    }
    
    .table-option:hover {
        background-color: #f8f9fa;
    }
    
    .table-option.selected {
        background-color: var(--primary-color);
        color: white;
        border-color: var(--primary-color);
    }
    
    .table-option.unavailable {
        background-color: #f8d7da;
        color: #721c24;
        cursor: not-allowed;
        opacity: 0.7;
    }
    
    .modal-footer {
        padding-top: 15px;
        border-top: 1px solid #e9ecef;
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    @media (max-width: 992px) {
        .reservation-table {
            min-width: 800px;
        }
    }
</style>
<div class="page-header">
    <h1 class="page-title">Danh Sách Đặt Bàn Chờ</h1>
</div>

<c:if test="${not empty message}">
    <div class="alert alert-${alertType}" role="alert" style="margin-bottom: 20px; padding: 15px; border-radius: var(--border-radius); 
        background-color: ${alertType == 'success' ? '#d4edda' : '#f8d7da'}; 
        color: ${alertType == 'success' ? '#155724' : '#721c24'};">
        ${message}
    </div>
</c:if>

<div class="reservation-table-container">
    <c:choose>
        <c:when test="${empty listReservation}">
            <div class="empty-state">
                <p>Không có lịch đặt bàn nào.</p>
            </div>
        </c:when>
        <c:otherwise>
            <table class="reservation-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Tên Khách</th>
                        <th>SĐT</th>
                        <th>Số Khách</th>
                        <th>Ngày Đặt</th>
                        <th>Giờ Đặt</th>
                        <th>Món Ăn Kèm</th>
                        <th>Ghi Chú</th>
                        <th>Tài Khoản</th>
                        <th>Thao Tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listReservation}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td><strong>${item.name}</strong></td>
                            <td class="phone-number">${item.phone}</td>
                            <td class="guest-count">${item.guests}</td>
                            <td class="reservation-date">${item.date}</td>
                            <td class="reservation-time">${item.time}</td>

                            <td>
                                <span class="note-text" onclick="this.classList.toggle('expanded')" style="cursor: pointer;" title="Nhấn để xem đầy đủ">
                                    ${item.foods != null && item.foods != '' ? item.foods : 'Không có'}
                                </span>
                            </td>
                            <td>
                                <span class="note-text" onclick="this.classList.toggle('expanded')" style="cursor: pointer;" title="Nhấn để xem đầy đủ">
                                    ${item.message != null && item.message != '' ? item.message : 'Không có'}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.idAccount !=-1}">
                                        <span class="badge badge-primary">${item.idAccount}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-secondary">Khách vãng lai</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <div style="display: flex; gap: 5px;">
                                    <button type="button" 
                                        class="btn btn-primary" 
                                        style="padding: 0px 0px; font-size: 12px; height: 32px; width: 65px;"
                                        onclick="openTableSelection('${item.id_reservation}', ${item.guests})">
                                        Xác nhận
                                    </button>
                                    <form method="post" action="${pageContext.request.contextPath}/admin/Waiting_booking_table" style="margin:0;">
                                        <input type="hidden" name="reservationId" value="${item.id_reservation}" />
                                        <button type="submit" name="action" value="delete" 
                                            class="btn btn-danger" style="padding: 6px 10px; font-size: 12px; background-color: #dc3545; height: 32px;"
                                            onclick="return confirm('Bạn có chắc muốn hủy đặt bàn này?')">
                                            Hủy
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>


<div id="tableModal" style="
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1050;
    width: 400px;
    background: #fff;
    border-radius: 12px;
    padding: 25px 30px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
">
  <h4 style="margin-bottom: 20px; font-size: 20px; color: #333; text-align: center;">
    Chọn Bàn
  </h4>

  <div style="margin-bottom: 15px;">
    <select id="tableSelect" style="
        width: 100%;
        padding: 10px 12px;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 14px;
    ">
      <c:forEach var="table" items="${listTable}">
        <option value="${table.idTable}">Bàn ${table.tableNumber}</option>
      </c:forEach>
    </select>
  </div>

  <div style="text-align: right; display: flex; justify-content: flex-end; gap: 10px;">
    <button class="btn btn-secondary" onclick="closeTableModal()" style="
        padding: 8px 16px;
        border: none;
        background: #ccc;
        color: #333;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
    ">Hủy</button>

    <button class="btn btn-success" onclick="confirmTableSelection()" style="
        padding: 8px 16px;
        border: none;
        background: #28a745;
        color: white;
        border-radius: 6px;
        font-size: 14px;
        cursor: pointer;
    ">Xác nhận</button>
  </div>
</div>

<form id="tableForm" method="post" action="${pageContext.request.contextPath}/admin/Waiting_booking_table" style="display:none;">
    <input type="hidden" name="reservationId" id="reservationIdInput" />
    <input type="hidden" name="tableId" id="tableIdInput" />
    <input type="hidden" name="action" value="confirm" />
</form>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const noteTexts = document.querySelectorAll('.note-text');
        noteTexts.forEach(note => {
            note.addEventListener('click', function() {
                this.classList.toggle('expanded');
            });
        });
    });

    function openTableSelection(reservationId, guestCount) {
        const modal = document.getElementById('tableModal');
        modal.style.display = 'block';
        modal.dataset.reservationId = reservationId;
    }

    function closeTableModal() {
        document.getElementById('tableModal').style.display = 'none';
    }

    function confirmTableSelection() {
        const modal = document.getElementById('tableModal');
        const reservationId = modal.dataset.reservationId;
        const tableId = document.getElementById('tableSelect').value;

        if (!reservationId || !tableId) {
            alert("Vui lòng chọn bàn hợp lệ.");
            return;
        }

        document.getElementById('reservationIdInput').value = reservationId;
        document.getElementById('tableIdInput').value = tableId;

        document.getElementById('tableForm').submit();
    }
</script>
