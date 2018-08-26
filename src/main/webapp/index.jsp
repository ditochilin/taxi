<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<fmt:setLocale value="${sessionScope.locale}"/>--%>
<%--<fmt:setBundle basename="locales.lang"/>--%>
<%--<fmt:setLocale value="${sessionScope.locale}"/>--%>
<%--<fmt:setBundle basename="navbar"/>--%>

<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

<div>
    <div>
        <fmt:message key="welcome" />
        <%--<h1><fmt:message key="title"/></h1>--%>
    </div>
    <fmt:message key="hello"/><button class="btn">hello</button>
</div>

<%--<div><fmt:message key="description"/></div>--%>
</body>
</html>
