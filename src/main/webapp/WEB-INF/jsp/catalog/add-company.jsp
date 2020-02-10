<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
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
    <title><fmt:message key="title.company.add"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container col-sm-8 mx-auto mt-2">
    <div>
        <a href="${header.Referer}"><i class="fas fa-arrow-left"></i><fmt:message key="link.back"/></a>
    </div>
    <!-- Messages -->
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.company.add.success"/> : <c:out value="${sessionScope.company.name}"/>
        </div>
        <c:set var="success_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="message.company.add.error"/>
        </div>
        <c:set var="error_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.success_country_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.country.add.success"/> : <c:out value="${country}"/></a>
        </div>
        <c:set var="success_country_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${sessionScope.error_country_message}">
        <div class="alert alert-warning" role="alert">
            <fmt:message key="message.country.add.error"/>
        </div>
        <c:set var="error_country_message" value="false" scope="session"/>
    </c:if>
    <!-- Country Form -->
    <form id="newCountry" action="controller" method="POST">
        <input type="hidden" name="command" value="add_country">
        <div class="input-group">
            <input class="form-control" type="text" name="country" aria-describedby="countryHelp" placeholder="<fmt:message 
                key="text.enter.country.name"/>"/>
            <div class="input-group-append">
                <button class="btn btn-outline-primary" type="submit" form="newCountry"><fmt:message
                key="button.add"/></button>
            </div>
        </div>
        <small id="countryHelp" class="form-text text-muted"><fmt:message 
                key="text.new.country.help"/></small>
    </form>
    <!-- Company Form -->
    <div class="container col-sm-6 text-center">
        <h5><fmt:message key="text.addCompany"/></h5>
    </div>
    <form id="company" action="controller" method="POST">
        <input type="hidden" name="command" value="add-company"/>
        <div class="form-group">
            <label for="name">
                <fmt:message key="text.name"/>:
            </label>
            <input type="text" id="name" name="manufacturer_name" class="form-control"
                   placeholder="<fmt:message key="text.name"/>" required/>
        </div>
        <div class=" form-group">
            <label for="sel">
                <fmt:message key="text.country"/>:
            </label>
            <select name="country_id" class="form-control" id="sel" required>
                <c:forEach var="country" items="${countries}">
                    <option value="${country.id}"><c:out value="${country.name}"/></option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="website">
                <fmt:message key="text.website"/>:
            </label>
            <input type="text" id="website" name="website" class="form-control"
                   placeholder="<fmt:message key="text.website"/>"/>
        </div>
        <!-- Button -->
        <div style="padding: 10px 10px 0 0">
            <input class="btn btn-primary" form="company" type="submit" value="<fmt:message key="button.company.add"/>"/>
        </div>
    </form>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>
