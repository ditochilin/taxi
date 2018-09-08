<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 01.09.2018
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="editTaxi" bundle="${locale}"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

    <c:set var="isThisEdition" value="${not empty taxiDTO}"/>

    <p><fmt:message key="editTaxi" bundle="${locale}"/></p>
    <form method="post" action="/Controller" name="saveTaxi">
        <input type="hidden" name="command" value="saveTaxi"/>
        <c:if test="${isThisEdition eq true}">
            <input type="hidden" name="taxiId" value="${taxiDTO.id}"/>
        </c:if>
        <table>
            <tr>
                <th><fmt:message key="driverUser" bundle="${locale}"/></th>
                <th>
                    <select name='driverId'>
                        <option selected value="${null}">empty driver</option>
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="driverItem" items="${driverList}">
                                    <c:choose>
                                        <c:when test="${driverItem eq taxiDTO.driver}">
                                            <option value="${taxiDTO.driver.id}"
                                                    selected>${taxiDTO.driver.userName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${driverItem.id}">${driverItem.userName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty driverList}">
                                <c:forEach var="driverItem" items="${driverList}">
                                    <option value="${driverItem.id}">${driverItem.userName}</option>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </select>
                </th>
            </tr>
            </tr>
            <tr>
                <th><fmt:message key="carType" bundle="${locale}"/></th>
                <th>
                    <select name='carTypeId'>
                        <option selected value="${null}">empty car type</option>
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="carTypeItem" items="${carTypeList}">
                                    <c:choose>
                                        <c:when test="${carTypeItem eq taxiDTO.carType}">
                                            <option value="${taxiDTO.carType.id}"
                                                    selected>${taxiDTO.carType.typeName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${carTypeItem.id}">${carTypeItem.typeName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty carTypeList}">
                                <c:forEach var="carTypeItem" items="${carTypeList}">
                                    <option value="${carTypeItem.id}">${carTypeItem.typeName}</option>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </select>
                </th>
            </tr>
            <tr>
                <th><fmt:message key="carName" bundle="${locale}"/></th>
                <th>
                    <input type="text" name="carName"
                            <c:if test="${isThisEdition}"> value="${taxiDTO.carName}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="carNumber" bundle="${locale}"/></th>
                <th>
                    <input type="text" name="carNumber"
                            <c:if test="${isThisEdition}"> value="${taxiDTO.carNumber}"</c:if>
                    />
                </th>
            </tr>
            <tr>
                <th><fmt:message key="busy" bundle="${locale}"/></th>
                <th>
                    <input type="checkbox" name="busy" disabled
                            <c:if test="${isThisEdition}">
                                <c:choose>
                                    <c:when test="${taxiDTO.busy}"> checked="checked" </c:when>
                                </c:choose>
                            </c:if>
                    />
                </th>
            </tr>
            <tr>
                <jsp:include page="errorsStack.jsp"/>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="saveTaxi"
                           value="<fmt:message key="saveBtn" bundle="${locale}"/>"
                    />

                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
