<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 30.08.2018
  Time: 1:28
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
    <title><fmt:message key="Edit share" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="navbarAdmin.jsp"/>

    <c:set var="isThisEdition" value="${not empty shareDTO}"/>

    <form method="post" action="/Controller" name="saveShare">
        <input type="hidden" name="command" value="saveShare"/>
        <p><fmt:message key="Edit share" bundle="${locale}"/></p>
        <table>
            <c:if test="${isThisEdition eq true}">
                <input type="hidden" name="shareId" value="${shareDTO.id}"/>
            </c:if>
            <tr>
                <th><fmt:message key="Shares's name" bundle="${locale}"/></th>
                <th><input type="text" name="shareName"
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                value="${shareDTO.shareName}"
                            </c:when>
                            <c:otherwise>placeholder="share's name"</c:otherwise>
                        </c:choose>
                />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="isLoyalty" bundle="${locale}"/></th>
                <th>
                    <input type="checkbox" name="isLoyalty"
                            <c:if test="${isThisEdition}">
                                <c:choose>
                                    <c:when test="${shareDTO.isLoyalty}"> checked="checked" </c:when>
                                </c:choose>
                            </c:if>
                    />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="isOnOff" bundle="${locale}"/></th>
                <th>
                    <input type="checkbox" name="isOnOff"
                            <c:if test="${isThisEdition}">
                                <c:choose>
                                    <c:when test="${shareDTO.isOn}"> checked="checked" </c:when>
                                </c:choose>
                            </c:if>
                    />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="bySum" bundle="${locale}"/></th>
                <th>
                    <input type="number" name="sum"
                            <c:if test="${isThisEdition}"> value="${shareDTO.sum}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="percent" bundle="${locale}"/></th>
                <th>
                    <input type="number" name="percent"
                            <c:if test="${isThisEdition}"> value="${shareDTO.percent}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <jsp:include page="errorsStack.jsp"/>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="saveShare"
                           value="<fmt:message key="saveBtn" bundle="${locale}"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
