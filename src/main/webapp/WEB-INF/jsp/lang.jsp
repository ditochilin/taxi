<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dmitry Tochilin
  Date: 25.08.2018
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="langList" value="${['ru_RU','en_EN']}"/>

<form action="/Controller" method="post">
    <input type="hidden" name="command" value="changeLocale">
    <input type="hidden" name="fromURI" value="${pageContext.request.requestURI}">
    <select name='selectLangs' d onchange="submit(this)">
        <c:forEach items="${langList}" var="lang">
            <option value="${lang}"  ${lang == sessionScope.locale ? 'selected' : ''}>${lang}</option>
        </c:forEach>
    </select>
</form>

<script language="JavaScript" type="text/javascript">
    function getSelectedValue(select) {
        var index = select.selectedIndex;
        if ((index >= 0) && (index < select.length)) {
            return select.options[index].value;
        }
        return '';
    }

    function submit(select) {
        if (getSelectedValue(select) != '') {
            select.form.submit();
        }
    }
</script>