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
    <title><fmt:message key="title.order"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container col-sm-6 text-center mt-1">
    <h4><fmt:message key="title.order"/> #${requestScope.order.id}</h4>
</div>
<c:set var="count" value="0" scope="page"/>
<c:set var="total_quantity" value="0" scope="page"/>
<c:set var="total_amount" value="0" scope="page"/>
<div class="container">
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger">
            <fmt:message key="message.order.status.not.changed"/>
            <c:set var="error_message" scope="session" value="false"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success">
            <fmt:message key="local.message.order.status.changed"/>
            <c:set var="success_message" scope="session" value="false"/>
        </div>
    </c:if>
    <h3><fmt:message key="text.order"/> #<c:out value="${requestScope.order.id}"/></h3>
    <h4><fmt:message key="text.status"/>: <c:if test="${requestScope.order.canceled}"><span
            class="label label-danger"><fmt:message key="text.order.canceled"/></span>
    </c:if>
        <c:choose>
            <c:when test="${requestScope.order.status eq 'в работе'}">
                <span class="label label-info"><fmt:message key="text.order.processing"/></span>
            </c:when>
            <c:when test="${requestScope.order.status eq 'к доставке'}">
                <span class="label label-primary"><fmt:message key="text.order.shipping"/></span>
            </c:when>
            <c:when test="${requestScope.order.status eq 'выполнен'}">
                <span class="label label-success"><fmt:message key="text.order.completed"/></span>
            </c:when>
            <c:when test="${requestScope.order.status eq 'открыт'}">
                <span class="label label-default"><fmt:message key="link.shopping.cart"/></span>
            </c:when>
        </c:choose>

    </h4>
    <h4><fmt:message key="local.text.date"/>: <fmt:formatDate type="both" value="${requestScope.order.date}"/></h4>
    <h4><fmt:message key="local.text.amount"/>:<b> <c:out value="${requestScope.order.amount}"/></b></h4>
    <table class="table">
        <thead>
        <tr>
            <th>
                #
            </th>
            <th>
                <fmt:message key="text.drug"/>
            </th>
            <th>
                <fmt:message key="text.quantity"/>
            </th>
            <th>
                <fmt:message key="text.price"/>
            </th>
            <th>
                <fmt:message key="text.byprescription"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${requestScope.order.items}">
        <c:set var="count" value="${count+1}"/>
        <tr>
            <td>
                <c:out value="${count}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?command=view-item&id=${entry.key.id}">
                    <b>${entry.key.label}</b> ${entry.key.dosage}, ${entry.key.volume} ${entry.key.volumeType}
                </a>
            </td>
            <td>
                <c:out value="${entry.value}"/>
            </td>
            <td>
                <c:out value="${entry.key.price}"/>
            </td>
            <td>
                <c:if test="${entry.key.byPrescription}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
            </td>
            </c:forEach>
        </tr>
        </tbody>
    </table>
    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
        <form class="form-inline inline" role="form" action="controller" method="post">
            <div class="input-group">
                <input type="hidden" name="command" value="change-order-status"/>
                <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                <div class="form-group">
                    <select class="selectpicker form-control" id="sel" name="status">
                        <c:forEach var="status" items="${sessionScope.status_list}">
                            <c:if test="${status ne requestScope.order.status}">
                                <option value="${status}">${status}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <input type="submit" style="margin: 10px"
                           class="btn btn-warning"
                           value="<fmt:message key="local.button.order.change.status"/> "/>
                </div>

            </div>
        </form>
    </c:if>
    <c:if test="${(requestScope.order.status eq 'в работе' or requestScope.order.status eq 'к доставке') and not requestScope.order.canceled}">
        <form class="inline" action="controller" method="post">
            <input type="hidden" name="command" value="cancel-order"/>
            <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
            <input type="hidden" name="is_canceled" value="true"/>
            <input type="submit" style="margin: 10px" class="btn btn-danger"
                   value="<fmt:message key="local.button.order.cancel"/> "/>
        </form>
    </c:if>
    <c:if test="${requestScope.order.canceled}">
        <form class="inline" action="controller" method="post">
            <input type="hidden" name="command" value="cancel-order"/>
            <input type="hidden" name="is_canceled" value="false"/>
            <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
            <input type="submit" style="margin: 10px" class="btn btn-success"
                   value="<fmt:message key="local.button.order.restore"/> "/>
        </form>
    </c:if>
</div>
</body>
</html>