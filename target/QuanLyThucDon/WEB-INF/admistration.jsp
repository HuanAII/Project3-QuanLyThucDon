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
        
        /* Mobile Toggle Button */
        .mobile-toggle {
            display: none;
            position: fixed;
            top: 15px;
            left: 15px;
            background: white;
            border: none;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            font-size: 1.2rem;
            color: var(--dark-color);
            cursor: pointer;
            box-shadow: var(--box-shadow);
            z-index: 90;
            align-items: center;
            justify-content: center;
        }
        
        /* Content Container */
        .content-container {
            background-color: white;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            padding: 25px;
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
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/thongke" class="nav-link active">
                    <i class="fas fa-chart-bar"></i>
                    <span>Thống kê</span>
                </a>
            </li>
            
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/datmon" class="nav-link">
                    <i class="fas fa-clipboard-list"></i>
                    <span>Yêu cầu đặt món</span>
                </a>
            </li>
            
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/Waiting_booking_table" class="nav-link">
                    <i class="fas fa-calendar-check"></i>
                    <span>Yêu cầu đặt bàn</span>
                </a>
            </li>
            
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/list-hoadon" class="nav-link">
                    <i class="fas fa-file-invoice"></i>
                    <span>Hóa đơn</span>
                </a>
            </li>
            
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
            
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/tables" class="nav-link">
                    <i class="fas fa-chair"></i>
                    <span>Bàn ăn</span>
                </a>
            </li>
            
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/khachhang" class="nav-link">
                    <i class="fas fa-users"></i>
                    <span>Khách hàng</span>
                </a>
            </li>
<!-- 
             <li class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/reservationHistory" class="nav-link">
                    <i class="fas fa-users"></i>
                    <span>Khách hàng</span>
                </a>
            </li> -->

             <li class="nav-item">
                <a href="javascript:void(0)" onclick="logout()" class="nav-link">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Đăng xuất</span>
                </a>
            </li>
        </ul>
    </div>
    
    <!-- Mobile Toggle Button -->
    <button class="mobile-toggle" id="mobile-toggle">
        <i class="fas fa-bars"></i>
    </button>
    
    <!-- Main Content -->
    <div class="main-content" id="main-content">
        <!-- Đã xóa hoàn toàn phần user-profile có biểu tượng thông báo và Admin -->
        
        <div class="content-container">
            <jsp:include page="${contentPage}" />
        </div>
    </div>
    
    <!-- JavaScript -->
    <script>
        document.getElementById('thucdon-toggle').addEventListener('click', function() {
            this.classList.toggle('active');
            const submenu = document.getElementById('thucdon-submenu');
            submenu.classList.toggle('active');
        });
        
        // Toggle sidebar on mobile
        document.getElementById('mobile-toggle').addEventListener('click', function() {
            document.getElementById('sidebar').classList.toggle('active');
        });
        
        // Initialize active menu item based on current URL
        document.addEventListener('DOMContentLoaded', function() {
            const currentPath = window.location.pathname;
            const navLinks = document.querySelectorAll('.nav-link');
            
            navLinks.forEach(link => {
                if (link.getAttribute('href') && currentPath.includes(link.getAttribute('href'))) {
                    link.classList.add('active');
                    
                    // If it's a submenu item, expand the parent menu
                    const parentToggle = link.closest('.nav-item').querySelector('.toggle-submenu');
                    if (parentToggle) {
                        parentToggle.classList.add('active');
                        const submenu = link.closest('.submenu');
                        if (submenu) {
                            submenu.classList.add('active');
                        }
                    }
                } else if (!link.classList.contains('toggle-submenu')) {
                    link.classList.remove('active');
                }
            });
        });

        function logout() {
            if(confirm('Bạn có chắc chắn muốn đăng xuất?')) {
                // Xóa cache và chuyển hướng
                fetch('${pageContext.request.contextPath}/admin/logout', {
                    method: 'GET',
                    cache: 'no-cache',
                    credentials: 'same-origin',
                    headers: {
                        'Pragma': 'no-cache',
                        'Cache-Control': 'no-cache'
                    }
                }).then(function() {
                    // Xóa session storage và local storage
                    sessionStorage.clear();
                    localStorage.clear();
                    // Chuyển hướng về trang index
                    window.location.href = '${pageContext.request.contextPath}/index.jsp';
                });
                return false;
            }
        }
    </script>
</body>
</html>