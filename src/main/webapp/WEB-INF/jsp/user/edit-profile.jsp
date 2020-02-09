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
    <div>
        <a href="${header.Referer}"><i class="fas fa-arrow-left"></i><fmt:message key="link.back"/></a>
    </div>
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
    <form action="controller" method="POST">
        <input type="hidden" name="command" value="edit-user"/>
        <input type="hidden" name="user_id" value="${requestScope.user.id}"/>
        <div class="form-group">
            <!-- Username -->
            <label for="username"><fmt:message key="text.username"/></label>
                <input type="text" id="username" name="username" class="form-control" value="${requestScope.user.login}" disabled>
        </div>
            <!--NEW Password-->
        <div class="form-group">
            <label for="new_password"><fmt:message key="text.password.new"/></label>
                <input type="password" id="new_password" name="password" class="form-control" value="${requestScope.user.password}" 
                aria-describedby="passwordHelp" pattern=".{4,}" required>
                <small id="passwordHelp" class="form-text text-muted"><fmt:message key="text.password.help"/></small>
        </div>
            <!-- E-mail -->
        <div class="form-group">
            <label for="email"><fmt:message key="text.email"/></label>
                <input type="email" id="email" name="email" class="form-control"  value="${requestScope.user.email}" aria-describedby="emailHelp" pattern="\w{2,40}@\w{2,20}.\w{2,4}">
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="text.email.help"/></small>
        </div>
                <!-- First Name Last Name-->
        <div class="form-row">
            <div class="col">
                <label for="first_name"><fmt:message key="text.firstName"/></label>
                <input type="text" id="first_name" name="first_name" class="form-control" value="${requestScope.user.firstName}">
            </div>
            <div class="col">
                <label for="last_name"><fmt:message key="text.lastName"/></label>
                <input type="text" id="last_name" name="last_name" class="form-control" value="${requestScope.user.lastName}">
            </div>
        </div><br>
            <!-- Address-->
        <div class="form-group">
            <label for="address"><fmt:message key="text.address"/></label>
                <input type="text" id="address" name="address" value="${requestScope.user.address}" class="form-control">
        </div>
            <!-- Button -->
        <div class="form-group">
            <input type="submit" class="btn btn-primary" value="<fmt:message key="button.change" />"/>
        </div>
    </form>
    <span>
        <a href="${header.Referer}" class="btn btn-warning" role="button"><fmt:message key="button.cancel"/></a>
    </span>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>