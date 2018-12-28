<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div>
    <p class="text-info">
        <c:if test="${not empty resultMessage}">
            <fmt:message key="${resultMessage}" bundle="${locale}"/>
        </c:if>
    </p>

    <p class="card-header"><fmt:message key="List of shares" bundle="${locale}"/></p>

    <form action="/Controller" name="addShare" method="post">
        <input type="hidden" name="command" value="editShare"/>
        <button type="submit" class="btn btn-success btn-sm">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>
    <table class="table table-hover table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th><fmt:message key="share" bundle="${locale}"/></th>
            <th><fmt:message key="loyalty" bundle="${locale}"/></th>
            <th><fmt:message key="isOnOff" bundle="${locale}"/></th>
            <th><fmt:message key="bySum" bundle="${locale}"/></th>
            <th><fmt:message key="percent" bundle="${locale}"/></th>
        </tr>
        </thead>
        <c:forEach var="share" items="${shareList}">
            <tr>
                <td><c:out value="${share.shareName}"/></td>
                <td><c:out value="${share.isLoyalty}"/></td>
                <td><c:out value="${share.isOn}"/></td>
                <td><c:out value="${share.sum}"/></td>
                <td><c:out value="${share.percent}"/></td>
                <td>
                    <form class="d-inline-block float-left mr-3" action="/Controller" name="shareEdit" method="post">
                        <input type="hidden" name="command" value="editShare"/>
                        <input type="hidden" name="shareId" value="${share.id}"/>
                        <button type="submit" class="btn btn-warning btn-sm">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form class="d-inline-block" action="/Controller" name="shareRemove" method="post">
                        <input type="hidden" name="command" value="removeShare"/>
                        <input type="hidden" name="shareId" value="${share.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>