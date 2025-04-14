<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset Password</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <div class="login-container">
        <h2>Reset Password</h2>
        
        <% if(request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="reset-password" method="post">
            <input type="hidden" name="action" value="reset">
            <input type="hidden" name="token" value="${param.token}">
            
            <div class="form-group">
                <label for="password">Password Mới:</label>
                <input type="password" id="password" name="password" required>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Xác Nhận Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            
            <button type="submit" class="btn btn-primary">Reset Password</button>
        </form>
    </div>

    <script>
        document.querySelector('form').onsubmit = function(e) {
            var password = document.getElementById('password').value;
            var confirm = document.getElementById('confirmPassword').value;
            
            if (password !== confirm) {
                alert('Passwords không khớp!');
                e.preventDefault();
            }
        };
    </script>
</body>
</html>
