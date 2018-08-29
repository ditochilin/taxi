<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 29.08.2018
  Time: 2:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="makeOrder"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <input type="hidden" name="command" value="editUser"/>

    <form method="post" action="Controller" name="editUser">
        <p><fmt:message key="Edit client's order"/></p>
        <table>
            <input type="hidden" name="userId" value="${user.id}"/>
            <tr>
                <th><fmt:message key="User's name" bundle="${locale}"/></th>
                <th><input type="text" name="userName"/></th>
            </tr>
            <tr>
                <th><fmt:message key="Role" bundle="${locale}"/></th>
                <th><select name='roleList'>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role}">${role.roleName}</option>
                    </c:forEach>
                </select>
                </th>
            </tr>
            <tr>
                <th><fmt:message key="phone" bundle="${locale}"/></th>
                <th><input type="tel" name="phone"/></th>
            </tr>
            <tr>
                <th><fmt:message key="password" bundle="${locale}"/></th>
                <th><input type="password" name="password"/></th>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="editUser" value="editUser"
                           title="<fmt:message key="editBtn" bundle="locale"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
