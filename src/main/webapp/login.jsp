<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<title>Login</title>
<head>
    
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form action="login" method="POST">
    <div class="form-group">
        <label for="userName">Username:</label>
        <input type="text" id="userName" name="userName" required>
    </div>
    <div class="form-group">
        <label for="pass">Password:</label>
        <input type="password" id="pass" name="pass" required>
    </div>
                   <button type="submit" class="btn">Login</button>
   </div>
    </form>
</body>
</html>

