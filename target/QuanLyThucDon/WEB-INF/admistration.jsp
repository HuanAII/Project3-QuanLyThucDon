<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Trị Nhà Hàng</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admistration.css">
</head>
<body>
    <!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <div class="logo-container">
            <div class="logo">
                <i class="fas fa-utensils"></i>
                <span>Nhà Hàng</span>
            </div>
        </div>

        <ul class="nav-menu">
            <!-- Thống kê -->
            <li class="nav-item">
                <div class="nav-link toggle-submenu" id="thongke-toggle">
                    <div class="menu-item">
                        <i class="fas fa-book-open"></i>
                        <span>Thống kê</span>
                    </div>
                    <i class="fas fa-chevron-down"></i>
                </div>
                <ul class="submenu" id="thongke-submenu">
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/thongke_account" class="submenu-item">
                            Thống kê tài khoản
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/thongke_thucdon" class="submenu-item">
                            Thống kê thực đơn
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/thongke_doanhthu" class="submenu-item">
                            Thống kê doanh thu
                        </a>
                    </li>
                </ul>
            </li>

            <!-- Yêu cầu đặt món -->
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/datmon" class="nav-link">
                    <i class="fas fa-clipboard-list"></i>
                    <span>Yêu cầu đặt món</span>
                </a>
            </li>

            <!-- Yêu cầu đặt bàn -->
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/Waiting_booking_table" class="nav-link">
                    <i class="fas fa-calendar-check"></i>
                    <span>Yêu cầu đặt bàn</span>
                </a>
            </li>

            <!-- Hóa đơn -->
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/list-hoadon" class="nav-link">
                    <i class="fas fa-file-invoice"></i>
                    <span>Hóa đơn</span>
                </a>
            </li>

            <!-- Thực đơn -->
            <li class="nav-item">
                <div class="nav-link toggle-submenu" id="thucdon-toggle">
                    <div>
                        <i class="fas fa-book-open"></i>
                        <span>Thực đơn</span>
                    </div>
                    <i class="fas fa-chevron-down"></i>
                </div>
                <ul class="submenu" id="thucdon-submenu">
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/thucdon/mathang" class="submenu-item">
                            Danh sách thực đơn
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/thucdon/danhmuc" class="submenu-item">
                            Danh mục
                        </a>
                    </li>
                </ul>
            </li>

            <!-- Bàn ăn -->
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/tables" class="nav-link">
                    <i class="fas fa-chair"></i>
                    <span>Bàn ăn</span>
                </a>
            </li>

            <!-- Khách hàng -->
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/customers" class="nav-link">
                    <i class="fas fa-users"></i>
                    <span>Khách hàng</span>
                </a>
            </li>

            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/list-khuyenmai" class="nav-link">
                    <i class="fa-solid fa-ticket"></i>
                    <span>Khuyến Mãi</span>
                </a>
            </li>


            <!-- Đăng xuất -->
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/LogoutServlet" class="nav-link">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Đăng xuất</span>
                </a>
            </li>
        </ul>
    </div>

    <!-- Main Content -->
    <div class="main-content" id="main-content">
        <div class="content-container">
            <jsp:include page="${contentPage}" />
        </div>
    </div>

    <!-- JavaScript -->
    <script>
        // Toggle từng submenu
        document.getElementById('thongke-toggle').addEventListener('click', function () {
            this.classList.toggle('active');
            document.getElementById('thongke-submenu').classList.toggle('active');
        });

        document.getElementById('thucdon-toggle').addEventListener('click', function () {
            this.classList.toggle('active');
            document.getElementById('thucdon-submenu').classList.toggle('active');
        });

        // Toggle sidebar
        document.getElementById('toggle-sidebar').addEventListener('click', function () {
            document.getElementById('sidebar').classList.toggle('active');
        });

        // Active menu highlight
        document.addEventListener('DOMContentLoaded', function () {
            const currentPath = window.location.pathname;
            const navLinks = document.querySelectorAll('.nav-link a, .submenu-item');

            navLinks.forEach(link => {
                const href = link.getAttribute('href');
                if (href && currentPath.includes(href)) {
                    link.classList.add('active');

                    const submenu = link.closest('.submenu');
                    if (submenu) {
                        submenu.classList.add('active');
                        const toggle = submenu.previousElementSibling;
                        if (toggle) toggle.classList.add('active');
                    }
                }
            });
        });
    </script>
</body>
