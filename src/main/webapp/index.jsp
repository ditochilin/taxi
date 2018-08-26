<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

<div>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="login">
        <table>
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
                    <span/><fmt:message key="password"/>
                </th>
                <th>
                    <input type="password" name="password"/>
                </th>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="submit" value="<fmt:message key="enter" />">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
