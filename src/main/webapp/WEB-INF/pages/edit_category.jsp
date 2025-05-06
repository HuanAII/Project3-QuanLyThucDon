<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>

form {
    max-width: 500px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color:rgb(255, 255, 255);
    font-family: Arial, sans-serif;
}

form div {
    margin-bottom: 15px;
}

form label {
    display: block;
    font-weight: bold;
    margin-bottom: 5px;
}

form input[type="text"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

form button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

form button:hover {
    background-color: #0056b3;
}

</style>


<form action="${pageContext.request.contextPath}/admin/thucdon/edit-category" method="post">
    <input type="hidden" name="id" value="${category.id_danhmuc}" />
    <div>
        <label for="name">Tên danh mục:</label>
        <input type="text" id="name" name="name" value="${category.name_danhmuc}" required />
    </div>
    <button type="submit">Cập nhật</button>
</form>