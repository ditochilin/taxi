<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>

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
    <p class="text-warning"><c:out value='${errorDescription}'/><c:out value='${exception}'/></p>

    <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
    <button onclick="history.back()">Back to Previous Page</button>
    <br/>
</div>
</body>
</html>