<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <!-- Awesome icons -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
    integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <title>View item</title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container-fluid">
    <table class="table">
        <thead>
        <tr>
            <th>
                <fmt:message key="text.label"/>
            </th>
            <th>
                <fmt:message key="text.dosage"/>
            </th>
            <th>
                <fmt:message key="text.volume"/>
            </th>
            <th>
                <fmt:message key="text.manufacturer"/>
            </th>
            <th>
                <fmt:message key="text.price"/>
            </th>
            <th>
                <fmt:message key="text.byPrescription"/>
            </th>
            <th>
                <fmt:message key="text.description"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                ${item.label}
            </td>
            <td>
                ${item.dosage}
            </td>
            <td>
                ${item.volume} ${item.volumeType}
            </td>
            <td>
                ${item.manufacturerName}
            </td>
            <td>
                ${item.price}
            </td>
            <td>
                <c:if test="${item.byPrescription}">
                    <span class="glyphicon glyphicon-ok"></span>
                </c:if>
            </td>
            <td>
                ${item.description}
            </td>
        </tr>
        </tbody>
    </table>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>
