<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thống kê doanh thu</title>
    <style>
        :root {
            --primary: #007BFF;
            --light: #f8f9fa;
            --dark: #343a40;
            --gray: #6c757d;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: var(--light);
            padding: 30px;
            margin: 0;
            color: var(--dark);
        }

        h1 {
            text-align: center;
            color: var(--primary);
            margin-bottom: 40px;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
        }

        form {
            margin-bottom: 30px;
        }

        .form-row {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            gap: 20px;
            flex-wrap: wrap;
        }

        label {
            width: 120px;
            font-weight: 500;
        }

        select, input {
            padding: 10px 14px;
            border: 1px solid #ccc;
            border-radius: 6px;
            flex: 1;
            font-size: 15px;
        }

        button {
            background: var(--primary);
            color: white;
            padding: 10px 18px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 500;
            transition: 0.3s ease;
        }

        button:hover {
            background: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 14px 18px;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: var(--primary);
            color: white;
            text-align: left;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .money {
            text-align: right;
            font-weight: 500;
        }

        .total-row {
            background-color: #e9ecef;
            font-weight: bold;
        }

        .no-data {
            text-align: center;
            padding: 20px;
            font-style: italic;
            color: var(--gray);
        }

        @media (max-width: 600px) {
            label {
                width: 100%;
            }

            .form-row {
                flex-direction: column;
                align-items: flex-start;
            }

            button {
                width: 100%;
            }
        }
    </style>

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
