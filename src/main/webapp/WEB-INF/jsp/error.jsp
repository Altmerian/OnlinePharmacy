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
    <title><fmt:message key="title.error"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container mt-4">
    <div class="jumbotron text-center">
        <h1><fmt:message key="text.error"/></h1>
        <p class="lead"><fmt:message key="message.error"/></p>

            <c:choose>
                <c:when test="${pageContext.errorData.statusCode == 500}">
                    <fmt:message key="message.internal.error"/>
                </c:when>
                <c:when test="${pageContext.errorData.statusCode == 404}">
                    <fmt:message key="message.page.error"/>
                </c:when>
                <c:otherwise>
                    ${pageContext.exception}
                </c:otherwise>
            </c:choose> 
        <h1 class="display-1">${pageContext.errorData.statusCode}</h1>
    </div>   
</div>
<footer class="footer">
  <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>