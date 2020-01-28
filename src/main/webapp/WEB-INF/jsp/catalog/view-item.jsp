<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<!doctype html>
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
    <title>View item</title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<!-- Item -->
<div class="container-fluid mt-5">
    <table class="table table-striped">
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
                    <i class="fas fa-check"></i>
                </c:if>
            </td>
            <td>
                ${item.description}
            </td>
            <td>
                <form class="form-inline mr-2" action="controller" method="post">
                    <input type="hidden" name="command" value="add_item_to_shopping_cart"/>
                    <input type="hidden" name="item_id" value="${item.id}"/>
                    <input type="hidden" name="page_number" value="${param.page_number}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <div class="form-group row">
                        <input type="number" min="1" max="200" name="quantity" value="1"/>
                        <input type="submit" class="btn btn-warning btn-sm"
                               value="<fmt:message key="button.item.buy"/>"/>
                   </div>
                </form>
                <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
                    <form class="form-inline ml-0" action="controller" method="get">
                        <input type="hidden" name="command" value="view-edit-item"/>
                        <input type="hidden" name="id" value="${item.id}"/>
                        <input type="submit" class="btn btn-success btn-sm"
                               value="<fmt:message key="button.change"/>"/>
                    </form>
                </c:if>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!-- Messages -->
<div class="container col-sm-6 text-center">
     <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.item.add.error"/>
        </div>
        <c:set var="error_message" scope="session" value="false"/>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.item.add.success"/>
            <a href="${pageContext.request.contextPath}/controller?command=view_item&id=${sessionScope.item.id}"> ${sessionScope.item.label}</a>
        </div>
        <c:set var="success_message" scope="session" value="false"/>
    </c:if>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>
