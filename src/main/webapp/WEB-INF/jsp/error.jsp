<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>
<%@ taglib prefix="ctg" uri="customtags" %>

<html><head><title>Error Page</title></head>
<body>
<header>
    <ctg:header/>
</header>
<h1>Opps...</h1>
<table width = "80%" border = "1">
 <tr valign = "top">
    <td width = "40%"><b>Error:</b></td>
    <td>
        <c:choose>
        <c:when test="${pageContext.errorData.statusCode == 500}">
            Internal Server Error
        </c:when>
        <c:when test="${pageContext.errorData.statusCode == 404}">
            Page not found
        </c:when>
        <c:otherwise>
            </br>
            ${pageContext.exception}
        </c:otherwise>
    </td>
 </tr>
 <tr valign = "top">
    <td><b>URI:</b></td>
    <td>${pageContext.errorData.requestURI}</td>
 </tr>

 <tr valign = "top">
    <td><b>Status code:</b></td>
    <td>${pageContext.errorData.statusCode}</td>
 </tr>

 <tr valign = "top">
     <td><b>Message:</b></td>
     <td>${pageContext.exception.message}</td>
 </tr>

 <tr valign = "top">
    <td><b>Stack trace:</b></td>
    <td>
       <c:forEach var = "trace"
            items = "${pageContext.exception.stackTrace}">
            <p>${trace}</p>
       </c:forEach>
    </td>
 </tr>
</table>
<footer class="footer">
  <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body></html>