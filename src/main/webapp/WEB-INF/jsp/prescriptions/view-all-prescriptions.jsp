<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime"%>
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
    <title><fmt:message key="title.prescriptions"/></title>
</head>
<body>
<header>
    <ctg:header/>
</header>
<div class="container-fluid">
    <!-- Messages -->
    <c:if test="${requestScope.prescriptions == null}">
        <div class="alert alert-info" role="alert">
            <fmt:message key="message.prescriptions.null"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div class="alert alert-info" role="alert">
            <fmt:message key="message.prescription.status.change.error"/>
            <c:set var="error_message" scope="session" value="false"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.success_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.prescription.status.change.success"/>
            <c:set var="success_message" scope="session" value="false"/>
        </div>
    </c:if>
    <!-- Pagination -->
    <c:set var="number_of_pages" value="${requestScope.number_of_prescriptions div param.limit + 1}"/>
    <div style="padding: 10px;">
        <span><fmt:message key="text.number.of.prescriptions.on.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-prescriptions">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number"
                value="${((param.page_number-1)* param.limit) / 5 + 1}"/>"/>
            <input type="hidden" name="limit" value="5"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="5"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-prescriptions">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" 
                value="${((param.page_number-1)* param.limit) / 10 + 1}"/>"/>
            <input type="hidden" name="limit" value="10"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="10"/>
        </form>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-prescriptions">
            <input type="hidden" name="page_number" value="<fmt:parseNumber integerOnly="true" type="number" 
                value="${((param.page_number-1)* param.limit) / 20 + 1}"/>"/>
            <input type="hidden" name="limit" value="20"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="20"/>
        </form>
        <span><fmt:message key="text.goTo.page"/>:</span>
        <form style="display: inline-block" role="form" action="controller" method="get">
            <input type="hidden" name="command" value="view-all-prescriptions">
            <input type="number" max="${number_of_pages}" min="1" name="page_number"
                   placeholder="${param.page_number}/<fmt:parseNumber integerOnly="true" type="number"
                value="${number_of_pages}"/>" required/>
            <input type="hidden" name="limit" value="${param.limit}"/>
            <input class="btn btn-secondary btn-sm" type="submit" value="<fmt:message key="text.go"/>"/>
        </form>
        <c:choose>
            <c:when test="${param.page_number != 1}">
                <form style="display: inline-block" role="form" action="controller" method="get">
                    <input type="hidden" name="command" value="view-all-prescriptions">
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
                    <input type="hidden" name="command" value="view-all-prescriptions">
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
        <thead>
            <th>
                #
            </th>
            <th>
                <fmt:message key="text.customer"/>
            </th>
            <th>
                <fmt:message key="text.address"/>
            </th>
            <th>
                <fmt:message key="text.drug"/>
            </th>
            <th>
                <fmt:message key="text.prescription.status"/>
            </th>
            <th>
                <fmt:message key="text.validUntil"/>
            </th>
        </thead>
        <tbody>
        <c:forEach var="prescription" items="${requestScope.prescriptions}">
            <tr>
                <td>
                    <c:out value="${prescription.id}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=view-user&user_id=${prescription.userId}"
                       style="display: block">
                        <div style="width: 100%; height: 100%">
                            <c:out value="${users[prescription.userId].firstName} ${users[prescription.userId].lastName}"/>
                        </div>
                    </a>
                </td>
                <td>
                    <c:out value="${users[prescription.userId].address}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/controller?command=view-item&id=${prescription.drugId}"
                       style="display: block">
                        <div style="width: 100%; height: 100%">
                            <c:out value="${items[prescription.drugId].label} ${items[prescription.drugId].dosage}"/>
                        </div>
                    </a>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${prescription.status eq 'requested'}">
                            <span class="badge badge-info"><fmt:message key="text.prescription.status.requested"/></span>
                        </c:when>
                        <c:when test="${prescription.status eq 'approved'}">
                            <span class="badge badge-primary"><fmt:message key="text.prescription.status.approved"/></span>
                        </c:when>
                        <c:when test="${prescription.status eq 'overdue'}">
                            <span class="badge badge-success"><fmt:message key="text.prescription.status.overdue"/></span>
                        </c:when>
                        <c:when test="${prescription.status eq 'rejected'}">
                            <span class="badge badge-default"><fmt:message key="text.prescription.status.rejected"/></span>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <javatime:format value="${prescription.validUntil}" style="MS" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
