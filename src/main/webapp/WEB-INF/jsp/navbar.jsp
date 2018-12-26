<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>

<nav class="navbar navbar-expand-sm navbar-dark bg-info">
    <ul class="nav navbar-nav mr-auto">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <li>
                    <c:choose>
                        <c:when test="${sessionScope.role == 'ADMIN'}">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/Controller/admin?command=openAdministration">
                                <span><fmt:message key="Admin panel" bundle="${locale}"/></span>
                            </a>
                        </c:when>
                    </c:choose>
                </li>
                <li>
                    <c:choose>
                        <c:when test="${sessionScope.role == 'DRIVER' || sessionScope.role == 'ADMIN'}">
                            <a class="nav-link text-white" href="${pageContext.request.contextPath}/Controller?command=openListTaxis">
                                <span><fmt:message key="vehicles" bundle="${locale}"/></span>
                            </a>
                        </c:when>
                    </c:choose>
                </li>
                <li>
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/Controller?command=openListOrders">
                        <span><fmt:message key="List of orders" bundle="${locale}"/></span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/Controller?command=openRegistration">
                        <fmt:message key="registration" bundle="${locale}"/>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

    <ul class="nav navbar-nav">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <li class="nav-item text-white mr-2">
                    <label class="pt-2">
                        <fmt:message key="welcome" bundle="${locale}"/>${sessionScope.user}
                    </label>
                </li>
                <li class="nav-item mr-1">
                    <a class="nav-link text-white" href="${pageContext.request.contextPath}/Controller?command=logout">
                        <fmt:message key="logout" bundle="${locale}"/>
                    </a>
                </li>
            </c:when>
        </c:choose>
        <!-- Dropdown -->
        <li class="nav-item dropdown">
            <a class="dropdown-toggle" href="#" role="button" id="navbardroplocale" data-toggle="dropdown">
                <c:choose>
                    <c:when test="${empty sessionScope.locale or sessionScope.locale eq 'en_EN'}">
                        <img src="../../images/United-Kingdom-icon.png"/>
                    </c:when>
                    <c:otherwise>
                        <img src="../../images/Russia-icon.png"/>
                    </c:otherwise>
                </c:choose>
            </a>

            <div class="dropdown-menu">
                <form id="changeLocaleForm" name="changeLocaleForm" action="/Controller" method="post">
                    <input type="hidden" name="command" value="changeLocale">
                    <input type="hidden" id="selectedLang" name="selectedLang">
                    <input type="hidden" name="fromURI" value="${pageContext.request.requestURI}">
                    <a class="dropdown-item" onclick="submit('en_EN')"><img
                            src="../../images/United-Kingdom-icon.png"/> English</a>
                    <a class="dropdown-item" onclick="submit('ru_RU')"><img src="../../images/Russia-icon.png"/>
                        Russian</a>
                </form>
            </div>
        </li>
    </ul>
</nav>
