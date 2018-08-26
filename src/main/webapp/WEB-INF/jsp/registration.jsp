<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <title><fmt:message key="registrationOfNewUser"></fmt:message></title>
</head>
<body>
<form action="Controller" method="post">
    <input type="hidden" name="command" value="register">
    <table>
        <tr>
            <th>
                <span/><fmt:message key="role"/>
            </th>
            <th>
                <select name='selectLangs'>
                    <option value="Driver"><fmt:message key="driverUser"></option>
                    <option value="Client"><fmt:message key="client"></option>
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
            <th>
                <input type="submit" name="submit" value="<fmt:message key="enter" />">
            </th>
        </tr>
    </table>
</form>
</body>
</html>

