  // chuyen menu
  document.querySelector('.mobile-menu-toggle').addEventListener('click', function() {
    const nav = document.querySelector('.horizontal-nav');
    nav.classList.toggle('active');
});


const filterOptions = document.querySelectorAll('.filter-option');
filterOptions.forEach(option => {
    option.addEventListener('click', function() {
        filterOptions.forEach(opt => opt.classList.remove('active'));
        this.classList.add('active');
    }); 
});






const productsData = [
    {
        id: 1,
        name: "Buffet Ăn Vặt Tokbokki Cho Tín Đồ Mê Món Ăn Hàn Quốc",
        price: 69000,
        originalPrice: 69000,
        discount: 0,
        image: "assets/img/ptr2.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: false
    },
    {
        id: 2,
        name: "Buffet Trưa Hải Sản Thượng Hạng - Bao Gồm thức uống",
        price: 69000,
        originalPrice: 69000,
        discount: 0,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: false
    },
    {
        id: 3,
        name: "Buffet Xèo Xèo Thịt Nướng Chuẩn Vị Hàn Quốc",
        price: 125000,
        originalPrice: 150000,
        discount: 17,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: true
    },
    {
        id: 4,
        name: "Buffet Lẩu Bò Mỹ, Hải Sản Chuẩn Vị Hàn Quốc",
        price: 145000,
        originalPrice: 145000,
        discount: 0,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: true
    },
    {
        id: 5,
        name: "Buffet Nướng & Lẩu Hơn 130 Món Hải Sản, Bò Mỹ Cùng 5 Vị Lẩu Đặc Biệt",
        price: 229000,
        originalPrice: 315000,
        discount: 28,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: false
    },
    {
        id: 6,
        name: "Japanese BBQ Buffet Nướng Và Lẩu Menu Vip",
        price: 292000,
        originalPrice: 292000,
        discount: 0,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: false
    },
    {
        id: 7,
        name: "Buffet Lẩu Nhật Hải Sản, Bò Mỹ & Dimsum Với 60 Món Nhúng Và 5 Loại Nước Lẩu",
        price: 295000,
        originalPrice: 310000,
        discount: 5,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: false
    },
    {
        id: 8,
        name: "Buffet Nướng Lẩu Thượng Hạng Nhật Bản Hơn 80 Món",
        price: 299000,
        originalPrice: 299000,
        discount: 0,
        image: "assets/img/ptr3.jpg",
        type: "Buffet",
        brand: "Ant Bistro",
        isNew: true
    }
];

function displayProducts(products) {
    const productsContainer = document.querySelector('.products-container');
    productsContainer.innerHTML = '';
    if (products.length === 0) {
        productsContainer.innerHTML = '<div class="no-products">Không tìm thấy sản phẩm phù hợp</div>';
        return;
    }
    
    // Tạo HTML 
    products.forEach(product => {
        const productCard = document.createElement('div');
        productCard.className = 'product-card';
        productCard.setAttribute('data-id', product.id);
        productCard.setAttribute('data-price', product.price);
        productCard.setAttribute('data-name', product.name);
        
        let productHTML = `
            ${product.discount > 0 ? `<div class="discount-badge">${product.discount}%</div>` : ''}
            <img src="${product.image}" alt="${product.name}" class="product-image">
            <div class="product-info">
                <div class="product-title">${product.name}</div>
                <div class="product-price">
                    <div class="current-price">${product.price.toLocaleString()}₫</div>
                    ${product.discount > 0 ? `<div class="original-price">${product.originalPrice.toLocaleString()}₫</div>` : ''}
                </div>
                <div class="action-buttons">
                    <div class="action-button favorite">
                        <i class="fas fa-link"></i>
                    </div>
                    <div class="action-button add-to-cart">
                        <i class="fas fa-shopping-cart"></i>
                    </div>
                </div>
            </div>
        `;
        
        productCard.innerHTML = productHTML;
        productsContainer.appendChild(productCard);
    });
    
    document.querySelectorAll('.action-button.favorite').forEach(button => {
        button.addEventListener('click', function() {
            alert('Đã thêm vào danh sách yêu thích!');
        });
    });
    
    document.querySelectorAll('.action-button.add-to-cart').forEach(button => {
        button.addEventListener('click', function() {
            alert('Đã thêm vào giỏ hàng!');
        });
    });
}

// Biến lưu trữ sản phẩm đã được lọc và sắp xếp
let filteredAndSortedProducts = [...productsData];
let activeFilters = {
    priceRanges: [],
    brands: [],
    types: []
};
let activeSortType = 'default';

// Hàm sắp xếp sản phẩm
function sortProducts(products, sortType) {
    let sortedProducts = [...products];
    
    switch(sortType) {
        case 'name-asc':
            // Sắp xếp theo tên A-Z
            sortedProducts.sort((a, b) => a.name.localeCompare(b.name));
            break;
        case 'name-desc':
            // Sắp xếp theo tên Z-A
            sortedProducts.sort((a, b) => b.name.localeCompare(a.name));
            break;
        case 'price-asc':
            // Sắp xếp theo giá tăng dần
            sortedProducts.sort((a, b) => a.price - b.price);
            break;
        case 'price-desc':
            // Sắp xếp theo giá giảm dần
            sortedProducts.sort((a, b) => b.price - a.price);
            break;
        case 'newest':
            sortedProducts.sort((a, b) => (b.isNew ? 1 : 0) - (a.isNew ? 1 : 0));
            break;
        default:
            break;
    }
    
    return sortedProducts;
}

