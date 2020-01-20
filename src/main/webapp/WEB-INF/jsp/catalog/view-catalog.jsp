<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources.local"/>

<html>
<head>
    <title><fmt:message key="title.catalog"/></title>
</head>
<body>
<ctg:header/>
<div class="container-fluid">
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger">
            <fmt:message key="message.item.add.error"/>
            <c:set var="error_message" scope="session" value="false"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success">
            <fmt:message key="message.item.add.success"/>
            <c:set var="success_message" scope="session" value="false"/>
        </div>
    </c:if>
    <c:set var="number_of_pages" value="${requestScope.number_of_items div param.limit + 1}"/>
    <div style="padding: 10px;">
        <span><fmt:message key="text.number.of.items.on.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
         value="${((param.page_number-1)* param.limit) / 5 + 1}"/>"/>
            <input type="hidden" name="limit" value="5"/>
            <input class="btn btn-default" type="submit" value="5"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
         value="${((param.page_number-1)* param.limit) / 10 + 1}"/>"/>
            <input type="hidden" name="limit" value="10"/>
            <input class="btn btn-default" type="submit" value="10"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
         value="${((param.page_number-1)* param.limit) / 20 + 1}"/>"/>
            <input type="hidden" name="limit" value="20"/>
            <input class="btn btn-default" type="submit" value="20"/>
        </form>

        <span><fmt:message key="text.go.to.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="number" max="${number_of_pages}" min="1" name="page_number"
                   placeholder="${param.page_number}/<fmt:parseNumber integerOnly="true" type="number"
                value="${number_of_pages}"/>" required/>
            <input type="hidden" name="limit" value="${param.limit}"/>
            <input class="btn btn-default" type="submit" value="<fmt:message key="text.go"/>"/>
        </form>
        <c:choose>
            <c:when test="${param.page_number != 1}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-catalog">
                    <input type="hidden" name="page_number" value="${param.page_number - 1}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <input class="btn btn-default" type="submit" value="<fmt:message key="text.previous"/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <input class="btn btn-default" type="submit" value="<fmt:message key="text.previous"/>" disabled/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${param.page_number < (number_of_pages - 1)}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-catalog">
                    <input type="hidden" name="page_number" value="${param.page_number + 1}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <input class="btn btn-default" type="submit" value="<fmt:message key="м"/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <input class="btn btn-default" type="submit" value="<fmt:message key="text.next"/>" disabled/>
            </c:otherwise>
        </c:choose>
    </div>
    <table class="table table-striped table-condensed">
        <thead>
        <tr>
            <th>
                <fmt:message key="text.label"/>
            </th>
            <th>
                <fmt:message key="text.dosage"/>
            </th>
            <th>
                <fmt:message key="text.volume"/>
            </th>
            <th>
                <fmt:message key="text.manufacturer"/>
            </th>
            <th>
                <fmt:message key="text.price"/>
            </th>
            <th>
                <fmt:message key="text.byPrescription"/>
            </th>
            <th>
                <fmt:message key="text.description"/>
            </th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.items}">
            <tr>
                <td>
                        ${item.label}
                </td>
                <td>
                        ${item.dosage}
                </td>
                <td>
                        ${item.volume} ${item.volumeType}
                </td>
                <td>
                        ${item.manufacturerName}
                </td>
                <td>
                        ${item.price}
                </td>
                <td>
                    <c:if test="${item.byPrescription}">
                        <span class="glyphicon glyphicon-ok"></span>
                    </c:if>
                </td>
                <td>
                        ${item.description}
                </td>
                <td>
                    <form class="inline" action="controller" method="post">
                        <input type="hidden" name="command" value="add_item_to_shopping_cart"/>
                        <input type="hidden" name="item_id" value="${item.id}"/>
                        <input type="hidden" name="page_number" value="${param.page_number}"/>
                        <input type="hidden" name="limit" value="${param.limit}"/>
                        <input type="number" min="1" max="200" name="quantity" value="1"/>
                        <input type="submit" class="btn btn-warning"
                               value="<fmt:message key="button.item.buy"/>"/>
                    </form>
                    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
                        <form class="inline" action="Controller" method="get">
                            <input type="hidden" name="command" value="view-edit-item"/>
                            <input type="hidden" name="id" value="${item.id}"/>
                            <input type="submit" class="btn btn-success"
                                   value="<fmt:message key="button.change"/>"/>
                        </form>

                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
