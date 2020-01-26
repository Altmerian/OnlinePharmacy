<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" 
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title><fmt:message key="title.catalog"/></title>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light rounded">
    <a class="navbar-brand">Online Pharmacy</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarsExample09">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="${pageContext.request.contextPath}/controller?command=change_locale&locale=en_US" 
                    id="dropdown09" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="flag-icon flag-icon-us"> </span>
                    <fmt:message key="button.name.en"/></a>
                <div class="dropdown-menu" aria-labelledby="dropdown09">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/controller?command=change_locale&locale=ru_RU">
                        <span class="flag-icon flag-icon-ru"> </span><fmt:message key="button.name.ru"/></a>
                </div>
            </li>
        </ul>
        <form class="form-inline" role="search" action="controller" method="get">
            <input type="hidden" name="command" value="search-item">
            <input type="hidden" name="page_number" value="1"/>
            <input type="hidden" name="limit" value="10"/>
            <input class="form-control mr-sm-2" type="search" aria-label="Search" placeholder="<fmt:message key="text.enter.drug.name"/>"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="text.search"/></button>
        </form>
    </div>
</nav>

 <!-- OLD   

    </form>
    <form class="navbar-form navbar-right" action="controller" method="get">
        <input type="hidden" name="command" value="view-shopping-cart"/>
        <input class="btn btn-default" type="submit"
        value="<fmt:message key="link.shopping.cart"/>(${shopping_cart.totalCount})"/>
    </form>
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
    </div> -->
 
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>
