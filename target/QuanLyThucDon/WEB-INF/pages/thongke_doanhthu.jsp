<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thống kê doanh thu</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/thongke_doanhthu.css">

    <script>
        function updateInputType() {
            const loai = document.querySelector('select[name="loai"]').value;
            const startDate = document.querySelector('input[name="startDate"]');
            const endDate = document.querySelector('input[name="endDate"]');

            if (loai === 'ngay') {
                startDate.type = 'date';
                endDate.type = 'date';
            } else if (loai === 'thang') {
                startDate.type = 'month';
                endDate.type = 'month';
            } else if (loai === 'nam') {
                startDate.type = 'number';
                endDate.type = 'number';
                startDate.min = '2000';
                startDate.max = '2100';
                endDate.min = '2000';
                endDate.max = '2100';
            }
        }

        window.onload = function () {
            updateInputType();
            document.querySelector('select[name="loai"]').addEventListener('change', updateInputType);
        }
    </script>
</head>

<body>
<div class="container">
    <h1>Thống kê doanh thu</h1>

    <form method="post" action="${pageContext.request.contextPath}/admin/thongke_doanhthu">
        <div class="form-row">
            <label for="loai">Thống kê theo:</label>
            <select name="loai" id="loai">
                <option value="ngay" ${loai == 'ngay' ? 'selected' : ''}>Ngày</option>
                <option value="thang" ${loai == 'thang' ? 'selected' : ''}>Tháng</option>
                <option value="nam" ${loai == 'nam' ? 'selected' : ''}>Năm</option>
            </select>
        </div>

        <div class="form-row">
            <label for="startDate">Từ:</label>
            <input type="text" name="startDate" id="startDate" value="${startDate}" placeholder="yyyy-MM-dd / yyyy-MM / yyyy" />
        </div>

        <div class="form-row">
            <label for="endDate">Đến:</label>
            <input type="text" name="endDate" id="endDate" value="${endDate}" placeholder="yyyy-MM-dd / yyyy-MM / yyyy" />
        </div>

        <div class="form-row">
            <button type="submit">Thống kê</button>
        </div>
    </form>

    <c:if test="${not empty thongKeMap}">
        <table>
            <thead>
                <tr>
                    <th>Thời gian</th>
                    <th class="money">Doanh thu (VNĐ)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${thongKeMap}">
                    <tr>
                        <td>${entry.key}</td>
                        <td class="money">
                            <fmt:formatNumber value="${entry.value}" pattern="#,##0" />
                        </td>
                    </tr>
                </c:forEach>
                <tr class="total-row">
                    <td>Tổng cộng</td>
                    <td class="money">
                        <fmt:formatNumber value="${tongDoanhThu}" pattern="#,##0" />
                    </td>
                </tr>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty thongKeMap}">
        <div class="no-data">Không có dữ liệu trong khoảng thời gian này.</div>
    </c:if>
</div>
</body>
</html>
