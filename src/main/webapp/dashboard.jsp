<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
    <h2>Welcome, <%= session.getAttribute("username") %></h2> <!-- Or use JSTL if added -->
    <div class="container">
        <h3>Semester Finished.</h3>
        <p>Chill and enjoy your time!</p>
    </div>
</body>
</html>
