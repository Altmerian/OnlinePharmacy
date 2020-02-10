<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
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
    <title><fmt:message key="tittle.shoppingCart"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<!-- Shopping cart -->
<div class="container col-sm-6 text-center mt-1">
    <h4><fmt:message key="title.shoppingCart"/>:</h4>
</div>
<c:set var="count" value="0" scope="page"/>
<c:set var="total_quantity" value="0" scope="page"/>
<c:set var="total_amount" value="0" scope="page"/>
<div class="container-fluid">
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
            <th>
                <fmt:message key="text.prescription.status"/>
            </th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${sessionScope.cart_items}">
            <c:set var="count" value="${count+1}"/>
            <c:set var="total_quantity" value="${total_quantity + entry.value}"/>
            <c:set var="total_amount" value="${total_amount + entry.value * entry.key.price}"/>
            <tr>
                <td>
                    <c:out value="${count}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=view-item&id=${entry.key.id}">
                        <b><c:out value="${entry.key.label}"/></b>
                        <c:out value="${entry.key.dosage}, ${entry.key.volume} ${entry.key.volumeType}"/>
                    </a>
                </td>
                <td>
                    <form class="form-inline" action="controller" method="post">
                        <input type="hidden" name="command" value="add_item_to_shopping_cart"/>
                        <input type="hidden" name="change_quantity" value="true"/>
                        <input type="hidden" name="old_quantity" value="${entry.value}"/>
                        <input type="hidden" name="item_id" value="${entry.key.id}"/>
                        <div class="form-group">
                            <input type="number" class="form-control" min="1" max="999" name="quantity"
                                   value="${entry.value}"/>
                        </div>
                        <button type="submit" class="btn btn-warning" value=""><fmt:message
                                key="button.change"/></button>
                    </form>
                </td>
                <td>
                    <c:out value="${entry.key.price}"/>
                </td>
                <td>
                    <c:if test="${entry.key.byPrescription}">
                        <i class="fas fa-check"></i>
                    </c:if>
                </td>
                <td>
                    <c:if test="${entry.key.byPrescription}">
                        <c:choose>
                            <c:when test="${prescriptions[entry.key.id].status eq 'requested'}">
                                <span class="badge badge-primary"><fmt:message key="text.prescription.status.requested"/></span>
                            </c:when>
                            <c:when test="${prescriptions[entry.key.id].status eq 'approved'}">
                                <span class="badge badge-success"><fmt:message key="text.prescription.status.approved"/></span>
                                <div>
                                    <javatime:format value="${prescriptions[entry.key.id].validUntil}" style="MS" />
                                </div>
                            </c:when>
                            <c:when test="${prescriptions[entry.key.id].status eq 'overdue'}">
                                <span class="badge badge-warning"><fmt:message key="text.prescription.status.overdue"/></span>
                                <div>
                                    <javatime:format value="${prescriptions[entry.key.id].validUntil}" style="MS" />
                                </div>
                            </c:when>
                            <c:when test="${prescriptions[entry.key.id].status eq 'rejected'}">
                                <span class="badge badge-danger"><fmt:message key="text.prescription.status.rejected"/></span>
                            </c:when>
                        </c:choose>
                            <c:if test="${prescriptions[entry.key.id] eq null or prescriptions[entry.key.id].status eq 'rejected' or prescriptions[entry.key.id].status eq 'overdue'}">
                                <form class="form-inline" action="controller" method="POST">
                                    <input type="hidden" name="command" value="request-prescription"/>
                                    <input type="hidden" name="prescription_id" value="${prescriptions[entry.key.id].id}"/>
                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                    <input type="hidden" name="drug_id" value="${entry.key.id}"/>
                                    <input type="submit" class="btn btn-success btn-sm"
                                           value="<fmt:message key="button.prescription.request"/>"/>
                                </form>
                            </c:if>
                    </c:if>
                </td>
                <td>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="remove_item_from_shopping_cart"/>
                        <input type="hidden" name="item_id" value="${entry.key.id}"/>
                        <input type="submit" class="btn btn-danger" value="<fmt:message key="button.delete"/> "/>
                    </form>
                </td>
            </c:forEach>
        </tr>
        </tbody>
        <tfoot>
            <td></td>
            <th>
                <fmt:message key="text.total"/>
            </th>
            <th>
                <c:out value="${total_quantity}"/>
            </th>
            <th>
                <c:out value="${total_amount}"/>
            </th>
            <th></th>
            <th></th>
            <th>
                <div class="container col-sm-6 ml-0 pl-0">
                    <a class="badge badge-danger" href="${pageContext.request.contextPath}/controller?command=clear_shopping_cart">
                        <fmt:message key="link.clear.shopping.cart"/></a>
                </div>
            </th>
        </tfoot>
    </table>
    <!-- Button -->
    <c:if test="${count != 0}">
        <c:choose>
                <c:when test="${order_available}">
                <form action="controller" method="post">
                    <input type="hidden" name="total_amount" value="${total_amount}"/>
                    <input type="hidden" name="command" value="submit_order"/>
                    <div class="col-sm-4 mx-auto">
                        <input type="submit" style="padding: 10px" class="btn btn-success btn-lg btn-block active"
                               value="<fmt:message key="button.order.submit"/> "/>
                    </div>
                </form>
                </c:when>
                <c:otherwise>
                    <div class="col-sm-4 mx-auto text-center">
                        <input type="submit" style="padding: 10px" class="btn btn-success btn-lg btn-block active"
                                   aria-describedby="order" value="<fmt:message key="button.order.submit"/> " disabled/>
                        <small id="order" class="form-text text-muted"><fmt:message key="text.order.help"/></small>
                    </div>
                </c:otherwise>
        </c:choose>
    </c:if>
</div>
<!-- Messages -->
<div class="container col-sm-4 text-center">
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.quantity.change.success"/>
        </div>
        <c:set var="success_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.quantity.change.error"/>
        </div>
        <c:set var="error_message" value="false" scope="session"/>
    </c:if>
</div>
<div class="container col-sm-4 text-center">
    <c:if test="${success_request_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.prescription.request.success"/>
        </div>
        <c:set var="success_request_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${error_request_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.prescription.request.error"/>
        </div>
        <c:set var="error_request_message" value="false" scope="session"/>
    </c:if>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>