<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
</head>
<body>
<div class="container">
    <br/>
    <p>Error!</p>
    <br/>

    <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
    <p style="color: red; font-size: small"><c:out value='${errorDescription}'/><c:out value='${exception}'/></p>

    <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
    <button onclick="history.back()">Back to Previous Page</button>
    <br/>
</div>
</body>
</html>