<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.models.Table" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý bàn - Hệ thống nhà hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/list_table.css">
</head>
<body>

        <%
        String addedMsg = (String) request.getAttribute("message");
        if (addedMsg != null) {
    %>
        <div id="toast-notification"><%= addedMsg %></div>
    <%
            request.removeAttribute("addedMsg");
        }
    %>
    <div class="container">
        <div class="page-header">
            <h1 class="page-title">Danh sách bàn</h1>

            <form action="${pageContext.request.contextPath}/Filter_Table_Servlet" method="get" class="filter-form" style="margin-right: 1rem;">
                <label for="filterDate">Lọc theo ngày:</label>
                <input type="date" id="filterDate" name="date"
                       value="<%= request.getParameter("date") != null ? request.getParameter("date") : "" %>"/>
                <button type="submit">Lọc</button>
            </form>


        <button type="button" class="btn-add" onclick="openAddTableModal()">Thêm bàn mới</button>

        </div>

        <!-- Bàn đã đặt -->
        <h2>Bàn đã đặt</h2>
        <div class="tables-grid">
            <%
                List<Table> bookedTables = (List<Table>) request.getAttribute("bookedTables");
                if (bookedTables != null && !bookedTables.isEmpty()) {
                    for (Table table : bookedTables) {
            %>
            <div class="table-card">
                <div class="table-header">
                    <span class="table-status status-reserved">Đã đặt</span>
                    <div class="table-icon">🍽️</div>
                    <h3 class="table-number">Bàn số <%= table.getTableNumber() %></h3>
                    <p class="table-id">Mã bàn: <%= table.getIdTable() %></p>
                    
                    
                </div>
                <div class="table-details">
                    <div class="detail-item"><span class="detail-icon">👥</span> Sức chứa: <%= table.getSeats() %> người</div>
                    <div class="detail-item"><span class="detail-icon">📋</span> Đặt bàn: Đã được đặt</div>
                </div>
                <div class="table-actions">
                    <!-- Form Sửa -->
                    <form action="${pageContext.request.contextPath}/admin/tables" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="<%= table.getIdTable() %>">
                        <button type="submit" class="btn-action btn-edit">
                            <span class="btn-icon">✏️</span> Sửa
                        </button>
                    </form>
                    <!-- Form Xóa -->
                    <form action="${pageContext.request.contextPath}/admin/tables" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= table.getIdTable() %>">
                        <button type="submit" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc muốn xóa bàn này?');">
                            <span class="btn-icon">🗑️</span> Xóa
                        </button>
                    </form>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <p>Không có bàn nào đã được đặt.</p>
            <%
                }
            %>
        </div>

        <!-- Bàn chưa đặt -->
        <h2>Bàn chưa đặt</h2>
        <div class="tables-grid">
            <%
                List<Table> availableTables = (List<Table>) request.getAttribute("availableTables");
                if (availableTables != null && !availableTables.isEmpty()) {
                    for (Table table : availableTables) {
            %>
            <div class="table-card">
                <div class="table-header">
                    <span class="table-status status-available">Trống</span>
                    <div class="table-icon">🍽️</div>
                    <h3 class="table-number">Bàn số <%= table.getTableNumber() %></h3>
                    <p class="table-id">Mã bàn: <%= table.getIdTable() %></p>
                </div>
                <div class="table-details">
                    <div class="detail-item"><span class="detail-icon">👥</span> Sức chứa: <%= table.getSeats() %> người</div>
                    <div class="detail-item"><span class="detail-icon">📋</span> Chưa được đặt</div>
                </div>
                <div class="table-actions">
                    <!-- Form Sửa -->
                    <button class="btn-action btn-edit" onclick="openEditTableModal('<%= table.getIdTable() %>', '<%= table.getTableNumber() %>', '<%= table.getSeats() %>')">
                        <span class="btn-icon">✏️</span> Sửa
                    </button>

                    <!-- Form Xóa -->
                    <form action="${pageContext.request.contextPath}/admin/tables" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= table.getIdTable() %>">
                        <button type="submit" class="btn-action btn-delete" onclick="return confirm('Bạn có chắc muốn xóa bàn này?');">
                            <span class="btn-icon">🗑️</span> Xóa
                        </button>
                    </form>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <p>Không có bàn nào đang trống.</p>
            <%
                }
            %>
        </div>
    </div>

    <div id="editOverlay" class="overlay" onclick="closeEditTableModal()"></div>

<div id="editTableModal" class="modal">
    <form action="${pageContext.request.contextPath}/admin/tables" method="post" class="modal-content" onsubmit="return validateEditForm()">
        <input type="hidden" name="action" value="edit">

        <h2>Sửa bàn</h2>

        <label for="editTableId">Mã bàn:</label>
        <input type="text" id="editTableId" name="idTable" readonly>

        <label for="editTableNumber">Số bàn:</label>
        <input type="number" id="editTableNumber" name="tableNumber" required>

        <label for="editSeats">Số chỗ ngồi:</label>
        <input type="number" id="editSeats" name="seats" required>

        <div class="modal-buttons">
            <button type="submit" class="btn-submit">Lưu</button>
            <button type="button" onclick="closeEditTableModal()" class="btn-cancel">Hủy</button>
        </div>
    </form>
</div>


    <div id="overlay" class="overlay" onclick="closeAddTableModal()"></div>

    <div id="addTableModal" class="modal">
        <form action="${pageContext.request.contextPath}/admin/tables" method="post" class="modal-content" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="add">
            <h2>Thêm bàn mới</h2>
            <input type="hidden" name="action" value="add">

            <label for="tableId">Mã bàn:</label>
            <input type="text" id="tableId" name="idTable" required>

            <label for="tableNumber">Số bàn:</label>
            <input type="number" id="tableNumber" name="tableNumber" required>

            <label for="seats">Số chỗ ngồi:</label>
            <input type="number" id="seats" name="seats" required>

            <div class="modal-buttons">
                <button type="submit" class="btn-submit">Thêm</button>
                <button type="button" onclick="closeAddTableModal()" class="btn-cancel">Hủy</button>
            </div>
        </form>
    </div>

    <script>
        function openAddTableModal() {
            document.getElementById("overlay").style.display = "block";
            document.getElementById("addTableModal").style.display = "block";
        }

        function closeAddTableModal() {
            document.getElementById("overlay").style.display = "none";
            document.getElementById("addTableModal").style.display = "none";
        }

        function validateForm() {
            return true;
        }
    </script>

    <script>
    function openEditTableModal(id, number, seats) {
        document.getElementById("editOverlay").style.display = "block";
        document.getElementById("editTableModal").style.display = "block";

        // Gán dữ liệu vào form sửa
        document.getElementById("editTableId").value = id;
        document.getElementById("editTableNumber").value = number;
        document.getElementById("editSeats").value = seats;
    }

    function closeEditTableModal() {
        document.getElementById("editOverlay").style.display = "none";
        document.getElementById("editTableModal").style.display = "none";
    }

    function validateEditForm() {
        return true;
    }
</script>

</body>
</html>
