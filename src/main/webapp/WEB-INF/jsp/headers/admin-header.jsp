<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <title><fmt:message key="Admin header"/></title>
</head>
<!--New content-->
<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
  <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="/static/img/logo1.png" width="40" height="40" alt="">
        </a>
    <ul class="nav navbar-nav">
        <li class="active"><a href="${pageContext.request.contextPath}/"><fmt:message key="link.home"/></a></li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="link.orders"/>
                <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/controller?command=view-orders"><fmt:message 
                        key="link.orders.view.own"/></a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=view-all-orders&limit=20&page_number=1">
                        <fmt:message key="link.orders.view.all"/></a></li>
            </ul>
        </li>
        <li><a href="${pageContext.request.contextPath}/controller?command=view-prescriptions" class="current">
            <fmt:message key="link.prescriptions"/></a></li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="link.users"/>
                <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/register"><fmt:message
                        key="link.register.new.user"/></a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=view-all-users&page_number=1&limit=20">
                        <fmt:message key="link.manage.users"/></a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key="title.catalog"/>
                <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="${pageContext.request.contextPath}/controller?command=view-add-item"><fmt:message
                        key="button.item.add"/></a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/Controller?command=view-catalog&page_number=1&limit=10">
                        <fmt:message key="link.view.catalog"/></a></li>
            </ul>
        </li>
    </ul>
    <p class="navbar-text">
        <c:if test="${sessionScope.user != null}">
            <span><fmt:message key="text.welcome"/>, ${sessionScope.user.login}</span>
        </c:if>
    </p>
    <form class="navbar-form navbar-right" action="controller" method="post">
            <div class="form-group">
                <input type="hidden" name="command" value="logout"/>
            </div>
            <button type="submit" class="btn btn-link"><fmt:message
                    key="link.logout"/></button>
        </form>
        <form class="navbar-form navbar-right" action="controller" method="get">
            <div class="form-group">
                <input type="hidden" name="command" value="view-user"/>
                <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
            </div>
            <button type="submit" class="btn btn-link"><fmt:message
                    key="link.profile"/></button>
        </form>
        <form class="navbar-form navbar-right" role="search" action="controller" method="get">
            <input type="hidden" name="command" value="search-item">
            <input type="hidden" name="page_number" value="1"/>
            <input type="hidden" name="limit" value="10"/>
            <div class="form-group">
                <input type="text" name="search" class="form-control" style="width: 300px;"
                        placeholder="<fmt:message key="text.enter.drug.name"/> "/>
                <input class="btn btn-default" type="submit" value="<fmt:message key="text.search"/>"/>
            </div>
        </form>
        <form class="navbar-form navbar-right" action="controller" method="get">
            <input type="hidden" name="command" value="view-shopping-cart"/>
            <input class="btn btn-default" type="submit"
            value="<fmt:message key="link.shopping.cart"/>(${shopping_cart.totalCount})"/>
        </form>
        <div class="jumbotron">
            <div class="change-locale">
                <form action="controller" method="post" id="toRU">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="locale" value="ru_RU"/>
                    <input type="hidden" name="from" value="${pageContext.request.requestURI}?${pageContext.request.queryString}"/>
                </form>
                <form action="controller" method="post" id="toEN">
                    <input type="hidden" name="command" value="change_locale"/>
                    <input type="hidden" name="locale" value="en_US"/>
                    <input type="hidden" name="from" value="${pageContext.request.requestURI}?${pageContext.request.queryString}"/>
                </form>
                <div class="btn-group navbar-right">
                    <button type="submit" class="navbar-btn" form="toRU"><fmt:message key="button.name.ru"/></>
                    <button type="submit" class="navbar-btn" form="toEN"><fmt:message key="button.name.en"/></>
                </div>
            </div>
            
        </div>
  </div>
</nav>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>