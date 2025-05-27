<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.dao.reservationDAO" %>
<%@ page import="java.sql.Time" %>  
<%@ page import="java.util.Date" %>
<%@ page import="com.example.models.DonHang" %>
<%@ page import="com.example.models.ChiTietDonHang" %>


<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/request_food.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h1>Quản lý đơn hàng</h1>
            <div class="header-actions">
                <button type="button" class="btn btn-primary add-order-btn" id="openAddOrderModal">
                    <i class="fas fa-plus"></i> Thêm đơn hàng
                </button>
            </div>
            <div class="filter-bar">
                <form class="filter-form" method="get" action="${pageContext.request.contextPath}/admin/datmon">
                    <select name="status">
                        <option value="">Tất cả trạng thái</option>
                        <option value="CHO_XU_LY" ${param.status == 'CHO_XU_LY' ? 'selected' : ''}>Chờ xử lý</option>
                        <option value="DANG_TIEN_HANH" ${param.status == 'DANG_TIEN_HANH' ? 'selected' : ''}>Đang tiến hành</option>
                        <option value="DANG_CHUAN_BI" ${param.status == 'DANG_CHUAN_BI' ? 'selected' : ''}>Đang chuẩn bị</option>
                        <option value="CHO_PHUC_VU" ${param.status == 'CHO_PHUC_VU' ? 'selected' : ''}>Chờ phục vụ</option>
                        <option value="DANG_PHUC_VU" ${param.status == 'DANG_PHUC_VU' ? 'selected' : ''}>Đang phục vụ</option>
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
                                <c:choose>
                                <c:when test="${dh.status == 'CHO_PHUC_VU'}">
                                    <span class="order-time">${dh.date} - ${dh.time}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="order-time">${dh.date}</span>
                                </c:otherwise>
                            </c:choose>
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
                                    <span><fmt:formatNumber value="${dh.total}" type="number" groupingUsed="true" /> VNĐ</span>
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
                                            <c:when test="${dh.status == 'DANG_TIEN_HANH'}">Đang tiến hành</c:when>
                                            <c:when test="${dh.status == 'DANG_CHUAN_BI'}">Đang chuẩn bị</c:when>
                                            <c:when test="${dh.status == 'CHO_PHUC_VU'}">Chờ phục vụ</c:when>
                                            <c:when test="${dh.status == 'DANG_PHUC_VU'}">Đang phục vụ</c:when>
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
                                        <li>${ct.tenMon} - SL: ${ct.soLuong} - 
                                            <fmt:formatNumber value="${ct.gia}" type="number" groupingUsed="true"/> VNĐ
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            
                            <div class="order-actions">
                                <form class="action-form" method="post" action="${pageContext.request.contextPath}/admin/datmon">
                                    <input type="hidden" name="orderId" value="${dh.idDonHang}" />
                                    <select name="status">
                                        <option value="CHO_XU_LY" ${dh.status == 'CHO_XU_LY' ? 'selected' : ''}>Chờ xử lý</option>
                                        <option value="DANG_TIEN_HANH" ${dh.status == 'DANG_TIEN_HANH' ? 'selected' : ''}>Đang tiến hành</option>
                                        <option value="DANG_CHUAN_BI" ${dh.status == 'DANG_CHUAN_BI' ? 'selected' : ''}>Đang chuẩn bị</option>
                                        <option value="CHO_PHUC_VU" ${dh.status == 'CHO_PHUC_VU' ? 'selected' : ''}>Chờ phục vụ</option>
                                        <option value="DANG_PHUC_VU" ${dh.status == 'DANG_PHUC_VU' ? 'selected' : ''}>Đang phục vụ</option>
                                        <option value="DA_HOAN_THANH" ${dh.status == 'DA_HOAN_THANH' ? 'selected' : ''}>Hoàn thành</option>
                                    </select>
                                    <button type="submit" name="action" value="UpdateStatus" class="btn btn-success">Cập nhật trạng thái</button>
                                </form>
                                
                                <div class="action-row">
                                    <c:if test="${dh.status == 'DANG_PHUC_VU'}">
                                        <button type="button" class="btn btn-primary payment-btn" data-idtable="${dh.idTable}" data-order-id="${dh.idDonHang}" data-total="${dh.total}">
                                            <i class="fas fa-money-bill-wave"></i> Thanh toán
                                        </button>
                                        <button type="button" class="btn btn-success add-food-btn" data-order-id="${dh.idDonHang}">
                                            <i class="fas fa-plus"></i> Thêm món
                                        </button>
                                    </c:if>
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
    </div>
    
   <!-- Modal Thêm đơn hàng -->
