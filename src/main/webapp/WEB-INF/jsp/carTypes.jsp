<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>

<div>
    <span><fmt:message key="List of car type"/></span>
    <p style="color: red">
        <c:if test="${not empty resultMessage}">
            <span><fmt:message key="${resultMessage}"/></span>
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
            <th>Car type</th>
            <th>Price</th>
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
                            <fmt:message key="editBtn"/>
                        </button>
                    </form>
                    <form action="/Controller" name="carTypeRemove" method="post">
                        <input type="hidden" name="command" value="removeCarType"/>
                        <input type="hidden" name="carTypeId" value="${carType.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>