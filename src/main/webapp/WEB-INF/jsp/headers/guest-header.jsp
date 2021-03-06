<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="title.page"/></title>
</head>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <!-- Logo -->
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">
        <img src="/static/img/logo1.png" width="75" height="50" class="d-inline-block align-top" alt="">
        <fmt:message key="title.pharmacy"/></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse flex-column align-items-start ml-lg-2 ml-0" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
            <!-- Home -->
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/"><fmt:message key="link.home"/>
                    <span class="sr-only">(current)</span></a></li>
            <!-- Catalog -->
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=view-catalog&page_number=1&limit=10">
                    <fmt:message key="link.view.catalog"/></a></li>
            <!-- Registration -->
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/register"/>
                <i class="fas fa-user"></i> <fmt:message key="link.register"/></a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <!-- Change locale -->
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
            <div class="btn-group btn-group-sm mr-2 mb-3 mt-2" role="group">
                <button type="submit" class="btn btn-secondary btn-sm" form="toRU"><fmt:message key="button.name.ru"/></>
                <button type="submit" class="btn btn-secondary btn-sm" form="toEN"><fmt:message key="button.name.en"/></>
            </div>
            <!-- Search -->
            <form class="form-inline" role="search" action="controller" method="get" height="80%">
                <input type="hidden" name="command" value="search-item">
                <input type="hidden" name="page_number" value="1"/>
                <input type="hidden" name="limit" value="10"/>
                <div class="input-group">
                    <input class="form-control" type="search" name="search" aria-label="Search" placeholder="<fmt:message key="text.enter.drug.name"/>"/>
                    <div class="input-group-append">
                        <button class="btn btn-outline-success" type="submit"><i class="fas fa-search"></i></button>
                    </div>
                </div>
            </form>
            <!-- Login -->
            <form class="form-inline mx-2" action="controller" method="post" width="80%">
                <input type="hidden" name="command" value="login"/>
                <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="<fmt:message key="text.username"/>"
                         name="login">
                    <input type="password" class="form-control" placeholder="<fmt:message key="text.password"/>"
                         name="password">
                    <div class="input-group-append">
                        <button class="btn btn-outline-success" type="submit"><i class="fas fa-sign-in-alt"></i></button>
                    </div>
                </div>
            </form>
        </ul>
    </div>
</nav>
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