<div id="addOrderModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Thêm đơn hàng mới</h2>
            <span class="close">&times;</span>
        </div>
        <div class="modal-body">
            <form id="addOrderForm" method="post" action="${pageContext.request.contextPath}/admin/addOrder">
                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label for="tenKH">Tên khách hàng:</label>
                    <input type="text" id="tenKH" name="tenKH" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="sdt">Số điện thoại:</label>
                    <input type="text" id="sdt" name="sdt" class="form-control" required>
                </div>
                <label for="idTable">Bàn : </label>
                <select id="idTable" name="idTable" class="form-control" required>
                    <c:if test="${not empty emptyTable}">
                        <c:forEach var="table" items="${emptyTable}">
                            <option value="${table.idTable}">${table.tableNumber}</option>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty emptyTable}">
                        <option disabled>Không có bàn trống</option>
                    </c:if>
                </select>


                <input type="hidden" name="diaChi" value="Tại chỗ">

                <div class="form-group">
                    <input type="hidden" name="status" value="DANG_CHUAN_BI">
                </div>

                <div class="food-items-section">
    <h3>Danh sách món</h3>
    <div id="foodItemsList">
        <div class="food-item">
            <div class="form-group">
                <label>Tên món:</label>
                <select name="tenMon[]" class="form-control" required>
                    <option value="" disabled selected>Chọn món</option>
                    <c:forEach var="mon" items="${listMon}">
                        <option value="${mon.idMon}">${mon.tenMon}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Số lượng:</label>
                <input type="number" name="soLuong[]" class="form-control" min="1" value="1" required>
            </div>
            <button type="button" class="btn btn-danger remove-item">
                <i class="fas fa-trash"></i>
            </button>
        </div>
    </div>
    <button type="button" id="addFoodItemBtn" class="btn btn-secondary">
        <i class="fas fa-plus"></i> Thêm món
    </button>
</div>


                <div class="form-footer">
                    <button type="submit" class="btn btn-primary">Thêm đơn hàng</button>
                    <button type="button" class="btn btn-secondary" id="cancelAddOrder">Hủy</button>
                </div>
            </form>
        </div>
    </div>
</div>


<div id="paymentModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Thanh toán đơn hàng</h2>
            <span class="close">&times;</span>
        </div>
        <div class="modal-body">
            <form id="paymentForm" method="post" action="${pageContext.request.contextPath}/admin/payment">
                 <input type="hidden" name="idTable" id="paymentIdTable">
                <input type="hidden" name="orderId" id="paymentOrderId">
                <input type="hidden" name="total" id="paymentTotal">
                
                <div class="form-group">
                    <label for="paymentMethod">Phương thức thanh toán:</label>
                    <select id="paymentMethod" name="paymentMethod" class="form-control" required>
                        <option value="TIEN_MAT">Tiền mặt</option>
                        <option value="CHUYEN_KHOAN">Chuyển khoản</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Tổng tiền:</label>
                    <span id="paymentAmount"></span> đ
                </div>

                <div class="form-footer">
                    <button type="submit" class="btn btn-primary">Xác nhận thanh toán</button>
                    <button type="button" class="btn btn-secondary" id="cancelPayment">Hủy</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal Thêm món cho đơn hàng -->
<div id="addFoodModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Thêm món cho đơn hàng</h2>
            <span class="close">&times;</span>
        </div>
        <div class="modal-body">
            <form id="addFoodForm" method="post" action="${pageContext.request.contextPath}/admin/addMoreFood">
                <input type="hidden" name="orderId" id="addFoodOrderId">
                
                <div class="food-items-section">
                    <h3>Danh sách món thêm</h3>
                    <div id="addFoodItemsList">
                        <div class="food-item">
                            <div class="form-group">
                                <label>Tên món:</label>
                                <select name="tenMon[]" class="form-control" required>
                                    <option value="" disabled selected>Chọn món</option>
                                    <c:forEach var="mon" items="${listMon}">
                                        <option value="${mon.idMon}">${mon.tenMon}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Số lượng:</label>
                                <input type="number" name="soLuong[]" class="form-control" min="1" value="1" required>
                            </div>
                            <button type="button" class="btn btn-danger remove-item">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </div>
                    <button type="button" id="addMoreFoodItemBtn" class="btn btn-secondary">
                        <i class="fas fa-plus"></i> Thêm món
                    </button>
                </div>

                <div class="form-footer">
                    <button type="submit" class="btn btn-primary">Xác nhận thêm món</button>
                    <button type="button" class="btn btn-secondary" id="cancelAddFood">Hủy</button>
                </div>
            </form>
        </div>
    </div>
