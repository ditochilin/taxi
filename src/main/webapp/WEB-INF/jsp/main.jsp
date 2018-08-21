<jsp:directive.page contentType="text/html" pageEncoding="UTF-8"/>

<html>
<head>
    <title> Hello !</title>
</head>
<hr/>
<c:set var="userName" value='${requestScope["user"]}' />
<c:out value="${userName}, Hello! "/>
<hr/>
<a href="Controller">Return to login</a>

</body>
</html>

