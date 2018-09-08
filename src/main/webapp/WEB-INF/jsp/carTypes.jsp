<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${not empty sessionScope.locale ? sessionScope.locale : 'en_EN'}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div>
    <span><fmt:message key="List of car type" bundle="${locale}"/></span>
    <p style="color: red">
        <c:if test="${not empty resultMessage}">
            <span><fmt:message key="${resultMessage}" bundle="${locale}"/></span>
        </c:if>
    </p>
    <form action="/Controller" name="addCarType" method="post">
        <input type="hidden" name="command" value="editCarType"/>
        <button type="submit" class="smallbutton">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>

    <table border="1">
        <tr>
            <th><fmt:message key="carType" bundle="${locale}"/></th>
            <th><fmt:message key="price" bundle="${locale}"/></th>
        </tr>
        <c:forEach var="carType" items="${carTypeList}">
            <tr>
                <td><c:out value="${carType.typeName}"/></td>
                <td><c:out value="${carType.price}"/></td>
                <td>
                    <form action="/Controller" name="carTypeEdit" method="post">
                        <input type="hidden" name="command" value="editCarType"/>
                        <input type="hidden" name="carTypeId" value="${carType.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form action="/Controller" name="carTypeRemove" method="post">
                        <input type="hidden" name="command" value="removeCarType"/>
                        <input type="hidden" name="carTypeId" value="${carType.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>