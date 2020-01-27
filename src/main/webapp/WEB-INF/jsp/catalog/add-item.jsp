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
    <title>Add item</title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container mx-auto">
    <div class="col-sm-offset-2 col-sm-8">
        <div class="container col-sm-8">
            <h4><fmt:message key="text.addItem"/></h4>
        </div>
        <c:if test="${sessionScope.success_message}">
            <div class="alert alert-success" role="alert">
                <fmt:message key="message.item.add.success"/> : <a class="alert-link"
                href="${pageContext.request.contextPath}/controller?command=view_item&id=${sessionScope.item.id}"> ${sessionScope.item.label}</a>
            </div>
            <c:set var="success_message" value="false" scope="session"/>
        </c:if>
        <c:if test="${sessionScope.error_message}">
            <div class="alert alert-warning" role="alert"">
                <fmt:message key="message.item.add.error"/>
            </div>
            <c:set var="error_message" value="false" scope="session"/>
        </c:if>
        <form action="controller" method="POST">
            <input type="hidden" name="command" value="add-item"/>
            <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
            <div class="form-group">
                <label for="label">
                    <fmt:message key="text.label"/>:
                </label>
                <input type="text" id="label" name="label" class="form-control"
                       placeholder="<fmt:message key="text.label"/>" required/>
            </div>
            <div class="form-group">
                <label for="sel1">
                    <fmt:message key="text.dosage"/>:
                </label>
                <select name="dosage_id" class="form-control" id="sel1" required>
                    <c:forEach var="dosage" items="${dosages}">
                        <option value="${dosage.id}">${dosage.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="volume">
                    <fmt:message key="text.volume"/>:
                </label>
                <input type="number" class="form-control" id="volume" step="1" name="volume"
                       placeholder="<fmt:message key="text.volume"/>"
                required/>
            </div>
            <div class="form-group">
                <label for="sel2">
                    <fmt:message key="text.unit"/>:
                </label>
                <select name="volume_type" class="form-control" id="sel2" required>
                    <c:forEach var="volume_type" items="${volume_types}">
                        <option value="${volume_type}">${volume_type}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="sel3">
                    <fmt:message key="text.manufacturer"/>:
                </label>
                <select name="manufacturer_id" class="form-control" id="sel3">
                    <c:forEach var="company" items="${companies}">
                        <option value="${company.id}"> ${company.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="price">
                    <fmt:message key="text.price"/>:
                </label>
                <input type="number" class="form-control" id="price" step="0.01" name="price"
                       placeholder="<fmt:message key="text.price"/>"
                required/>
            </div>
            <div class="checkbox">
                <label><input type="checkbox" name="by_prescription" value="true"/>
                    <fmt:message key="text.byPrescription"/>
                </label>
            </div>
            <div class="form-group">
                <label for="description">
                    <fmt:message key="text.description"/>:
                </label>
                <textarea class="form-control" rows="5" id="description" name="description"
                          placeholder="<fmt:message key="text.description"/> ">
                </textarea>
            </div>
            <div style="padding: 10px 10px 0 0">
                <input class="btn btn-primary" type="submit" value="<fmt:message key="button.item.add"/>"/>
            </div>
        </form>
    </div>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>
