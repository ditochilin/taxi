<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="taxiLib" uri="/WEB-INF/tags/requestedViewTag" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="navbar"/>

<%--<input type="hidden" name="fromURI" value="${pageContext.request.requestURI}">--%>
<%--<p><a class="button" href="${pageContext.request.contextPath}/">${lang['app.home']}</a></p>--%>

<nav>
    <div>
        <ul>
                <li>
                    <jsp:include page="/WEB-INF/jsp/lang.jsp"/>
                </li>
                <li>
                <a href="${pageContext.request.contextPath}/login">
                    <span></span><fmt:message key="login"/>
                </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/logout">
                        <span></span><fmt:message key="logout"/>
                    </a>
                </li>
        </ul>
    </div>
</nav>