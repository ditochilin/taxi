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

    <p class="card-header"><fmt:message key="List of users" bundle="${locale}"/></p>

    <form action="/Controller/admin" name="addUser" method="post">
        <input type="hidden" name="command" value="editUser"/>
        <button type="submit" class="btn btn-success btn-sm">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>
    <table class="table table-hover table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th><fmt:message key="User's name" bundle="${locale}"/></th>
            <th><fmt:message key="Role" bundle="${locale}"/></th>
            <th><fmt:message key="phone" bundle="${locale}"/></th>
            <th><fmt:message key="password" bundle="${locale}"/></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td><c:out value="${user.userName}"/></td>
                <td><c:out value="${user.role.roleName}"/></td>
                <td><c:out value="${user.phone}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td>
                    <form class="d-inline-block float-left mr-3" action="/Controller/admin" name="userEdit"
                          method="post">
                        <input type="hidden" name="command" value="editUser"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit" class="btn btn-warning btn-sm">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form class="d-inline-block" action="/Controller/admin" name="userRemove" method="post">
                        <input type="hidden" name="command" value="removeUser"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>