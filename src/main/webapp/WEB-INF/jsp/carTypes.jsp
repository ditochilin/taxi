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

    <p class="card-header"><fmt:message key="List of car type" bundle="${locale}"/></p>

    <form action="/Controller" name="addCarType" method="post">
        <input type="hidden" name="command" value="editCarType"/>
        <button type="submit" class="btn btn-success btn-sm">
            <fmt:message key="addBtn" bundle="${locale}"/>
        </button>
    </form>

    <table class="table table-hover table-bordered table-sm">
        <thead class="thead-light">
        <tr>
            <th><fmt:message key="carType" bundle="${locale}"/></th>
            <th><fmt:message key="price" bundle="${locale}"/></th>
        </tr>
        </thead>
        <c:forEach var="carType" items="${carTypeList}">
            <tr>
                <td><c:out value="${carType.typeName}"/></td>
                <td><c:out value="${carType.price}"/></td>
                <td>
                    <form class="d-inline-block float-left mr-3" action="/Controller" name="carTypeEdit" method="post">
                        <input type="hidden" name="command" value="editCarType"/>
                        <input type="hidden" name="carTypeId" value="${carType.id}"/>
                        <button type="submit" class="btn btn-warning btn-sm">
                            <fmt:message key="editBtn" bundle="${locale}"/>
                        </button>
                    </form>
                    <form class="d-inline-block" action="/Controller" name="carTypeRemove" method="post">
                        <input type="hidden" name="command" value="removeCarType"/>
                        <input type="hidden" name="carTypeId" value="${carType.id}"/>
                        <button type="submit" class="btn btn-danger btn-sm">
                            <fmt:message key="removeBtn" bundle="${locale}"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>