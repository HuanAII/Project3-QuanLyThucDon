<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Trị Nhà Hàng</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #ff6b35;
            --primary-light: #ffe0d5;
            --secondary-color: #2b50aa;
            --dark-color: #2d3142;
            --light-color: #f5f7fb;
            --accent-color: #00a896;
            --border-radius: 10px;
            --box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
            --transition: all 0.3s ease;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Montserrat', sans-serif;
            background-color: var(--light-color);
            color: var(--dark-color);
            display: flex;
            min-height: 100vh;
        }
        
        /* Sidebar */
        .sidebar {
            width: 250px;
            background-color: white;
            box-shadow: var(--box-shadow);
            padding: 20px 0;
            position: fixed;
            height: 100vh;
            overflow-y: auto;
            transition: var(--transition);
            z-index: 100;
        }
        
        .logo-container {
            padding: 0 20px 20px;
            margin-bottom: 20px;
            border-bottom: 1px solid #eee;
            display: flex;
            align-items: center;
        }
        
        .logo {
            font-weight: 700;
            font-size: 1.5rem;
            color: var(--primary-color);
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .nav-menu {
            list-style-type: none;
        }
        
        .nav-item {
            margin: 5px 0;
        }
        
        .nav-link {
            display: flex;
            align-items: center;
            padding: 12px 20px;
            color: var(--dark-color);
            text-decoration: none;
            font-weight: 500;
            transition: var(--transition);
            border-left: 4px solid transparent;
        }
        
        .nav-link i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }
        
        .nav-link:hover, .nav-link.active {
            background-color: var(--primary-light);
            color: var(--primary-color);
            border-left-color: var(--primary-color);
        }
        
        .toggle-submenu {
            cursor: pointer;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .toggle-submenu i.fa-chevron-down {
            transition: var(--transition);
        }
        
        .toggle-submenu.active i.fa-chevron-down {
            transform: rotate(180deg);
        }
        
        .submenu {
            list-style-type: none;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease-out;
            background-color: #f9fafc;
        }
        
        .submenu.active {
            max-height: 200px;
        }
        
        .submenu-item {
            padding: 10px 20px 10px 55px;
            display: block;
            color: var(--dark-color);
            text-decoration: none;
            font-size: 0.9rem;
            transition: var(--transition);
        }
        
        .menu-item {
            text-decoration: none;
        }
        .submenu-item:hover {
            color: var(--primary-color);
            background-color: #f0f2f5;
        }
        
        /* Main Content */
        .main-content {
            flex: 1;
            margin-left: 250px;
            padding: 20px;
            transition: var(--transition);
        }
        
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            padding: 15px;
            background-color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
        }
        
        .page-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: var(--dark-color);
        }
        
        .toggle-sidebar {
            display: none;
            background: none;
            border: none;
            font-size: 1.5rem;
            color: var(--dark-color);
            cursor: pointer;
        }
        
        .user-actions {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        
        .notification-btn, .profile-btn {
            background: none;
            border: none;
            font-size: 1.2rem;
            color: var(--dark-color);
            cursor: pointer;
            transition: var(--transition);
            position: relative;
        }
        
        .notification-btn:hover, .profile-btn:hover {
            color: var(--primary-color);
        }
        
        .notification-count {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: var(--primary-color);
            color: white;
            font-size: 0.7rem;
            width: 18px;
            height: 18px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .profile-avatar {
            width: 35px;
            height: 35px;
            border-radius: 50%;
            object-fit: cover;
        }
        
        /* Content Container */
        .content-container {
            background-color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            margin-bottom: 20px;
        }
        
        /* Responsive Design */

        @media (max-width: 992px) {
            .sidebar {
                transform: translateX(-100%);
            }
            
            .sidebar.active {
                transform: translateX(0);
            }
            
            .main-content {
                margin-left: 0;
            }
            
            .mobile-toggle {
                display: flex;
            }
        }
        
        @media (max-width: 576px) {
            .content-container {
                padding: 15px;
            }
        }
    </style>
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
                <a href="${pageContext.request.contextPath}/admin/logOut" class="nav-link">
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
