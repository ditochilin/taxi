<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="registrationOfNewUser" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="registration">
        <table>
            <tr>
                <th>
                    <span/><fmt:message key="role" bundle="${locale}"/>
                </th>
                <th>
                    <select name='role'>
                        <option value="DRIVER"><fmt:message key="driverUser" bundle="${locale}"/></option>
                        <option value="CLIENT"><fmt:message key="client" bundle="${locale}"/></option>
                    </select>
                </th>
            </tr>
            <tr>
                <th>
                    <span><fmt:message key="login" bundle="${locale}"/></span>
                </th>
                <th>
                    <input type="text" name="login"/>
                </th>
            </tr>
            <tr>
                <th>
                    <span><fmt:message key="phone" bundle="${locale}"/></span>
                </th>
                <th>
                    <input type="tel" name="phone"/>
                </th>
            </tr>
            <tr>
                <th>
                    <span><fmt:message key="password" bundle="${locale}"/></span>
                </th>
                <th>
                    <input type="password" name="password"/>
                </th>
            </tr>
            <tr>
                <th>
                    <span><fmt:message key="confirmPassword" bundle="${locale}"/></span>
                </th>
                <th>
                    <input type="password" name="confirmPassword"/>
                </th>
            </tr>
            <tr>
                <c:if test="${not empty requestScope.errors}">
                    <c:forEach items="${requestScope.errors}" var="error">
                        <strong><fmt:message key="error" bundle="${locale}"/></strong> <fmt:message key="${error}"
                                                                                                    bundle="${locale}"/><br>
                    </c:forEach>
                </c:if>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="action" value="register"
                           title="<fmt:message key="register"/>">
                    <input type="submit" name="action" value="exit"
                           title="<fmt:message key="exit"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>

