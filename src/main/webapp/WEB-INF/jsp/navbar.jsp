<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="taxiLib" uri="/WEB-INF/tags/requestedViewTag" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}'/>
<fmt:setBundle basename="i18n.lang"/>

<c:set var="currView" scope="page">
    <taxiLib:viewUri/>
</c:set>
<input type="hidden" name="fromURI" value="${pageContext.request.requestURI}">
<jsp:include page="/WEB-INF/jsp/lang.jsp"/>
<p><a class="button" href="${pageContext.request.contextPath}/">${lang['app.home']}</a></p>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${empty sessionScope.user}">
                <c:choose>
                    <c:when test="${signUpView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/site/signup">
                    <span class="glyphicon glyphicon-user"></span><fmt:message key="signup"/>
                </a>
                </li>
                <c:choose>
                    <c:when test="${loginView.equals(currView)}">
                        <li class="active">
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/site/login">
                    <span class="glyphicon glyphicon-log-in"></span><fmt:message key="login"/>
                </a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <li>
                    <a href="#">
                        <fmt:message key="welcome"/><c:out value="${sessionScope.user.getFirstName()}"/>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/site/logout">
                        <span class="glyphicon glyphicon-log-out"></span><fmt:message key="logout"/>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>