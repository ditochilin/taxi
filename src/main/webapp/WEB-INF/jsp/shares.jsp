<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div>
    <span><fmt:message key="List of shares" bundle="${locale}"/></span>
    <p style="color: red">
        <c:if test="${not empty resultMessage}">
            <span><fmt:message key="${resultMessage}"/></span>
        </c:if>
    </p>
    <form action="/Controller" name="addShare" method="post">
        <input type="hidden" name="command" value="editShare"/>
        <button type="submit" class="smallbutton">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>
    <table border="1">
        <tr>
            <th><fmt:message key="share" bundle="${locale}"/></th>
            <th><fmt:message key="loyalty" bundle="${locale}"/></th>
            <th><fmt:message key="isOnOff" bundle="${locale}"/></th>
            <th><fmt:message key="bySum" bundle="${locale}"/></th>
            <th><fmt:message key="percent" bundle="${locale}"/></th>
        </tr>
        <c:forEach var="share" items="${shareList}">
            <tr>
                <td><c:out value="${share.shareName}"/></td>
                <td><c:out value="${share.isLoyalty}"/></td>
                <td><c:out value="${share.isOn}"/></td>
                <td><c:out value="${share.sum}"/></td>
                <td><c:out value="${share.percent}"/></td>
                <td>
                    <form action="/Controller" name="shareEdit" method="post">
                        <input type="hidden" name="command" value="editShare"/>
                        <input type="hidden" name="shareId" value="${share.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form action="/Controller" name="shareRemove" method="post">
                        <input type="hidden" name="command" value="removeShare"/>
                        <input type="hidden" name="shareId" value="${share.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>