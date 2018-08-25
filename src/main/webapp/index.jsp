<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locales.lang"/>

<html>
<head>
    <%--<jsp:include page="/WEB-INF/jsp/header.jsp"/>--%>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

<div>
    <div>привет
        <%--<h1><fmt:message key="title"/></h1>--%>
    </div>
</div>

<%--<div><fmt:message key="description"/></div>--%>
</body>
</html>
