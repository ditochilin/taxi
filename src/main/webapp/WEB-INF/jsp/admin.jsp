<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="Admin panel"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="navbar.jsp"/>

    <jsp:include page="navbarAdmin.jsp"/>

    <c:choose>
        <c:when test="${itemMenuSelected=='users'}">
            <jsp:include page="users.jsp"/>
        </c:when>
        <c:when test="${itemMenuSelected=='shares'}">
            <jsp:include page="shares.jsp"/>
        </c:when>
        <c:when test="${itemMenuSelected=='carTypes'}">
            <jsp:include page="carTypes.jsp"/>
        </c:when>
    </c:choose>
</div>
</body>
</html>

