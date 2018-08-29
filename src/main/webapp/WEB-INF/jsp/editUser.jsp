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

    <c:set var="isThisEdition" value="${not empty userDTO}"/>
    <p style="color: red">
        <c:if test="${not empty requestScope.errorMessage}">
            <strong><fmt:message key="error" bundle="${locale}"/></strong> <fmt:message
                key="${requestScope.errorMessage}"
                bundle="${locale}"/><br>
        </c:if>
    </p>

    <form method="post" action="Controller" name="saveUser">
        <input type="hidden" name="command" value="saveUser"/>
        <p><fmt:message key="Edit client's order"/></p>
        <table>
            <c:if test="${isThisEdition eq true}">
                <input type="hidden" name="userId" value="${userDTO.id}"/>
            </c:if>

            <tr>
                <th><fmt:message key="User's name" bundle="${locale}"/></th>
                <th><input type="text" name="userName"
                        <c:if test="${isThisEdition}}"> value="${userDTO.userName}"</c:if>
                />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="Role" bundle="${locale}"/></th>
                <th><select name='roleName'>
                    <c:choose>
                        <c:when test="${isThisEdition}">
                            <c:forEach var="roleItem" items="${roleList}">
                                <c:choose>
                                    <c:when test="${roleItem eq userDTO.role}">
                                        <option value="${userDTO.role}"
                                                selected>${userDTO.role.roleName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${roleItem}">${roleItem.roleName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:when>
                        <c:when test="${not empty roleList}">
                            <option selected disabled>select role</option>
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role}">${role.roleName}</option>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <option selected disabled>no role</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                </th>
            </tr>
            <tr>
                <th><fmt:message key="phone" bundle="${locale}"/></th>
                <th>
                    <input type="tel" name="phone"
                            <c:if test="${isThisEdition}}"> value="${userDTO.phone}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="password" bundle="${locale}"/></th>
                <th>
                    <input type="password" name="password"
                            <c:if test="${isThisEdition}}"> value="${userDTO.password}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="saveUser"
                           value="<fmt:message key="saveBtn" bundle="${locale}"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
