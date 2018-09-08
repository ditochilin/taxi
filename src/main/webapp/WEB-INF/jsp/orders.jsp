<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="List of orders" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

    <p style="color: red">
        <c:if test="${not empty resultMessage}">
            <span><fmt:message key="${resultMessage}"/></span>
        </c:if>
    </p>

    <span><fmt:message key="List of orders" bundle="${locale}"/></span>

    <%--<c:if test="${not sessionScope.role eq 'DRIVER'}">--%>
        <form action="/Controller" name="addOrder" method="post">
            <input type="hidden" name="command" value="editOrder"/>
            <button type="submit" class="smallbutton">
                <fmt:message key="addBtn" bundle="${locale}"/>
            </button>
        </form>
    <%--</c:if>--%>

    <table border="1">
        <tr>
            <th><fmt:message key="orderStatus" bundle="${locale}"/></th>
            <th><fmt:message key="dateTime" bundle="${locale}"/></th>
            <th><fmt:message key="carType" bundle="${locale}"/></th>
            <th><fmt:message key="taxi" bundle="${locale}"/></th>
            <th><fmt:message key="startPoint" bundle="${locale}"/></th>
            <th><fmt:message key="endPoint" bundle="${locale}"/></th>
            <th><fmt:message key="cost" bundle="${locale}"/></th>
            <c:if test="${not sessionScope.role eq 'CLIENT'}">
                <th><fmt:message key="distance" bundle="${locale}"/></th>
                <th><fmt:message key="client" bundle="${locale}"/></th>
            </c:if>
            <c:if test="${sessionScope.role eq 'CLIENT'}">
                <th><fmt:message key="shares" bundle="${locale}"/></th>
                <th><fmt:message key="discount" bundle="${locale}"/></th>
            </c:if>
            <th><fmt:message key="feedTime" bundle="${locale}"/></th>
            <th><fmt:message key="waitingTime" bundle="${locale}"/></th>
        </tr>
        <c:forEach var="order" items="${orderList}">
            <td><c:out value="${order.status}"/></td>
            <td><c:out value="${order.dateTime}"/></td>
            <td><c:out value="${order.carType.typeName}"/></td>
            <c:choose>
                <c:when test="${not empty order.taxi}">
                    <td><c:out
                            value="${order.taxi.carName} / ${order.taxi.carNumber} / ${order.taxi.driver.userName} / ${order.taxi.driver.phone}"/></td>
                </c:when>
                <c:otherwise>
                    <td><c:out value=""/></td>
                </c:otherwise>
            </c:choose>
            <td><c:out value="${order.startPoint}"/></td>
            <td><c:out value="${order.endPoint}"/></td>
            <td><c:out value="${order.cost}"/></td>
            <c:if test="${not sessionScope.role eq 'CLIENT'}">
                <td><c:out value="${order.distance}"/></td>
                <td><c:out value="${order.client.userName}"/></td>
            </c:if>
            <c:if test="${sessionScope.role eq 'CLIENT'}">
                <td><c:out value="${order.shares}"/></td>
                <td><c:out value="${order.discount}"/></td>
            </c:if>
            <td><c:out value="${order.feedTime}"/></td>
            <td><c:out value="${order.waitingTime}"/></td>


            <td>
                <form action="/Controller" name="orderEdit" method="post">
                    <input type="hidden" name="command" value="editOrder"/>
                    <input type="hidden" name="orderId" value="${order.id}"/>
                    <button type="submit" class="smallbutton">
                        <fmt:message key="editBtn" bundle="${locale}"/>
                    </button>
                </form>
                <form action="/Controller" name="orderRemove" method="post">
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
