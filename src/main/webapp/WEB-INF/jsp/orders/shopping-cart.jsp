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
    <title>Shopping Cart</title>
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
                        ${count}
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=view-item&id=${entry.key.id}">
                        <b>${entry.key.label}</b> ${entry.key.dosage}, ${entry.key.volume} ${entry.key.volumeType}
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
                    ${entry.key.price}
                </td>
                <td>
                    <c:if test="${entry.key.byPrescription}">
                        <i class="fas fa-check"></i>
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
                ${total_quantity}
            </th>
            <th>
                ${total_amount}
            </th>
            <th></th>
            <th>
                <div class="container col-sm-6 ml-0 pl-0">
                    <a class="badge badge-danger" href="${pageContext.request.contextPath}/controller?command=clear_shopping_cart">Clear shopping cart</a>
                </div>
            </th>
        </tfoot>
    </table>
    <c:if test="${count != 0}">
    <form action="controller" method="post">
        <input type="hidden" name="total_amount" value="${total_amount}"/>
        <input type="hidden" name="command" value="submit_order"/>
        <div class="col-sm-4 mx-auto">
            <input type="submit" style="padding: 10px" class="btn btn-success btn-lg btn-block active"
                   value="<fmt:message key="button.order.submit"/> "/>
        </div>
    </form>
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
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>