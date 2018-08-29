<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="orders" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

    <table border="1">
        <tr>
            <th><fmt:message key="status" bundle="${locale}"/></th>
            <th><fmt:message key="date order" bundle="${locale}"/></th>
            <th><fmt:message key="client" bundle="${locale}"/></th>
            <th><fmt:message key="taxi" bundle="${locale}"/></th>
            <th><fmt:message key="start point" bundle="${locale}"/></th>
            <th><fmt:message key="destination" bundle="${locale}"/></th>
            <th><fmt:message key="distance" bundle="${locale}"/></th>
            <th><fmt:message key="shares" bundle="${locale}"/></th>
            <th><fmt:message key="cost" bundle="${locale}"/></th>
            <th><fmt:message key="feed time" bundle="${locale}"/></th>
            <th><fmt:message key="waiting time" bundle="${locale}"/></th>
        </tr>
        <c:forEach var="order" items="${orderList}">
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
                <td><c:out value="${order.waitingTime}"/></td>
                <td>
                    <form name="orderEdit" method="post">
                        <input type="hidden" name="command" value="editOrder"/>
                        <input type="hidden" name="orderId" value="${order.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form name="orderRemove" method="post">
                        <input type="hidden" name="command" value="removeOrder"/>
                        <input type="hidden" name="orderId" value="${order.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
