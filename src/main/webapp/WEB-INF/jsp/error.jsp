<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
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
    <title>Error Page</title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="jumbotron text-center">
    <h1>Opps...</h1>
    <p class="lead"><fmt:message key="message.error"/></p>
</div>
<div class="container">
<table class="table table-striped">
 <tr valign = "top">
    <td width = "40%"><b>Error:</b></td>
    <td>
        <c:choose>
        <c:when test="${pageContext.errorData.statusCode == 500}">
            Internal Server Error
        </c:when>
        <c:when test="${pageContext.errorData.statusCode == 404}">
            Page not found
        </c:when>
        <c:otherwise>
            </br>
            ${pageContext.exception}
        </c:otherwise>
        </c:choose>
    </td>
 </tr>
 <tr valign = "top">
    <td><b>URI:</b></td>
    <td>${pageContext.errorData.requestURI}</td>
 </tr>

 <tr valign = "top">
    <td><b>Status code:</b></td>
    <td>${pageContext.errorData.statusCode}</td>
 </tr>

 <tr valign = "top">
     <td><b>Message:</b></td>
     <td>${pageContext.exception.detailMessage}</td>
 </tr>

 <tr valign = "top">
    <td><b>Stack trace:</b></td>
    <td>
       <c:forEach var = "trace"
            items = "${pageContext.exception.stackTrace}">
            <p>${trace}</p>
       </c:forEach>
    </td>
 </tr>
</table>
</div>
<footer class="footer">
  <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>