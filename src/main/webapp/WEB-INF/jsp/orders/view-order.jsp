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
    <div class="container col-sm-6 text-center mt-1">
        <h4><fmt:message key="title.order"/> #${requestScope.order.id}</h4>
    </div>
    <c:choose>
        <c:when test="${requestScope.order.status eq 'in_process'}">
            <span class="badge badge-info"><fmt:message key="text.order.processing"/></span>
        </c:when>
        <c:when test="${requestScope.order.status eq 'payment_confirmation'}">
            <span class="badge badge-primary"><fmt:message key="text.order.payment"/></span>
        </c:when>
        <c:when test="${requestScope.order.status eq 'paid'}">
            <span class="badge badge-success"><fmt:message key="text.order.paid"/></span>
        </c:when>
        <c:when test="${requestScope.order.status eq 'completed'}">
            <span class="badge badge-default"><fmt:message key="text.order.completed"/></span>
        </c:when>
    </c:choose>

    <h4><fmt:message key="text.date"/>: <fmt:formatDate type="both" value="${requestScope.order.date}"/></h4>
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
                        <i class="fas fa-check"></i>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
        <div class="col">
            <form class="form-inline" role="form" action="controller" method="post">
                <div class="input-group">
                    <input type="hidden" name="command" value="change_order_status"/>
                    <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                    <div class="form-group">
                        <select class="selectpicker form-control" id="sel" name="status">
                            <c:forEach var="status" items="${sessionScope.status_list}">
                                <c:if test="${status ne order.status}">
                                    <option value="${status}">${status}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="submit" style="margin: 10px"
                               class="btn btn-warning"
                               value="<fmt:message key="button.order.change.status"/> "/>
                    </div>
                </div>
            </form>
        </div>
        </c:if>
        <c:if test="${requestScope.order.status eq 'in_process'}">
        <div class="col">
            <form class="col-sm-6" action="controller" method="post">
                <input type="hidden" name="command" value="pay-order"/>
                <input type="hidden" name="order_id" value="${requestScope.order.id}"/>
                <input type="submit" style="margin: 10px" class="btn btn-success"
                       value="<fmt:message key="button.order.pay"/> "/>
            </form>
        </div>
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