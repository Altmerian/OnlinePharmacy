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
    <title><fmt:message key="text.login"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container">
  <div class="container col-sm-offset-2 col-sm-8">
    <div class="container col-sm-8">
        <h4><fmt:message key="message.login"/></h4>
    </div>
    <div class="container col-sm-8">
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.login.error"/>
        </div>
        <c:set var="error_message" value="false" scope="session"/>
    </c:if>
    </div>
    <form method="POST" action="/controller">
        <input type="hidden" name="command" value="login"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
        <div class="form-group">
            <label for="login">
                <fmt:message key="text.username"/>:
            </label>
            <input type="text" id="login" class="form-control" placeholder="<fmt:message key="text.username"/>"
                   name="login">
        </div>
        <div class="form-group">
            <label for="password">
                <fmt:message key="text.password"/>:
            </label>
            <input type="password" id="password" class="form-control" placeholder="<fmt:message key="text.password"/>"
                   name="password">
        </div>
        <input class="btn btn-primary" type="submit"
               value="<fmt:message key="button.name.login"/>">
    </form>
  </div>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>