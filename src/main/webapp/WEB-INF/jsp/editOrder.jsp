<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 21.08.2018
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="makeOrder"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <input type="hidden" name="command" value="editOrder"/>
    <form method="post" action="Controller" name="editOrder">
        <table>
            <p><fmt:message key="Edit client's order"/></p>
            <tr>
                <th>Status</th>
                <th><select name='statusList'>
                    <c:forEach items="${statusList}" var="status">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
                </th>
            <tr>
            <tr>
                <th>Date</th>
                <th><input type="datetime-local" name="date"></th>
            </tr>
            <tr>
                <th>Client</th>
                <th>
                <th><select name='clientList'>
                    <c:forEach items="${clientList}" var="client">
                        <option value="${client}">${client}</option>
                    </c:forEach>
                </select></th>
            </tr>
            <tr>
                <th>Taxi</th>
                <th><select name='taxiList'>
                    <c:forEach items="${taxiList}" var="taxi">
                        <option value="${taxi}">${taxi}</option>
                    </c:forEach>
                </select></th>
            </tr>
            <tr>
                <th>Start point</th>
                <th><input type="text" name="startPoint"></th>
            </tr>
            <tr>
                <th>Destination</th>
                <th><input type="text" name="endPoint"></th>
            </tr>
            <tr>
                <th>Distance</th>
                <th><input type="number" name="distance"></th>
            </tr>
            <tr>
                <th>Shares</th>
                <!--todo  multiple choice can be added-->
                <th><input type="text" name="share"></th>
                <th><input type="text" name="share"></th>
                <th><input type="text" name="share"></th>
            </tr>
            <tr>
                <th>Cost</th>
                <th><input type="number" name="cost"></th>
            </tr>
            <tr>
                <th>Feed time</th>
                <th><input type="feedTime" name="feedTime"></th>
            </tr>
            <tr>
                <th>Waiting Time</th>
                <th><input type="number" name="waitingTime"></th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
