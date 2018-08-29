<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
    <span><fmt:message key="List of car type"/></span>
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
                    <form name="carTypeEdit" method="post">
                        <input type="hidden" name="command" value="editCarType"/>
                        <input type="hidden" name="carTypeId" value="${carType.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn"/>
                        </button>
                    </form>
                    <form name="carTypeRemove" method="post">
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