<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="orders"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

    <table border="1">
        <tr>
            <th>Status</th>
            <th>Date</th>
            <th>Client</th>
            <th>Taxi</th>
            <th>Start point</th>
            <th>Destination</th>
            <th>Distance</th>
            <th>Shares</th>
            <th>Cost</th>
            <th>Feed time</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td><c:out value="${order.status}"/></td>
                <td><c:out value="${order.dateTime}"/></td>
                <td><c:out value="${order.client}"/></td>
                <td><c:out value="${order.taxi}"/></td>
                <td><c:out value="${order.startPoint}"/></td>
                <td><c:out value="${order.endPoint}"/></td>
                <td><c:out value="${order.distance}"/></td>
                <td><c:out value="${order.shares}"/></td>
                <td><c:out value="${order.cost}"/></td>
                <td><c:out value="${order.feedTime}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
