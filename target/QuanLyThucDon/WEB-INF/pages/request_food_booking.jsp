<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

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

    <div class="page-header">
        <div class="filter-bar">
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/admin/datmon">
                <select name="status">
                    <option value="">Tất cả trạng thái</option>
                    <option value="CHO_XU_LY" ${param.status == 'CHO_XU_LY' ? 'selected' : ''}>Chờ xử lý</option>
                    <option value="DANG_CHUAN_BI" ${param.status == 'DANG_CHUAN_BI' ? 'selected' : ''}>Đang chuẩn bị</option>
                    <option value="CHO_THANH_TOAN" ${param.status == 'CHO_THANH_TOAN' ? 'selected' : ''}>Chờ thanh toán</option>
                    <option value="DA_HOAN_THANH" ${param.status == 'DA_HOAN_THANH' ? 'selected' : ''}>Hoàn thành</option>
                </select>
                <button type="submit" class="btn btn-primary">Lọc</button>
            </form>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty listDH}">
            <div class="empty-state">
                <p>Không có đơn hàng nào phù hợp với tiêu chí tìm kiếm.</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="order-grid">
                <c:forEach var="dh" items="${listDH}">
                    <div class="order-card">
                        <div class="order-header">
                            <h3 class="order-id">Đơn #${dh.idDonHang}</h3>
                            <span class="order-date">${dh.date}</span>
                        </div>
                        
                        <div class="order-info">
                            <p>
                                <strong>Khách hàng:</strong>
                                <span>${dh.tenKH}</span>
                            </p>
                            <p>
                                <strong>Số điện thoại:</strong>
                                <span>${dh.sdt}</span>
                            </p>
                            <p>
                                <strong>Tổng tiền:</strong>
                                <span>${dh.total} đ</span>
                            </p>
                            <p>
                                <strong>Bàn:</strong>
                                <span>${dh.idTable}</span>
                            </p>
                            <p>
                                <strong>Địa chỉ:</strong>
                                <span>${dh.diaChi}</span>
                            </p>
                            <p>
                                <strong>Trạng thái:</strong>
                                <span class="status-tag status-${dh.status}">
                                    <c:choose>
                                        <c:when test="${dh.status == 'CHO_XU_LY'}">Chờ xử lý</c:when>
                                        <c:when test="${dh.status == 'DANG_CHUAN_BI'}">Đang chuẩn bị</c:when>
                                        <c:when test="${dh.status == 'CHO_THANH_TOAN'}">Chờ thanh toán</c:when>
                                        <c:when test="${dh.status == 'DA_HOAN_THANH'}">Hoàn thành</c:when>
                                        <c:otherwise>${dh.status}</c:otherwise>
                                    </c:choose>
                                </span>
                            </p>
                        </div>
                        
                        <div class="order-items">
                            <h4>Danh sách món</h4>
                            <ul>
                                <c:forEach var="ct" items="${dh.chiTietList}">
                                    <li>${ct.tenMon} - SL: ${ct.soLuong} - ${ct.gia} đ</li>
                                </c:forEach>
                            </ul>
                        </div>
                        
                        <div class="order-actions">
                            <form class="action-form" method="post" action="${pageContext.request.contextPath}/admin/datmon">
                                <input type="hidden" name="orderId" value="${dh.idDonHang}" />
                                <select name="status">
                                    <option value="CHO_XU_LY" ${dh.status == 'CHO_XU_LY' ? 'selected' : ''}>Chờ xử lý</option>
                                    <option value="DANG_CHUAN_BI" ${dh.status == 'DANG_CHUAN_BI' ? 'selected' : ''}>Đang chuẩn bị</option>
                                    <option value="CHO_THANH_TOAN" ${dh.status == 'CHO_THANH_TOAN' ? 'selected' : ''}>Chờ thanh toán</option>
                                    <option value="DA_HOAN_THANH" ${dh.status == 'DA_HOAN_THANH' ? 'selected' : ''}>Hoàn thành</option>
                                </select>
                                <button type="submit" name="action" value="UpdateStatus" class="btn btn-success">Cập nhật trạng thái</button>
                            </form>
                            
                            <div class="action-row">
                                <form class="action-form" action="${pageContext.request.contextPath}/admin/datmon" method="post">
                                    <input type="hidden" name="orderId" value="${dh.idDonHang}" />
                                    <button type="submit" name="action" value="delete" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa đơn hàng này?')">Xóa đơn hàng</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>