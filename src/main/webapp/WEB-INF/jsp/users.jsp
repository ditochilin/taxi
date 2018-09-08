<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div>
    <p style="color: red">
        <c:if test="${not empty resultMessage}">
            <span><fmt:message key="${resultMessage}"/></span>
        </c:if>
    </p>

    <span><fmt:message key="List of users" bundle="${locale}"/></span>
    <form action="/Controller/admin" name="addUser" method="post">
        <input type="hidden" name="command" value="editUser"/>
        <button type="submit" class="smallbutton">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>
    <table border="1">
        <tr>
            <th><fmt:message key="User's name" bundle="${locale}"/></th>
            <th><fmt:message key="Role" bundle="${locale}"/></th>
            <th><fmt:message key="phone" bundle="${locale}"/></th>
            <th><fmt:message key="password" bundle="${locale}"/></th>
        </tr>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td><c:out value="${user.userName}"/></td>
                <td><c:out value="${user.role.roleName}"/></td>
                <td><c:out value="${user.phone}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td>
                    <form action="/Controller/admin" name="userEdit" method="post">
                        <input type="hidden" name="command" value="editUser"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form action="/Controller/admin" name="userRemove" method="post">
                        <input type="hidden" name="command" value="removeUser"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>