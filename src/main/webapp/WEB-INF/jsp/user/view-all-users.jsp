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
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/custom.css"/>
    <title><fmt:message key="title.users"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container">
    <c:if test="${requestScope.error_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.users.not.found"/>
        </div>
        <c:set var="error_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${requestScope.success_message}">
        <div class="alert alert-info" role="alert">
            <fmt:message key="message.users.found"/>: <c:out value="${requestScope.number_of_users}"/>
        </div>
        <c:set var="success_message" value="false" scope="session"/>
    </c:if>
</div>
<c:choose>
    <c:when test="${requestScope.number_of_users % param.limit == 0}">
        <c:set var="number_of_pages" value="${requestScope.number_of_users div param.limit}"/>
    </c:when>
    <c:otherwise>
        <c:set var="number_of_pages" value="${requestScope.number_of_users div param.limit + 1}"/>
    </c:otherwise>
</c:choose>
<div class="container-fluid">
    <div class="form-row">
        <div class="col-auto">
            <span><fmt:message key="text.search.userBy"/>: </span>
        </div>
        <form class="form-inline ml-2" role="search" action="controller" method="get">
            <input type="hidden" name="command" value="view-user">
            <div class="input-group">
                <input type="number" min="1" name="user_id" class="form-control" style="width: 300px;"
                       placeholder="ID#"/>
                <div class="input-group-append">
                    <button class="btn btn-outline-success" type="submit"><i class="fas fa-search"></i></button>
                </div>
            </div>
        </form>
        <form class="form-inline ml-2" role="search" action="controller" method="get">
            <input type="hidden" name="command" value="view-user">
            <div class="input-group">
                <input type="text" name="email" class="form-control" style="width: 300px;"
                       placeholder="<fmt:message key="text.email"/> "/>
                <div class="input-group-append">
                    <button class="btn btn-outline-success" type="submit"><i class="fas fa-search"></i></button>
                </div>
            </div>
        </form>
        <form class="form-inline ml-2" role="search" action="controller" method="get">
            <input type="hidden" name="command" value="view-user">
            <div class="input-group">
                <input type="text" name="login" class="form-control" style="width: 300px;"
                       placeholder="<fmt:message key="text.username"/> "/>
                <div class="input-group-append">
                    <button class="btn btn-outline-success" type="submit"><i class="fas fa-search"></i></button>
                </div>
            </div>
        </form>
    </div>
    <div style="padding: 10px;">
        <span><fmt:message key="text.numberOfUsers.onPage"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-users">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" 
                value="${((param.page_number-1)* param.limit) / 5 + 1}"/>"/>
            <input type="hidden" name="limit" value="5"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="5"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-users">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" 
                value="${((param.page_number-1)* param.limit) / 10 + 1}"/>"/>
            <input type="hidden" name="limit" value="10"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="10"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-users">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" 
                value="${((param.page_number-1)* param.limit) / 20 + 1}"/>"/>
            <input type="hidden" name="limit" value="20"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="20"/>
        </form>
        <span><fmt:message key="text.goTo.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-users">
            <input type="number" max="${number_of_pages}" min="1" name="page_number"
                   placeholder="${param.page_number}/<fmt:parseNumber integerOnly="true" type="number"
                value="${number_of_pages}"/>" required/>
            <input type="hidden" name="limit" value="${param.limit}"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.go"/>"/>
        </form>
        <c:choose>
            <c:when test="${param.page_number != 1}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-all-users">
                    <input type="hidden" name="page_number" value="${param.page_number - 1}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.previous"/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.previous"/>" disabled/>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${param.page_number < (number_of_pages - 1)}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-all-users">
                    <input type="hidden" name="page_number" value="${param.page_number + 1}"/>
                    <input type="hidden" name="limit" value="${param.limit}"/>
                    <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.next"/>"/>
                </form>
            </c:when>
            <c:otherwise>
                <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.next"/>" disabled/>
            </c:otherwise>
        </c:choose>
    </div>
    <table class="table table-striped">
        <thead>
            <th">
                #
            </th>
            <th">
                <fmt:message key="text.username"/>
            </th>
            <th">
                <fmt:message key="text.firstName"/> <fmt:message key="text.lastName"/>
            </th>
            <th">
                <fmt:message key="text.role"/>
            </th>
            <th">
                <fmt:message key="text.email"/>
            </th>
            <th>
                <fmt:message key="text.address"/>
            </th>
            <th></th>
        </thead>
        <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td>
                    <c:out value="${user.id}"/>
                </td>
                <td>
                    <c:out value="${user.login}"/>
                </td>
                <td>
                    <c:out value="${user.firstName} ${user.lastName}"/>
                </td>
                <td>
                    <c:out value="${user.role}"/>
                </td>
                <td>
                    <c:out value="${user.email}"/>
                </td>
                <td>
                    <c:out value="${user.address}"/>
                </td>
                <td>
                    <form class="inline" role="search" action="controller" method="get">
                        <input type="hidden" name="command" value="view-user">
                        <input type="hidden" name="user_id" value="${user.id}">
                        <input class="btn btn-info btn-sm" type="submit" value="<fmt:message key="button.user.view"/>"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