// Hàm lọc sản phẩm theo các tiêu chí
function filterProducts(products) {
    return products.filter(product => {
        // Lọc theo khoảng giá
        if (activeFilters.priceRanges.length > 0) {
            const matchesPrice = activeFilters.priceRanges.some(range => {
                switch(range) {
                    case 'price-1': return product.price < 100000;
                    case 'price-2': return product.price >= 100000 && product.price < 200000;
                    case 'price-3': return product.price >= 200000 && product.price < 300000;
                    case 'price-4': return product.price >= 300000 && product.price < 500000;
                    case 'price-5': return product.price >= 500000 && product.price < 1000000;
                    case 'price-6': return product.price >= 1000000;
                    default: return false;
                }
            });
            
            if (!matchesPrice) return false;
        }
        
        // Lọc theo thương hiệu
        if (activeFilters.brands.length > 0 && !activeFilters.brands.includes(product.brand)){
            return false;
        }
        // Lọc theo loại sản phẩm
        if (activeFilters.types.length > 0 && !activeFilters.types.includes(product.type)){
            return false;
        }
        return true;
    });
}

function updateProductsDisplay() {
    let filteredProducts = filterProducts(productsData);
    filteredAndSortedProducts = sortProducts(filteredProducts, activeSortType);
    displayProducts(filteredAndSortedProducts);
    const resultCount = document.querySelector('.result-count');
    if (resultCount) {
        resultCount.textContent = `Hiển thị ${filteredAndSortedProducts.length} sản phẩm`;
    }
}

document.addEventListener('DOMContentLoaded', function() {
    displayProducts(productsData);
    const filterToggle = document.getElementById('filter-toggle');
    const filterDropdown = document.getElementById('filter-dropdown');
    
    if (filterToggle && filterDropdown) {
        filterToggle.addEventListener('click', function() {
            filterDropdown.classList.toggle('active');
        });
        
        document.addEventListener('click', function(event) {
            if (!filterToggle.contains(event.target) && !filterDropdown.contains(event.target)) {
                filterDropdown.classList.remove('active');
            }
        });
    }
    // Xử lý các mục lọc 
    document.querySelectorAll('.filter-header').forEach(header => {
        header.addEventListener('click', function() {
            const content = this.nextElementSibling;
            const icon = this.querySelector('.filter-toggle i');
            if (icon.classList.contains('fa-minus')) {
                icon.classList.remove('fa-minus');
                icon.classList.add('fa-plus');
                content.style.display = 'none';
            } else {
                icon.classList.remove('fa-plus');
                icon.classList.add('fa-minus');
                content.style.display = 'block';
            }
        });
    });
    
    // Xử lý nút sắp xếp
    document.querySelectorAll('.sort-button').forEach(button => {
        button.addEventListener('click', function() {
            document.querySelectorAll('.sort-button').forEach(btn => {
                btn.classList.remove('active');
            });
            this.classList.add('active');
            activeSortType = this.getAttribute('data-sort');
            updateProductsDisplay();
        });
    });
    
    // Xử lý checkbox lọc giá
    document.querySelectorAll('.filter-checkbox[id^="price-"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const priceRangeId = this.id;
            if (this.checked) {
                if (!activeFilters.priceRanges.includes(priceRangeId)) {
                    activeFilters.priceRanges.push(priceRangeId);
                }
            } else {     
                activeFilters.priceRanges = activeFilters.priceRanges.filter(id => id !== priceRangeId);
            }
            updateProductsDisplay();
        });
    });
    
    // Xử lý checkbox lọc thương hiệu
    document.querySelectorAll('.filter-checkbox[id^="brand-"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const brandId = this.id.replace('brand-', '');  
            if (this.checked) {
                if (!activeFilters.brands.includes(brandId)) {
                    activeFilters.brands.push(brandId);
                }
            } else {
                activeFilters.brands = activeFilters.brands.filter(id => id !== brandId);
            }
            updateProductsDisplay();
        });
    });
    
    // Xử lý checkbox lọc loại sản phẩm
    document.querySelectorAll('.filter-checkbox[id^="type-"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const typeId = this.id.replace('type-', '');     
            if (this.checked) {
                if (!activeFilters.types.includes(typeId)) {
                    activeFilters.types.push(typeId);
                }
            } else {
                activeFilters.types = activeFilters.types.filter(id => id !== typeId);
            }
            updateProductsDisplay();
        });
    });
});

function addResultCount() {
    const productsContainer = document.querySelector('.products-container');
    const resultCountDiv = document.createElement('div');
    resultCountDiv.className = 'result-count';
    resultCountDiv.textContent = `Hiển thị ${productsData.length} sản phẩm`;
    resultCountDiv.style.marginBottom = '20px';
    resultCountDiv.style.fontSize = '14px';
    resultCountDiv.style.color = '#ccc';
    if (productsContainer.firstChild) {
        productsContainer.insertBefore(resultCountDiv, productsContainer.firstChild);
    } else {
        productsContainer.appendChild(resultCountDiv);
    }
}
document.addEventListener('DOMContentLoaded', function() {
    addResultCount();
});