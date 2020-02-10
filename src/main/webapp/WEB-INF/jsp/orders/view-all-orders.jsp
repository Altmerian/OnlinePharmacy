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
<div class="container-fluid">
    <!-- Messages -->
    <c:if test="${requestScope.orders == null}">
        <div class="alert alert-info">
            <fmt:message key="message.orders.null"/>
        </div>
    </c:if>
    <!-- Filter -->
    <div class="container mt-2">
        <form class="inline" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-orders">
            <span class="mr-2"><fmt:message key="text.filter.statuses"/>: </span>
            <div class="form-check-inline">
                <label for="in_process" class="form-check-label" style="color:#17a2b8">
                    <input name="in_process" value="true" type="checkbox" id="in_process" class="badgebox" 
                    <c:if test="${param.in_process}">checked</c:if>><b> <fmt:message key="text.order.in_process"/></b></label>
            </div>
            <div class="form-check-inline">
                <label for="payment_confirmation" class="form-check-label" style="color:#007bff">
                    <input name="payment_confirmation" value="true" type="checkbox" id="payment_confirmation" class="badgebox" <c:if test="${param.payment_confirmation}">checked</c:if>><b> <fmt:message key="text.order.payment_confirmation"/></b></label>
            </div>
            <div class="form-check-inline">
                <label for="paid" class="form-check-label" style="color:#28a745">
                    <input name="paid" value="true" type="checkbox" id="paid" class="badgebox" <c:if test="${param.paid}">checked</c:if>>
                    <b> <fmt:message key="text.order.paid"/></b></label>
            </div>
            <div class="form-check-inline">  
                <label for="completed" class="form-check-label">
                    <input name="completed" value="true" type="checkbox" id="completed" class="badgebox" <c:if test="${param.completed}">checked</c:if>><b> <fmt:message key="text.order.completed"/></b></label>
            </div>
                <input type="hidden" name="page_number" value="1"/>
                <input type="hidden" name="limit" value="${param.limit}"/>
            <div class="form-check-inline">
                <button type="submit" class="btn btn-warning"><fmt:message key="button.filter"/></button>
           </div>
        </form>
    </div>
    <!-- Pagination -->
    <c:choose>
        <c:when test="${requestScope.number_of_orders == param.limit}">
            <c:set var="number_of_pages" value="1"/>
        </c:when>
        <c:otherwise>
            <c:set var="number_of_pages" value="${requestScope.number_of_orders div param.limit + 1}"/>
        </c:otherwise>
    </c:choose>
    <div style="padding: 10px;">
        <span><fmt:message key="text.number.of.orders.on.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-orders">
            <input type="hidden" name="in_process" value="${param.in_process}"/>
            <input type="hidden" name="payment_confirmation" value="${param.payment_confirmation}"/>
            <input type="hidden" name="paid" value="${param.paid}"/>
            <input type="hidden" name="completed" value="${param.completed}"/>
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
         value="${((param.page_number-1)* param.limit) / 5 + 1}"/>"/>
            <input type="hidden" name="limit" value="5"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="5"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-orders">
            <input type="hidden" name="in_process" value="${param.in_process}"/>
            <input type="hidden" name="payment_confirmation" value="${param.payment_confirmation}"/>
            <input type="hidden" name="paid" value="${param.paid}"/>
            <input type="hidden" name="completed" value="${param.completed}"/>
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" value="${((param.page_number-1)* param.limit) / 10 + 1}"/>"/>
            <input type="hidden" name="limit" value="10"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="10"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-orders">
            <input type="hidden" name="in_process" value="${param.in_process}"/>
            <input type="hidden" name="payment_confirmation" value="${param.payment_confirmation}"/>
            <input type="hidden" name="paid" value="${param.paid}"/>
            <input type="hidden" name="completed" value="${param.completed}"/>
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" value="${((param.page_number-1)* param.limit) / 20 + 1}"/>"/>
            <input type="hidden" name="limit" value="20"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="20"/>
        </form>
        <span><fmt:message key="text.goTo.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-orders">
            <input type="hidden" name="in_process" value="${param.in_process}"/>
            <input type="hidden" name="payment_confirmation" value="${param.payment_confirmation}"/>
            <input type="hidden" name="paid" value="${param.paid}"/>
            <input type="hidden" name="completed" value="${param.completed}"/>
            <input type="number" max="${number_of_pages}" min="1" name="page_number"
                   placeholder="${param.page_number}/<fmt:parseNumber integerOnly="true" type="number"
                value="${number_of_pages}"/>" required/>
            <input type="hidden" name="limit" value="${param.limit}"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.go"/>"/>
        </form>
        <c:choose>
            <c:when test="${param.page_number != 1}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-all-orders">
                    <input type="hidden" name="in_process" value="${param.in_process}"/>
                    <input type="hidden" name="payment_confirmation" value="${param.payment_confirmation}"/>
                    <input type="hidden" name="paid" value="${param.paid}"/>
                    <input type="hidden" name="completed" value="${param.completed}"/>
                    <input type="hidden" name="page_number" value="${param.page_number - 1}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.previous"/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.previous"/>" disabled/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${param.page_number < (number_of_pages - 1)}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-all-orders">
                    <input type="hidden" name="in_process" value="${param.in_process}"/>
                    <input type="hidden" name="payment_confirmation" value="${param.payment_confirmation}"/>
                    <input type="hidden" name="paid" value="${param.paid}"/>
                    <input type="hidden" name="completed" value="${param.completed}"/>
                    <input type="hidden" name="page_number" value="${param.page_number + 1}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.next"/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.next"/>" disabled/>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- Table -->
    <table class="table table-striped">
        <thead>
            <th>
                #
            </th>
            <th>
                <fmt:message key="text.customer"/>
            </th>
            <th>
                <fmt:message key="text.address"/>
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
                    <a href="${pageContext.request.contextPath}/controller?command=view-user&user_id=${order.user.id}"
                       style="display: block">
                        <div style="width: 100%; height: 100%">
                            <c:out value="#${order.user.id} ${order.user.firstName} ${order.user.lastName} (${order.user.login})"/>
                        </div>
                    </a>
                </td>
                <td>
                    <c:out value="${order.user.address}"/>
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
                            <span class="badge badge-info"><fmt:message key="text.order.in_process"/></span>
                        </c:when>
                        <c:when test="${order.status eq 'payment_confirmation'}">
                            <span class="badge badge-primary"><fmt:message key="text.order.payment_confirmation"/></span>
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
                       class="btn btn-secondary btn-sm" role="button"><fmt:message key="button.order.view"/> </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
