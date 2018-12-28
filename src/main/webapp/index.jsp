<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="login">
        <c:if test="${not empty resultMessage}">
            <span class="text-success"/><fmt:message key="${resultMessage}" bundle="${locale}"/>
        </c:if>
        <c:if test="${not empty warningMessage}">
            <span class="text-warning"/><fmt:message key="${warningMessage}" bundle="${locale}"/>
        </c:if>
        <table class="table table-borderless w-50 d-block d-md-table">
            <tr>
                <th>
                    <fmt:message key="login" bundle="${locale}"/>
                </th>
                <th>
                    <input type="text" name="login"/>
                </th>
            </tr>
            <tr>
                <th>
                    <fmt:message key="password" bundle="${locale}"/>
                </th>
                <th>
                    <input type="password" name="password"/>
                </th>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="submit" value="<fmt:message key="enter" bundle="${locale}"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
