<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 31.08.2018
  Time: 0:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="editCarType" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="navbarAdmin.jsp"/>

    <c:set var="isThisEdition" value="${not empty carTypeDTO}"/>

    <form method="post" action="/Controller" name="saveCarType">
        <input type="hidden" name="command" value="saveCarType"/>
        <p><fmt:message key="editCarType" bundle="${locale}"/></p>
        <table>
            <c:if test="${isThisEdition eq true}">
                <input type="hidden" name="carTypeId" value="${carTypeDTO.id}"/>
            </c:if>
            <tr>
                <th><fmt:message key="carType" bundle="${locale}"/></th>
                <th><input type="text" name="typeName"
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                value="${carTypeDTO.typeName}"
                            </c:when>
                            <c:otherwise>placeholder="Car type"</c:otherwise>
                        </c:choose>
                />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="price" bundle="${locale}"/></th>
                <th>
                    <input type="number" name="price"
                            <c:if test="${isThisEdition}"> value="${carTypeDTO.price}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <jsp:include page="errorsStack.jsp"/>
            </tr>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="saveCarType"
                           value="<fmt:message key="saveBtn" bundle="${locale}"/>">
                </th>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
