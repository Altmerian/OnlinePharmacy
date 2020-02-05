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
    <title>Edit item</title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container">
    <div>
        <a href="${header.Referer}"><i class="fas fa-arrow-left"></i><fmt:message key="link.back"/></a>
    </div>
    <div class="col-sm-offset-2 col-sm-8">
        <!-- Messages -->     
        <c:if test="${sessionScope.success_message}">
            <div class="alert alert-success" role="alert">
                <fmt:message key="message.item.edit.success"/>
            </div>
                <c:set var="success_message" value="false" scope="session"/>
        </c:if>
        <c:if test="${sessionScope.error_message}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="message.item.edit.error"/>
            </div>
                <c:set var="error_message" value="false" scope="session"/>
        </c:if>
        <c:if test="${sessionScope.success_message_delete}">
            <div class="alert alert-success">
            <span>
                <fmt:message key="message.item.deleted"/>
            </span>
                <c:set var="success_message_delete" value="false" scope="session"/>
            </div>
        </c:if>
        <c:if test="${sessionScope.error_message_delete}">
            <div class="alert alert-danger">
                <fmt:message key="message.item.not.deleted"/>
                <c:set var="error_message_delete" value="false" scope="session"/>
            </div>
        </c:if>
        <!-- Item -->
        <form role="form" id="edit" action="controller" method="post">
            <input type="hidden" name="command" value="edit-item"/>
            <input type="hidden" name="id" value="${requestScope.item.id}"/>
            <div class="form-group">
                <label for="label"><fmt:message key="text.label"/>:</label>
                <input type="text" id="label" name="label" class="form-control"
                       value="${requestScope.item.label}" required/>
            </div>
            <div class="form-group">
                <label for="sel1"><fmt:message key="text.dosage"/></label>
                <select name="dosage_id" class="form-control" id="sel1" required>
                    <c:forEach var="dosage" items="${requestScope.dosages}">
                        <option value="${dosage.id}" ${requestScope.item.dosageId == dosage.id ? 'selected' : ''}>
                        ${dosage.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-row">
                <div class="col">
                    <label for="volume"><fmt:message key="text.volume"/></label>
                    <input type="number" class="form-control" id="volume" step="0.01" name="volume"
                           value="${requestScope.item.volume}" required/>
                </div>
                <div class="col">
                    <label for="sel2"><fmt:message key="text.unit"/></label>
                    <select name="volume_type" class="form-control" id="sel2" required>
                        <c:forEach var="volume_type" items="${requestScope.volume_types}">
                            <option value="${volume_type}" ${requestScope.item.volumeType eq volume_type ? 'selected' : ''}>
                            ${volume_type}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="sel3"><fmt:message key="text.manufacturer"/></label>
                <select name="manufacturer_id" class="form-control" id="sel3">
                    <c:forEach var="company" items="${requestScope.companies}">
                        <option value="${company.id}"  ${requestScope.item.manufacturerId == company.id ? 'selected' : ''}>
                        ${company.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="price"><fmt:message key="text.price"/></label>
                <input type="number" class="form-control" id="price" step="0.01" name="price"
                       value="${requestScope.item.price}" required/>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" name="by_prescription"
                              value="true" ${requestScope.item.byPrescription ? 'checked' : ''}/><fmt:message
                        key="text.byPrescription"/></label>
            </div>
            <div class="form-group">
                <label for="description"><fmt:message key="text.description"/></label>
                <textarea class="form-control" rows="5" id="description"
                      name="description">${requestScope.item.description}</textarea>
            </div>
            <div class="form-group">
                <input type="submit" form="edit" class="btn btn-primary" value="<fmt:message key="button.change"/>"/>
            </div>
        </form>
        <form role="form" action="controller" method="post">
            <input type="hidden" name="command" value="delete-item"/>
            <input type="hidden" name="id" value="${requestScope.item.id}"/>
            <input type="submit" class="btn btn-danger" value="<fmt:message key="button.delete"/>"/>
        </form>
    </div>
</div>

</body>
</html>
