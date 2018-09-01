<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 01.09.2018
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<c:if test="${not empty requestScope.errors}">
    <p style="color: red">
    <c:forEach items="${requestScope.errors}" var="error">
        <strong><fmt:message key="error" bundle="${locale}"/></strong> <fmt:message key="${error}"
                                                                                    bundle="${locale}"/><br>
    </c:forEach>
    </p>
</c:if>