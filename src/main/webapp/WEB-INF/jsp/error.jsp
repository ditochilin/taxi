<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error page</title>
    <meta charset="utf-8">
</head>
<body>
    <br/>
    <button onclick="history.back()">Back to Previous Page</button>

    <c:set var="message" value='${requestScope["error"]}' />
    <p style="color: red"><c:out value="${message}"/></p>
        <br/>
    <c:set var="description" value='${requestScope["errorDescription"]}' />
    <p style="color: red; font-size: small"><c:out value="${description}"/></p>

    <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
    <br/>
</body>
</html>