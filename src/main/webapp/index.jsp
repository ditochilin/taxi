<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <title>JSP Page</title>
</head>
<body>
<h3>Login</h3>
<hr/>
<form name="loginForm" method="POST" action="Controller">
    <input type="hidden" name="command" value="login"/>
    <table>
        <tr>
            <th>Your nickname:</th>
            <th><input type="text" name="login" value=""><br/><br/></th>
        </tr>
        <tr>
            <th>Password:</th>
            <th><input type="password" name="password" value=""><br/></th>
        </tr>
    </table>

    <input type="submit" value="Enter">
</form>
<hr/>
</body>
</html>