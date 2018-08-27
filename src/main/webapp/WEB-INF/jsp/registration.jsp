<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="registrationOfNewUser"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="registration">
        <table>
            <tr>
                <th>
                    <span/><fmt:message key="role"/>
                </th>
                <th>
                    <select name='role'>
                        <option value="Driver"><fmt:message key="driverUser"/></option>
                        <option value="Client"><fmt:message key="client"/></option>
                    </select>
                </th>
            </tr>
            <tr>
                <th>
                    <span/><fmt:message key="login"/>
                </th>
                <th>
                    <input type="text" name="login"/>
                </th>
            </tr>
            <tr>
                <th>
                    <span/><fmt:message key="phone"/>
                </th>
                <th>
                    <input type="tel" name="phone"/>
                </th>
            </tr>
            <tr>
                <th>
                    <span/><fmt:message key="password"/>
                </th>
                <th>
                    <input type="password" name="password"/>
                </th>
            </tr>
            <tr>
                <th>
                    <span/><fmt:message key="confirmPassword"/>
                </th>
                <th>
                    <input type="password" name="confirmPassword"/>
                </th>
            </tr>
            <tr>
                <c:if test="${not empty requestScope.errors}">
                    <%--<div class="alert alert-danger">--%>
                    <c:forEach items="${requestScope.errors}" var="error">
                        <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
                    </c:forEach>
                    <%--</div>--%>
                </c:if>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="submit" value="<fmt:message key="register"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>

