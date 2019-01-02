<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="vehicles" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

    <p class="text-info">
        <c:if test="${not empty resultMessage}">
            <fmt:message key="${resultMessage}" bundle="${locale}"/>
        </c:if>
    </p>
    <p class="card-header"><fmt:message key="ListOfVehicles" bundle="${locale}"/></p><br>

    <form action="/Controller" name="addTaxi" method="post">
        <input type="hidden" name="command" value="editTaxi"/>
        <button type="submit" class="btn btn-success btn-sm">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>

    <table class="table table-hover table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th><fmt:message key="driverUser" bundle="${locale}"/></th>
            <th><fmt:message key="carType" bundle="${locale}"/></th>
            <th><fmt:message key="carName" bundle="${locale}"/></th>
            <th><fmt:message key="carNumber" bundle="${locale}"/></th>
            <th><fmt:message key="busy" bundle="${locale}"/></th>
        </tr>
        </thead>
        <c:forEach var="taxi" items="${taxiList}">
            <tr>
                <td><c:out value="${taxi.driver.userName}"/></td>
                <td><c:out value="${taxi.carType.typeName}"/></td>
                <td><c:out value="${taxi.carName}"/></td>
                <td><c:out value="${taxi.carNumber}"/></td>
                <td><c:out value="${taxi.busy}"/></td>
                <td>
                    <form class="d-inline-block float-left mr-3" action="/Controller" name="taxiEdit" method="post">
                        <input type="hidden" name="command" value="editTaxi"/>
                        <input type="hidden" name="taxiId" value="${taxi.id}"/>
                        <button type="submit" class="btn btn-warning btn-sm">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form class="d-inline-block" action="/Controller" name="taxiRemove" method="post">
                        <input type="hidden" name="command" value="removeTaxi"/>
                        <input type="hidden" name="taxiId" value="${taxi.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm">
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
