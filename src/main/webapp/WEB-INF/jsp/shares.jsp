<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<div>
    <span><fmt:message key="List of shares"/></span>
    <table border="1">
        <tr>
            <th>Share</th>
            <th>Loyalty</th>
            <th>On/Off</th>
            <th>Sum</th>
            <th>Percent</th>
        </tr>
        <c:forEach var="share" items="${shareList}">
            <tr>
                <td><c:out value="${share.shareName}"/></td>
                <td><c:out value="${share.isLoyalty}"/></td>
                <td><c:out value="${share.isOn}"/></td>
                <td><c:out value="${share.sum}"/></td>
                <td><c:out value="${share.percent}"/></td>
                <td>
                    <form name="shareEdit" method="post">
                        <input type="hidden" name="command" value="editShare"/>
                        <input type="hidden" name="shareId" value="${share.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="editBtn"/>
                        </button>
                    </form>
                    <form name="shareRemove" method="post">
                        <input type="hidden" name="command" value="removeShare"/>
                        <input type="hidden" name="shareId" value="${share.id}"/>
                        <button type="submit" class="smallbutton">
                            <fmt:message key="removeBtn"/>
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>