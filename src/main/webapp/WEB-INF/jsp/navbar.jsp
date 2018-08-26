<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>

<nav>
    <ul>
        <li>
            <c:choose>
                <c:when test="${sessionScope.user != null}"><span/><fmt:message
                        key="welcome"/><span>${sessionScope.user}</span></c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </li>
        <li>
            <c:choose>
                <c:when test="${sessionScope.user != null}">
                    <%--<input type="hidden" name="command" value="logout">--%>
                    <a href="${pageContext.request.contextPath}/Controller?command=logout">
                        <span></span><fmt:message key="logout"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <%--<input type="hidden" name="command" value="login">--%>
                    <a href="${pageContext.request.contextPath}/login?command=login">
                        <span></span><fmt:message key="enter"/>
                    </a>
                    <%--<input type="hidden" name="command" value="registration">--%>
                    <a href="${pageContext.request.contextPath}/Controller?command=registration">
                        <span></span><fmt:message key="registration"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </li>
    </ul>

</nav>

<jsp:include page="/WEB-INF/jsp/lang.jsp"/>
