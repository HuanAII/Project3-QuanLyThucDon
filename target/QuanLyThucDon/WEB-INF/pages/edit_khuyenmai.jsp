<%@ page import="com.example.dao.KhuyenMaiDAO" %>
<%@ page import="com.example.models.KhuyenMai" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    String proId = request.getParameter("pro_id");
    if (proId == null || proId.trim().isEmpty()) {
        out.println("<h3>Không có mã sản phẩm được cung cấp!</h3>");
        return;
    }

    Connection conn = (Connection) application.getAttribute("DBConnection");
    KhuyenMaiDAO dao = new KhuyenMaiDAO(conn);
    KhuyenMai km = dao.findById(proId);

    if (km == null) {
        out.println("<h3>Không tìm thấy khuyến mãi với Pro ID: " + proId + "</h3>");
        return;
    }

    // Định dạng ngày sang yyyy-MM-dd cho input type=date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String startDate = km.getStartDate() != null ? sdf.format(km.getStartDate()) : "";
    String endDate = km.getEndDate() != null ? sdf.format(km.getEndDate()) : "";
%>

<html>
<head>
    <title>Sửa khuyến mãi</title>
</head>
<body>
<h2>Sửa khuyến mãi</h2>
<form action="${pageContext.request.contextPath}/admin/sua-khuyen-mai" method="post">
    <input type="hidden" name="pro_id" value="<%= km.getProId() %>">
    Giảm giá (%): <input type="number" name="discount" step="0.01" value="<%= km.getDiscount() %>" required><br>
    Mã giảm giá: <input type="text" name="ma_giam_gia" value="<%= km.getMaGiamGia() %>" required><br>
    Ngày bắt đầu: <input type="date" name="start_date" value="<%= startDate %>" required><br>
    Ngày kết thúc: <input type="date" name="end_date" value="<%= endDate %>" required><br>
    <button type="submit">Cập nhật</button>
</form>
<a href="${pageContext.request.contextPath}/admin/khuyen-mai">Quay lại</a>
</body>
</html>
