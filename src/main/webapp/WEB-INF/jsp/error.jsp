<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>
<%@ taglib prefix="ctg" uri="customtags" %>

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
    <title>Error Page</title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="jumbotron">
  <div class="container text-center">
    <display3>Opps...</display3>
    <p><fmt:message key="message.error"/></p>
  </div>
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
     <td>${pageContext.exception.message}</td>
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
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>