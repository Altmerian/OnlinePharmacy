<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Awesome icons -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
    integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <!-- Custom css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom.css"/>
    <title><fmt:message key="title.orders"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container">
    <c:if test="${sessionScope.access_denied}">
        <div class="alert alert-danger">
            <fmt:message key="message.access.denied"/>
            <c:set var="access_denied" value="false" scope="session"/>
        </div>
    </c:if>
    <c:if test="${requestScope.orders == null}">
        <div class="alert alert-info">
            <fmt:message key="message.orders.null"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-info">
            <fmt:message key="message.order.not.found"/>
            <c:set var="error_message" scope="session" value="false"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success">
            <fmt:message key="message.order.submitted"/>
            <c:set var="success_message" scope="session" value="false"/>
        </div>
    </c:if>
    <div style="padding-bottom: 10px">
        <c:choose>
            <c:when test="${param.is_canceled eq false}">
                <form role="form" class="inline" action="controller" method="get">
                    <input type="hidden" name="command" value="view-orders"/>
                    <input type="hidden" name="user_id" value="${param.user_id}"/>
                    <input type="hidden" name="is_canceled" value="true"/>
                    <button type="submit" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>
                        <fmt:message key="button.orders.show.canceled"/></button>
                </form>
            </c:when>
            <c:otherwise>
                <form role="form" class="inline" action="controller" method="get">
                    <input type="hidden" name="command" value="view-orders"/>
                    <input type="hidden" name="user_id" value="${param.user_id}"/>
                    <input type="hidden" name="is_canceled" value="false"/>
                    <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span>
                        <fmt:message key="button.orders.show.all"/></button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    <table class="table table-striped table-bordered">
        <thead>
        <th class="col-sm-1">
            #
        </th>
        <th class="col-sm-3">
            <fmt:message key="text.date"/>
        </th>
        <th class="col-sm-3">
            <fmt:message key="text.amount"/>
        </th>
        <th class="col-sm-3">
            <fmt:message key="text.status"/>
        </th>
        <th class="col-sm-2"></th>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr <c:if test="${order.canceled}">class="danger"</c:if>>
                <td class="col-sm-1">
                    <c:out value="${order.id}"/>
                </td>
                <td class="col-sm-3">
                    <fmt:formatDate type="both" value="${order.date}"/>
                </td>
                <td class="col-sm-3">
                    <c:out value="${order.amount}"/>
                </td>
                <td class="col-sm-3">
                    <c:if test="${order.canceled}">
                        <span class="label label-danger"><fmt:message key="text.order.canceled"/></span>
                    </c:if>
                    <c:choose>
                        <c:when test="${order.status eq 'в работе'}">
                            <span class="label label-info"><fmt:message key="text.order.processing"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'к доставке'}">
                            <span class="label label-primary"><fmt:message key="text.order.shipping"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'выполнен'}">
                            <span class="label label-success"><fmt:message key="text.order.completed"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'открыт'}">
                            <span class="label label-default"><fmt:message key="link.shopping.cart"/></span>
                        </c:when>
                    </c:choose>
                </td>
                <td class="col-sm-2">
                    <a href="${pageContext.request.contextPath}/controller?command=view-order&id=${order.id}"
                       class="btn btn-info" role="button"><fmt:message key="button.order.view"/> </a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</body>
</html>
