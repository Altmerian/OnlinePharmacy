<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <title><fmt:message key="title.catalog"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<!-- Messages -->
<div class="container fluid mr-2">
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-danger text-center" role="alert">
            <fmt:message key="message.item.add.error"/>
        </div>
        <c:set var="error_message" scope="session" value="false"/>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success text-center" role="alert">
            <fmt:message key="message.item.add.success"/>
            <a href="${pageContext.request.contextPath}/controller?command=view_item&id=${sessionScope.item.id}"> ${sessionScope.item.label}</a>
        </div>
        <c:set var="success_message" scope="session" value="false"/>
    </c:if>
    <!-- Page navigation -->
     <c:choose>
        <c:when test="${requestScope.number_of_items == 10}">
            <c:set var="number_of_pages" value="1"/>
        </c:when>
        <c:otherwise>
            <c:set var="number_of_pages" value="${requestScope.number_of_items div param.limit + 1}"/>
        </c:otherwise>
    </c:choose>
    <div class="page-btns" style="padding: 10px;">
        <span><fmt:message key="text.numberOfItems.onPage"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" 
                value="${((param.page_number-1)* param.limit) / 5 + 1}"/>"/>
            <input type="hidden" name="limit" value="5"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="5"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
                value="${((param.page_number-1)* param.limit) / 10 + 1}"/>"/>
            <input type="hidden" name="limit" value="10"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="10"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
                value="${((param.page_number-1)* param.limit) / 20 + 1}"/>"/>
            <input type="hidden" name="limit" value="20"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="20"/>
        </form>

        <span><fmt:message key="text.goTo.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-catalog">
            <input type="number" max="${number_of_pages}" min="1" name="page_number"
                   placeholder="${param.page_number}/<fmt:parseNumber integerOnly="true" type="number"
                value="${number_of_pages}"/>" required/>
            <input type="hidden" name="limit" value="${param.limit}"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.go"/>"/>
        </form>
        <c:choose>
            <c:when test="${param.page_number != 1}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-catalog">
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
                    <input type="hidden" name="command" value="view-catalog">
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
    <!-- Table -->
    <table class="table table-striped">
        <thead class="thead-light">
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
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.items}">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=view-item&id=${item.id}"><b>${item.label}</b></a>
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
                        <i class="fas fa-check"></i>
                    </c:if>
                </td>
                <td>
                        ${item.description}
                </td>
                <td>
                    <form class="form-inline mr-2" action="controller" method="post">
                        <input type="hidden" name="command" value="add_item_to_shopping_cart"/>
                        <input type="hidden" name="item_id" value="${item.id}"/>
                        <input type="hidden" name="page_number" value="${param.page_number}"/>
                        <input type="hidden" name="limit" value="${param.limit}"/>
                        <div class="form-group">
                            <input type="number" min="1" max="200" name="quantity" value="1"/>
                            <input type="submit" class="btn btn-warning btn-sm"
                                   value="<fmt:message key="button.item.buy"/>"/>
                       </div>
                    </form>
                    <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
                        <form class="form-inline" action="controller" method="get">
                            <input type="hidden" name="command" value="view-edit-item"/>
                            <input type="hidden" name="id" value="${item.id}"/>
                            <input type="submit" class="btn btn-success btn-sm"
                                   value="<fmt:message key="button.change"/>"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>
