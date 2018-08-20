<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP Page</title>
</head>
<body>
<h3>Login</h3>
<hr/>
<form name="loginForm" method="POST" action="Controller">
    <input type="hidden" name="command" value="login"/>
    Your phone:
    <input type="text" name="login" value="">
    Password:
    <input type="password" name="password" value="">

    <input type="submit" value="Enter">
</form>
<hr/>
</body>
</html>