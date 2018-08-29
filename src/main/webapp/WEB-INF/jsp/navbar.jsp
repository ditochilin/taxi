<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<nav>
    <ul>
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <li>
                    <c:choose>
                        <c:when test="${sessionScope.role == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/Controller?command=openAdministration">
                                <span><fmt:message key="Admin panel" bundle="${locale}"/></span>
                            </a>
                        </c:when>
                    </c:choose>
                </li>
                <li>
                    <c:choose>
                        <c:when test="${sessionScope.role == 'DRIVER' || sessionScope.role == 'ADMIN'}">
                            <a href="${pageContext.request.contextPath}/Controller?command=openListTaxis">
                                <span><fmt:message key="vehicles" bundle="${locale}"/></span>
                            </a>
                        </c:when>
                    </c:choose>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/Controller?command=openListOrders">
                        <span><fmt:message key="orders" bundle="${locale}"/></span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/Controller?command=logout">
                        <span><fmt:message key="logout" bundle="${locale}"/></span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="${pageContext.request.contextPath}/Controller?command=openRegistration">
                        <span><fmt:message key="registration" bundle="${locale}"/></span>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>

        <li style="float: right; color: green">
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <span><fmt:message key="welcome" bundle="${locale}"/><span>${sessionScope.user}</span>
                </c:when>
            </c:choose>
        </li>
    </ul>

</nav>

<jsp:include page="/WEB-INF/jsp/lang.jsp"/>
