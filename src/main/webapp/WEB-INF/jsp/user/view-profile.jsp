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
    <title><fmt:message key="title.user.profile"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container">
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-info" role="alert">
            <span>
                <fmt:message key="message.user.edit.success"/>
             </span>
            <c:set var="success_message" value="false" scope="session"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.user.edit.error"/>
            <c:set var="error_message" value="false" scope="session"/>
        </div>
    </c:if>
    <h3><c:out value="${requestScope.user.login}"/></h3>
        <form role="form" class="form-inline" action="controller" method="get">
            <input type="hidden" name="command" value="view-orders"/>
            <input type="hidden" name="user_id" value="${requestScope.user.id}"/>
            <input type="submit" class="btn btn-default" value="<fmt:message key="button.orders.view.all"/> "/>
        </form>
    <hr>
    <h4>
        <span><b><fmt:message key="text.firstName"/></b>: <c:out value="${requestScope.user.firstName}"/></span>
        <br>
        <span><b><fmt:message key="text.lastName"/></b>: <c:out value="${requestScope.user.lastName}"/></span>
        <br>
        <span><b><fmt:message key="text.email"/></b>: <c:out value="${requestScope.user.email}"/> </span>
        <br>
        <span><b><fmt:message key="text.address"/></b>: <c:out value="${requestScope.user.address}"/></span>
    </h4>
    <div>
        <form role="form" class="form-inline" action="controller" method="get">
            <input type="hidden" name="command" value="view-edit-user"/>
            <input type="hidden" name="user_id" value="${requestScope.user.id}"/>
            <input type="submit" class="btn btn-default" value="<fmt:message key="button.change"/> "/>
        </form>
    </div>
</div>
</body>
</html>
