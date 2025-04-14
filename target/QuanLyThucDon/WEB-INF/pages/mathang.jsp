<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.productDao" %>
<%@ page import="java.util.List" %>
<style>

 .product-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 15px;
        border-bottom: 1px solid #eee;
    }

   .btn-add {
        background-color: #4CAF50;
        color: white;
        padding: 8px 15px;
        border-radius: 4px;
        text-decoration: none;
        font-weight: bold;
        font-size: 0.9em;
        display: inline-flex;
        align-items: center;
        transition: background-color 0.2s;
    }

.product_img {
    width: 200px;
    height: 150px;
    object-fit: cover;        
    border-radius: 8px;       
    box-shadow: 0 4px 8px rgba(0,0,0,0.1); 
    transition: transform 0.3s ease; 
}

.product_img:hover {
    transform: scale(1.05);   
}

</style>
<div  class="product-header">
    <h2>Danh sách thực đơn</h2>
    <button class ="btn-add"> + Thêm thực đơn mới</button>
</div>
<table cellpadding="10" cellspacing="0">
    <tr>
        <th>STT</th>
        <th>Mã Món</th>
        <th>Tên Món</th>
        <th>Danh Mục</th>
        <th>Giá</th>
        <th>Hình Ảnh</th>
        <th>Mô Tả</th>
        <th>Đơn Vị Tính</th>
    </tr>
    <%
        List<Product> listThucDon = productDao.getAllProducts();
        int stt = 1;
        for (Product a : listThucDon) {
            String hinhAnh= "https://drive.google.com/uc?export=view&id="+a.getHinhAnh();
             System.out.println(hinhAnh);
    %>
    <tr>
        <td><%= stt++ %></td>
        <td><%= a.getIdMon() %></td>
        <td><%= a.getTenMon() %></td>
        <td><%= a.getIdDanhMuc() %></td>
        <td><%= a.getGia() %></td>
        <td>
          <img src="${pageContext.request.contextPath}/assets/img/mon_an.jpg" width="100" alt="Hình ảnh" class="product_img">
        </td>
        <td><%= a.getMota() %></td>
        <td><%= a.getDonViTinh() %></td>
    </tr>
    <% } %>
</table>
