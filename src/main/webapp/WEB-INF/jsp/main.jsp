<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

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
    <title><fmt:message key="title.page"/></title>
</head>
<body>
    <header>
        <ctg:header/>
    </header>
    <div class="container col-sm-8">
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success mx-auto" role="alert">
            <fmt:message key="message.login.success"/> <b><c:out value=" ${sessionScope.user_name}"/></b>
        </div>
        <c:set var="success_message" value="false" scope="session"/>
    </c:if>
    </div>
    <!-- Carousel -->
    <div class="container col-sm-8 mx-auto">
      <div id="carousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
          <li data-target="#carousel" data-slide-to="0"></li>
          <li data-target="#carousel" data-slide-to="1" class="active"></li>
          <li data-target="#carousel" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner h-40">
          <div class="carousel-item">
            <img src="/static/img/apple-17528_1920.jpg" class="d-block w-40" alt="Girl and apple">
            <div class="carousel-caption d-none d-md-block">
              <h5>Питаемся правильно</h5>
              <p>Правила питания, или как не позволить жиру отложиться в жир</p>
            </div>
          </div>
          <div class="carousel-item active">
            <img src="/static/img/Вирус.jpg" class="d-block w-45" alt="Virus">
            <div class="carousel-caption d-none d-md-block">
              <h5>Новости науки</h5>
              <p>Модифицированный вирус гриппа убивает рак</p>
            </div>
          </div>
          <div class="carousel-item">
            <img src="/static/img/Сотовый телефон.jpg" class="d-block w-40" alt="telephone">
            <div class="carousel-caption d-none d-md-block">
              <h5>Гаджеты и здоровье</h5>
              <p>Вреден ли сотовый телефон: новые данные о старой проблеме</p>
            </div>
          </div>
        </div>
        <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
    </div>
    <footer class="footer">
		<jsp:include page="/WEB-INF/jsp/footer.jsp" />
	</footer>
</body>
</html>