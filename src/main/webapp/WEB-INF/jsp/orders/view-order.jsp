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
<c:set var="count" value="0" scope="page"/>
<c:set var="total_quantity" value="0" scope="page"/>
<c:set var="total_amount" value="0" scope="page"/>
<div class="container">
    <!-- Messages -->
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.order.status.not.changed"/>
        </div>
        <c:set var="error_message" scope="session" value="false"/>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.order.status.changed"/>
        </div>
        <c:set var="success_message" scope="session" value="false"/>
    </c:if>
    <!-- Order -->
    <div>
        <a href="${header.Referer}"><i class="fas fa-arrow-left"></i><fmt:message key="link.back"/></a>
    </div>
    <div class="container col-sm-6 text-center mt-1">
        <h4><fmt:message key="title.order"/> <c:out value="#${requestScope.order.id}"/></h4>
        <h5><fmt:message key="title.customer"/>: 
            #<c:out value="${order_owner.id} ${order_owner.firstName} ${order_owner.lastName} (${order_owner.login})"/></h5>
    </div>
    <div class="row">
        <c:forEach var="entry" items="${order_events}">
            <div class="col">
                <c:choose>
                    <c:when test="${entry.value eq 'in_process'}">
                        <span class="badge badge-info"><fmt:message key="text.order.in_process"/></span>
                        <i class="fas fa-long-arrow-alt-right"></i>
                    </c:when>
                    <c:when test="${entry.value eq 'payment_confirmation'}">
                        <span class="badge badge-primary"><fmt:message key="text.order.payment_confirmation"/></span>
                        <i class="fas fa-long-arrow-alt-right"></i>
                    </c:when>
                    <c:when test="${entry.value eq 'paid'}">
                        <span class="badge badge-success"><fmt:message key="text.order.paid"/></span>
                        <i class="fas fa-long-arrow-alt-right"></i>
                    </c:when>
                    <c:when test="${entry.value eq 'completed'}">
                        <span class="badge badge-default"><fmt:message key="text.order.completed"/></span>
                    </c:when>
                </c:choose>
                <h5><fmt:formatDate type="both" value="${entry.key}"/></h5>
            </div>
        </c:forEach>
    </div>

    <h4><fmt:message key="text.amount"/>:<b> <c:out value="${requestScope.order.amount}"/></b></h4>
    <table class="table table-striped">
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
                <fmt:message key="text.byPrescription"/>
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
                        <b><c:out value="${entry.key.label}"/></b> 
                        <c:out value="${entry.key.dosage}, ${entry.key.volume} ${entry.key.volumeType}"/></a>
                </td>
                <td>
                    <c:out value="${entry.value}"/>
                </td>
                <td>
                    <c:out value="${entry.key.price}"/>
                </td>
                <td>
                    <c:if test="${entry.key.byPrescription}">
                        <i class="fas fa-check"></i>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <c:if test="${requestScope.order.status eq 'payment_confirmation' and 
        (sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER')}">
        <div class="col">
            <form class="col-sm-6" action="controller" method="post">
                <input type="hidden" name="command" value="confirm-payment"/>
                <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                <input type="submit" style="margin: 10px" class="btn btn-primary"
                       value="<fmt:message key="button.order.confirm.payment"/> "/>
            </form>
        </div>
        </c:if>
        <c:if test="${requestScope.order.status eq 'paid' and user.login eq order_owner.login}">
        <div class="col">
            <form class="col-sm-6" action="controller" method="post">
                <input type="hidden" name="command" value="confirm-delivery"/>
                <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                <input type="submit" style="margin: 10px" class="btn btn-primary"
                       value="<fmt:message key="button.order.confirm.delivery"/> "/>
            </form>
        </div>
        </c:if>
        <c:if test="${requestScope.order.status eq 'in_process' and user.login eq order_owner.login}">
        <div class="col">
            <form class="col-sm-6" action="controller" method="post">
                <input type="hidden" name="command" value="pay-order"/>
                <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                <input type="submit" style="margin: 10px" class="btn btn-success"
                       value="<fmt:message key="button.order.pay"/> "/>
            </form>
        </div>
        </c:if>
        <c:if test="${order.status != 'completed' and ((sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER') or requestScope.order.status eq 'in_process')}">
        <div class="col">
            <form class="col-sm-6" action="controller" method="post">
                <input type="hidden" name="command" value="cancel_order"/>
                <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                <input type="submit" style="margin: 10px" class="btn btn-danger"
                       value="<fmt:message key="button.order.cancel"/> "/>
            </form>
        </div>
        </c:if>
    </div>
</div>
</body>
</html>