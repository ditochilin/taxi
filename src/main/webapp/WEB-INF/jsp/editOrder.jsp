<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <title><fmt:message key="Edit client's order"/></title>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>

    <c:set var="isThisEdition" value="${not empty orderDTO}"/>

    <form method="post" action="/Controller" name="saveOrder">
        <input type="hidden" name="command" value="saveOrder"/>
        <p><fmt:message key="Edit client's order" bundle="${locale}"/></p>
        <c:if test="${isThisEdition eq true}">
            <input type="hidden" name="orderId" value="${orderDTO.id}"/>
        </c:if>
        <table>
            <tr>
                <th><fmt:message key="status" bundle="${locale}"/></th>
                <th>
                    <select name='statusName'
                            <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                    >
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="statusItem" items="${statusList}">
                                    <c:choose>
                                        <c:when test="${statusItem eq orderDTO.status}">
                                            <option value="${orderDTO.status}"
                                                    selected>${orderDTO.status}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${statusItem}">${statusItem}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty statusList}">
                                <option selected disabled>select status</option>
                                <c:forEach var="status" items="${statusList}">
                                    <option value="${status}">${status}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option selected disabled>no status</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </th>
            </tr>

            <tr>
                <th><fmt:message key="dateTime" bundle="${locale}"/></th>
                <th>
                    <input type="text" name="dateOrder" placeholder="dd-MM-yyyy"
                            <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                            <c:if test="${isThisEdition}"> value="<fmt:formatDate value="${orderDTO.dateTime}" pattern="dd-MM-yyyy" />" </c:if>
                    />
                    <input type="text" name="timeOrder"  placeholder="HH:mm:ss"
                            <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                            <c:if test="${isThisEdition}"> value="<fmt:formatDate value="${orderDTO.dateTime}" pattern="HH:mm:ss" />" </c:if>

                    />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="client" bundle="${locale}"/></th>
                <th>
                    <select name='clientId'
                            <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                    >
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="clientItem" items="${clientList}">
                                    <c:choose>
                                        <c:when test="${clientItem eq orderDTO.client}">
                                            <option value="${orderDTO.client.id}"
                                                    selected>${orderDTO.client.userName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${clientItem.id}">${clientItem.userName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty clientList}">
                                <option selected disabled>select client</option>
                                <c:forEach var="clientItem" items="${clientList}">
                                    <option value="${clientItem.id}">${clientItem.userName}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option selected disabled>no client</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </th>
            </tr>

            <tr>
                <th><fmt:message key="carType" bundle="${locale}"/></th>
                <th>
                    <select name='carTypeId'
                            <c:if test="${sessionScope.role eq 'DRIVER'}"> hidden </c:if>
                    >
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="carTypeItem" items="${carTypeList}">
                                    <c:choose>
                                        <c:when test="${carTypeItem eq orderDTO.carType}">
                                            <option value="${orderDTO.carType.id}"
                                                    selected>${orderDTO.carType.typeName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${carTypeItem.id}">${carTypeItem.typeName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty carTypeList}">
                                <option selected disabled>select carType</option>
                                <c:forEach var="carTypeItem" items="${carTypeList}">
                                    <option value="${carTypeItem.id}">${carTypeItem.typeName}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option selected disabled>no carType</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </th>
            </tr>

            <tr>
                <th><fmt:message key="taxi" bundle="${locale}"/></th>
                <th>
                    <select name='taxiId'
                            <c:if test="${sessionScope.role eq 'CLIENT'}"> disabled</c:if>
                    >
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="taxiItem" items="${taxiList}">
                                    <c:choose>
                                        <c:when test="${taxiItem eq orderDTO.taxi}">
                                            <option value="${orderDTO.taxi.id}"
                                                    selected>${orderDTO.taxi}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${taxiItem.id}">${taxiItem}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty taxiList}">
                                <option selected disabled>select taxi</option>
                                <c:forEach var="taxi" items="${taxiList}">
                                    <option value="${taxi.id}">${taxi}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option selected disabled>no taxi</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </th>
            </tr>

            <tr>
                <th><fmt:message key="startPoint" bundle="${locale}"/></th>
                <th>
                    <input type="text" name="startPoint"
                            <c:if test="${sessionScope.role eq 'DRIVER'}"> disabled</c:if>
                            <c:if test="${isThisEdition}"> value="${orderDTO.startPoint}"</c:if>
                    />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="endPoint" bundle="${locale}"/></th>
                <th><input type="text" name="endPoint"
                        <c:if test="${sessionScope.role eq 'DRIVER'}"> disabled</c:if>
                        <c:if test="${isThisEdition}"> value="${orderDTO.endPoint}"</c:if>
                />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="distance" bundle="${locale}"/></th>
                <th><input type="number" name="distance"
                        <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                        <c:if test="${isThisEdition}"> value="${orderDTO.distance}"</c:if>
                />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="loyalty" bundle="${locale}"/></th>
                <th>
                    <select name='loyaltyId'
                            <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                    >
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="loyaltyItem" items="${loyaltyList}">
                                    <c:choose>
                                        <c:when test="${loyaltyItem eq loyalty}">
                                            <!-- todo check if work and what is sending -->
                                            <option value="${loyalty.id}"
                                                    selected>${loyalty.shareName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${loyaltyItem.id}">${loyaltyItem.shareName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty loyaltyList}">
                                <option selected disabled>select loyalty</option>
                                <c:forEach var="loyaltyItem" items="${loyaltyList}">
                                    <option value="${loyaltyItem.id}">${loyaltyItem.shareName}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option selected disabled>no loyalty</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </th>
            </tr>

            <tr>
                <th><fmt:message key="share" bundle="${locale}"/></th>
                <th>
                    <select name='shareId'
                            <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                    >
                        <c:choose>
                            <c:when test="${isThisEdition}">
                                <c:forEach var="shareItem" items="${shareList}">
                                    <c:choose>
                                        <c:when test="${shareItem eq share}">
                                            <!-- todo check if work and what is sending -->
                                            <option value="${share.id}"
                                                    selected>${share.shareName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${shareItem.id}">${shareItem.shareName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:when test="${not empty shareList}">
                                <option selected disabled>select share</option>
                                <c:forEach var="shareItem" items="${shareList}">
                                    <option value="${shareItem.id}">${shareItem.shareName}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option selected disabled>no share</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </th>
            </tr>

            <tr>
                <th><fmt:message key="discount" bundle="${locale}"/></th>
                <th><input type="number" name="discount"
                        <c:if test="${not sessionScope.role eq 'ADMIN'}"> disabled</c:if>
                        <c:if test="${isThisEdition}"> value="${orderDTO.discount}"</c:if>
                />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="cost" bundle="${locale}"/></th>
                <th><input type="number" name="cost" disabled
                        <c:if test="${isThisEdition}"> value="${orderDTO.cost}"</c:if>
                />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="feedTime" bundle="${locale}"/></th>
                <th>
                    <input type="text" name="dateFeed" placeholder="dd-MM-yyyy"
                            <c:if test="${sessionScope.role eq 'DRIVER'}"> disabled</c:if>
                            <c:if test="${isThisEdition}"> value="<fmt:formatDate value="${orderDTO.feedTime}" pattern="dd-MM-yyyy" />" </c:if>
                    />
                    <input type="text" name="timeFeed" placeholder="HH:ss"
                            <c:if test="${sessionScope.role eq 'DRIVER'}"> disabled</c:if>
                            <c:if test="${isThisEdition}"> value="<fmt:formatDate value="${orderDTO.feedTime}" pattern="HH:mm" />" </c:if>

                    />
                </th>
            </tr>

            <tr>
                <th><fmt:message key="waitingTime" bundle="${locale}"/></th>
                <th><input type="number" name="waitingTime"
                        <c:if test="${sessionScope.role eq 'CLIENT'}"> disabled</c:if>
                        <c:if test="${isThisEdition}"> value="${orderDTO.waitingTime}"</c:if>
                />
                </th>
            </tr>
            <tr>
                <jsp:include page="errorsStack.jsp"/>
            </tr>
            <tr>
                <th>
                    <input type="submit" name="saveUser"
                           value="<fmt:message key="saveBtn" bundle="${locale}"/>">
                </th>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
