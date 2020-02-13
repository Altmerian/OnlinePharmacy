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
<div class="container mt-2">
    <!-- Messages -->
    <c:if test="${requestScope.prescriptions == null}">
        <div class="alert alert-info" role="alert">
            <fmt:message key="message.prescriptions.null"/>
        </div>
    </c:if>
    <c:if test="${success_request_message}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="message.prescription.request.success"/>
        </div>
        <c:set var="success_request_message" value="false" scope="session"/>
    </c:if>
    <c:if test="${error_request_message}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="message.prescription.request.error"/>
        </div>
        <c:set var="error_request_message" value="false" scope="session"/>
    </c:if>
    <div>
        <a href="${header.Referer}"><i class="fas fa-arrow-left"></i><fmt:message key="link.back"/></a>
    </div>
    <!-- Orders -->
    <div class="container col-sm-6 text-center mt-1">
        <h5><fmt:message key="title.customer"/>: 
            <c:out value="#${prescription_owner.id} ${prescription_owner.firstName} ${prescription_owner.lastName} 
            (${prescription_owner.login})"/></h5>
    </div>
    <table class="table table-striped table-bordered">
        <thead>
            <th>
                #ID
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
            <th></th>
        </thead>
        <tbody>
        <c:forEach var="prescription" items="${requestScope.prescriptions}">
            <tr>
                <td>
                    <c:out value="${prescription.id}"/>
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
                            <span class="badge badge-warning"><fmt:message key="text.prescription.status.overdue"/></span>
                        </c:when>
                        <c:when test="${prescription.status eq 'rejected'}">
                            <span class="badge badge-default"><fmt:message key="text.prescription.status.rejected"/></span>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <javatime:format value="${prescription.validUntil}" style="MS" />
                </td>
                <td>
                    <c:if test="${prescription.status eq 'rejected' or prescription.status eq 'overdue'}">
                    <div class="col">
                        <form class="form-inline" action="controller" method="POST">
                            <input type="hidden" name="command" value="request-prescription"/>
                            <input type="hidden" name="prescription_id" value="${prescription.id}"/>
                            <input type="hidden" name="doctor_id" value="${prescription.doctorId}"/>
                            <input type="hidden" name="user_id" value="${user.id}"/>
                            <input type="hidden" name="drug_id" value="${prescription.drugId}"/>
                            <input type="submit" class="btn btn-success btn-sm"
                                   value="<fmt:message key="button.prescription.request"/>"/>
                        </form>
                    </div>
                    </c:if>
                    <div class="col">
                        <form class="form-inline" action="controller" method="POST">
                            <input type="hidden" name="command" value="cancel-prescription"/>
                            <input type="hidden" name="prescription_id" value="${prescription.id}"/>
                            <input type="submit" class="btn btn-danger btn-sm"
                                   value="<fmt:message key="button.prescription.cancel"/>"/>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
