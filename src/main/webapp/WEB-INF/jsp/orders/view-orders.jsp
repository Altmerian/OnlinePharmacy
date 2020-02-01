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
<div class="container mt-2">
    <!-- Messages -->
    <c:if test="${requestScope.orders == null}">
        <div class="alert alert-info">
            <fmt:message key="message.orders.null"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-info" role="alert">
            <fmt:message key="message.order.not.found"/>
            <c:set var="error_message" scope="session" value="false"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.order.deleted"/>
            <c:set var="success_message" scope="session" value="false"/>
        </div>
    </c:if>
    <!-- Orders -->
    <table class="table table-striped table-bordered">
        <thead>
            <th>
                #
            </th>
            <th>
                <fmt:message key="text.date"/>
            </th>
            <th>
                <fmt:message key="text.amount"/>
            </th>
            <th>
                <fmt:message key="text.status"/>
            </th>
            <th></th>
        </thead>
        <tbody>
        <c:forEach var="order" items="${requestScope.orders}">
            <tr>
                <td>
                    <c:out value="${order.id}"/>
                </td>
                <td>
                    <fmt:formatDate type="both" value="${order.date}"/>
                </td>
                <td>
                    <c:out value="${order.amount}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${order.status eq 'in_process'}">
                            <span class="badge badge-info"><fmt:message key="text.order.processing"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'payment_confirmation'}">
                            <span class="badge badge-primary"><fmt:message key="text.order.payment"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'paid'}">
                            <span class="badge badge-success"><fmt:message key="text.order.paid"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'completed'}">
                            <span class="badge badge-default"><fmt:message key="text.order.completed"/></span>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=view-order&id=${order.id}"
                       class="btn btn-sm btn-secondary" role="button"><fmt:message key="button.order.view"/> </a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
</body>
</html>
