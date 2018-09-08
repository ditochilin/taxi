<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>

<nav>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/Controller/admin?command=openListUsers">
                <span><fmt:message key="users" bundle="${locale}"/></span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/Controller?command=openListShares">
                <span><fmt:message key="shares" bundle="${locale}"/></span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/Controller?command=openListCarTypes">
                <span><fmt:message key="carTypes" bundle="${locale}"/></span>
            </a>
        </li>
    </ul>
</nav>