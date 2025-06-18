<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Table_reservation.css">
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
                        <th>Nhóm khách</th>
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
                                    <%-- ${item.idAccount} --%>
                                        <span class="badge badge-primary">Khách thành viên</span>
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
