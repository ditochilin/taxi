<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<fmt:setBundle basename="buttons" var="buttons"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="Admin panel" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <span><fmt:message key="List of users" bundle="${locale}"/></span>
    <table border="1">
        <tr>
            <th>User's name</th>
            <th>Role</th>
            <th>Phone</th>
            <th>Password</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.userName}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.phone}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td>
                    <form name="userEdit" method="post">
                        <input type="hidden" name="command" value="editUser"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn" bundle="${buttons}"/>
                        </button>
                    </form>
                    <form name="userRemove" method="post">
                        <input type="hidden" name="command" value="removeUser"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn" bundle="${buttons}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>

