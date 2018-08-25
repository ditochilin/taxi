<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 25.08.2018
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post" action="${pageContext.request.contextPath}/">
    <input type="hidden" name="command" value="changeLocale">
    <input type="hidden" name="from" value="${pageContext.request.requestURI}">
    <input type="hidden" name="language" value="en">
    <input type="image" src="../jsp/images/en.png" border="0" alt="English"/>
</form>

<form method="post" action="${pageContext.request.contextPath}/">
    <input type="hidden" name="command" value="changeLocale">
    <input type="hidden" name="from" value="${pageContext.request.requestURI}">
    <input type="hidden" name="language" value="ru">
    <input type="image" src="../jsp/images/ru.png" border="0" alt="Русский"/>
</form>
