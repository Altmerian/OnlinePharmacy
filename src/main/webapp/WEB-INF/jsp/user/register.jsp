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
    <title><fmt:message key="link.register"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container col-sm-offset-2 col-sm-8 mt-1">
    <!-- Messages -->
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.register.success"/>: <c:out value="${sessionScope.user_name}"/>
        </div>
        <c:set var="success_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="message.register.error"/>
        </div>
        <c:set var="error_message" value="false" scope="session"/>
    </c:if>
    <div class="container mx-auto">
        <h4><fmt:message key="text.register"/>:</h4>
    </div>
    <!-- Form -->
    <form action="/controller" method="POST">
        <input type="hidden" name="command" value="register"/>
        <div class="form-group">
            <label for="login">
                <fmt:message key="text.username"/>:
            </label>
            <input type="text" class="form-control" id="login" name="login" pattern="[a-zA-Z0-9]{4,20}" required> <br/>
        </div>
        <div class="form-group">
            <label for="password">
                <fmt:message key="text.password"/>:
            </label>
            <input type="password" class="form-control" id="password" name="password" pattern=".{4,}" required> <br/>
        </div>
        <div class="form-group">
            <label for="email">
                <fmt:message key="text.email"/>:
            </label>
            <input type="email" class="form-control" id="email" name="email" pattern="\w{2,40}@\w{2,20}.\w{2,4}" required> <br/>
        </div>
        <div class="form-row">
            <div class="col">
                <label for="first_name">
                    <fmt:message key="text.firstName"/>:
                </label>
                <input type="text" class="form-control" id="first_name" name="first_name"> <br/>
            </div>
            <div class="col">
                <label for="last_name">
                    <fmt:message key="text.lastName"/>:
                </label>
                <input type="text" class="form-control" id="last_name" name="last_name"> <br/>
            </div>
        </div>
        <div class="form-group">
            <label for="address">
                <fmt:message key="text.address"/>:
            </label>
            <input type="text" class="form-control" id="address" name="address" value="${requestScope.address}"> <br/>
        </div>
        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <div class="form-group">
                <label for="sel">
                    <fmt:message key="text.user.role"/>:
                </label>
                <select name="user_role" class="form-control" id="sel" required>
                    <option value="USER"><fmt:message key="text.user"/></option>
                    <option value="DOCTOR"><fmt:message key="text.doctor"/></option>
                    <option value="MANAGER"><fmt:message key="text.manager"/></option>
                </select>
            </div>
        </c:if>
            <!-- Button -->
        <div class="form-group">
            <input type="submit" class="btn btn-primary" value="<fmt:message key="button.name.register"/>"/>
        </div>
    </form>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>