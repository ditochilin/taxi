<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>

<nav class="navbar tx-navbar navbar-expand-sm">
    <ul class="nav navbar-nav mr-auto">
        <li>
            <a class="nav-link text-white tx-navbar-link" href="${pageContext.request.contextPath}/Controller/admin?command=openListUsers">
                <fmt:message key="users" bundle="${locale}"/>
            </a>
        </li>
        <li>
            <a class="nav-link text-white tx-navbar-link" href="${pageContext.request.contextPath}/Controller?command=openListShares">
                <fmt:message key="shares" bundle="${locale}"/>
            </a>
        </li>
        <li>
            <a class="nav-link text-white tx-navbar-link" href="${pageContext.request.contextPath}/Controller?command=openListCarTypes">
                <fmt:message key="carTypes" bundle="${locale}"/>
            </a>
        </li>
    </ul>
</nav>