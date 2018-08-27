<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="vehicles"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <span><fmt:message key="List of vehicles" bundle="${locale}"/></span>
    <table border="1">
        <tr>
            <th>Driver</th>
            <th>Car type</th>
            <th>Car name</th>
            <th>Number</th>
            <th>Busy</th>
        </tr>
        <c:forEach var="taxi" items="${taxis}">
            <tr>
                <td><c:out value="${taxi.driver}"/></td>
                <td><c:out value="${taxi.carType}"/></td>
                <td><c:out value="${taxi.carNumber}"/></td>
                <td><c:out value="${taxi.busy}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
