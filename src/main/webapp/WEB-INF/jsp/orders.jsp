<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 21.08.2018
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders of clients</title>
</head>
<body>
<input type="hidden" name="command" value="showOrders"/>
<c:out value='${requestScope["user"]}, Hello! '/>
<table border="1">
    <tr>
        <th>Status</th>
        <th>Date</th>
        <th>Client</th>
        <th>Taxi</th>
        <th>Start point</th>
        <th>Destination</th>
        <th>Distance</th>
        <th>Shares</th>
        <th>Cost</th>
        <th>Feed time</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.status}" /></td>
            <td><c:out value="${order.orderDate}" /></td>
            <td><c:out value="${order.client}" /></td>
            <td><c:out value="${order.taxi}" /></td>
            <td><c:out value="${order.startPoint}" /></td>
            <td><c:out value="${order.endPoint}" /></td>
            <td><c:out value="${order.distance}" /></td>
            <td><c:out value="${order.shares}" /></td>
            <td><c:out value="${order.cost}" /></td>
            <td><c:out value="${order.feedTime}" /></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
