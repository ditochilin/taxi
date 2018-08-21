<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 21.08.2018
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<input type="hidden" name="command" value="editOrder"/>
<table>
    <h3>Edit client's order</h3>
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
        <th><input type="text" name="client"></th>
    </tr>
    <tr>
        <th>Taxi</th>
        <th><input type="text" name="taxi"></th>
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
        <th><input type="cost" name="cost"></th>
    </tr>
    <tr>
        <th>Feed time</th>
        <th><input type="feedTime" name="feedTime"></th>
    </tr>
</table>
</body>
</html>
