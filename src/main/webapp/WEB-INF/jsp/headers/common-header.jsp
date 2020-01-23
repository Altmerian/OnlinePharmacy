<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title><fmt:message key="title.catalog"/></title>
</head>
<body>
<div class="jumbotron">
    <h2>
        <fmt:message key="message.company.name"/>
        <small><fmt:message key="message.staffOnly"/></small>
    </h2>
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
    </div>
    <div class="btn-group">
        <button type="submit" class="btn" form="toRU"><fmt:message key="button.name.ru"/></>
        <button type="submit" class="btn" form="toEN"><fmt:message key="button.name.en"/></>
    </div>
    <div class="messages">
        <c:if test="${sessionScope.user != null}">
            <span><fmt:message key="text.welcome"/>, ${sessionScope.user.login}</span>
        </c:if>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>