</div>

    <script>

        var modal = document.getElementById("addOrderModal");
        var btn = document.getElementById("openAddOrderModal");
        
        var span = document.getElementsByClassName("close")[0];
        var cancelBtn = document.getElementById("cancelAddOrder");
        
 
        btn.onclick = function() {
            modal.style.display = "block";
        }
        
        span.onclick = function() {
            modal.style.display = "none";
        }
        
        cancelBtn.onclick = function() {
            modal.style.display = "none";
        }
    
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
        

        document.getElementById('addFoodItemBtn').addEventListener('click', function() {
            var foodItemsList = document.getElementById('foodItemsList');
            var newItem = document.createElement('div');
            newItem.className = 'food-item';
            newItem.innerHTML = `
                <div class="form-group">
                    <label>Tên món:</label>
                    <select name="tenMon[]" class="form-control" required>
                        <option value="" disabled selected>Chọn món</option>
                        <c:forEach var="mon" items="${listMon}">
                            <option value="${mon.idMon}">${mon.tenMon}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Số lượng:</label>
                    <input type="number" name="soLuong[]" class="form-control" min="1" value="1" required>
                </div>
                <button type="button" class="btn btn-danger remove-item"><i class="fas fa-trash"></i></button>
            `;
            foodItemsList.appendChild(newItem);
            attachRemoveEvent(newItem.querySelector('.remove-item'));
        });
        

        function attachRemoveEvent(button) {
            button.addEventListener('click', function() {
                var parentItem = this.parentElement;
                parentItem.remove();
            });
        }
    
        document.querySelectorAll('.remove-item').forEach(function(button) {
            attachRemoveEvent(button);
        });

        var paymentModal = document.getElementById("paymentModal");
        var paymentBtns = document.getElementsByClassName("payment-btn");
        var cancelPaymentBtn = document.getElementById("cancelPayment");

        Array.from(paymentBtns).forEach(function(btn) {
            btn.onclick = function() {
                var orderId = this.getAttribute("data-order-id");
                var total = this.getAttribute("data-total");
                
                document.getElementById("paymentOrderId").value = orderId;
                document.getElementById("paymentTotal").value = total;
                document.getElementById("paymentAmount").textContent = total;
                
                paymentModal.style.display = "block";
            }
        });

        cancelPaymentBtn.onclick = function() {
            paymentModal.style.display = "none";
        }

        document.querySelector("#paymentModal .close").onclick = function() {
            paymentModal.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == paymentModal) {
                paymentModal.style.display = "none";
            }
        }

        // Thêm món cho đơn hàng
        var addFoodModal = document.getElementById("addFoodModal");
        var addFoodBtns = document.getElementsByClassName("add-food-btn");
        var cancelAddFoodBtn = document.getElementById("cancelAddFood");

        Array.from(addFoodBtns).forEach(function(btn) {
            btn.onclick = function() {
                var orderId = this.getAttribute("data-order-id");
                document.getElementById("addFoodOrderId").value = orderId;
                addFoodModal.style.display = "block";
            }
        });

        cancelAddFoodBtn.onclick = function() {
            addFoodModal.style.display = "none";
        }

        document.querySelector("#addFoodModal .close").onclick = function() {
            addFoodModal.style.display = "none";
        }

        // Thêm món mới trong modal thêm món
        document.getElementById('addMoreFoodItemBtn').addEventListener('click', function() {
            var foodItemsList = document.getElementById('addFoodItemsList');
            var newItem = document.createElement('div');
            newItem.className = 'food-item';
            newItem.innerHTML = `
                <div class="form-group">
                    <label>Tên món:</label>
                    <select name="tenMon[]" class="form-control" required>
                        <option value="" disabled selected>Chọn món</option>
                        <c:forEach var="mon" items="${listMon}">
                            <option value="${mon.idMon}">${mon.tenMon}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Số lượng:</label>
                    <input type="number" name="soLuong[]" class="form-control" min="1" value="1" required>
                </div>
                <button type="button" class="btn btn-danger remove-item"><i class="fas fa-trash"></i></button>
            `;
            foodItemsList.appendChild(newItem);
            attachRemoveEvent(newItem.querySelector('.remove-item'));
        });
    </script>
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Lấy tất cả button Thanh toán
        const paymentButtons = document.querySelectorAll('.payment-btn');

        paymentButtons.forEach(button => {
            button.addEventListener('click', function() {
                // Lấy data attributes
                const idTable = this.getAttribute('data-idtable');
                const orderId = this.getAttribute('data-order-id');
                const total = this.getAttribute('data-total');

                // Gán giá trị vào các input ẩn trong modal
                document.getElementById('paymentIdTable').value = idTable;
                document.getElementById('paymentOrderId').value = orderId;
                document.getElementById('paymentTotal').value = total;

                // Hiển thị số tiền trong modal
                document.getElementById('paymentAmount').textContent = total.toLocaleString('vi-VN') + " VNĐ";

                // Mở modal thanh toán
                const paymentModal = document.getElementById('paymentModal');
                paymentModal.style.display = 'block';
            });
        });

        // Đóng modal khi bấm nút Hủy hoặc dấu X
        document.getElementById('cancelPayment').addEventListener('click', function() {
            document.getElementById('paymentModal').style.display = 'none';
        });
        document.querySelector('#paymentModal .close').addEventListener('click', function() {
            document.getElementById('paymentModal').style.display = 'none';
        });
    });
</script>

</body>
</html>