<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Online Pharmacy</title></head>
<body>
    <ctg:header/>
    <c:choose>
    	<c:when test="${sessionScope.user != null}">
    		 <fmt:message key="message.welcome"/>, ${sessionScope.user.login} <br/>
    		 <a href="/controller?command=logout">Logout</a>
    	</c:when>
    	<c:otherwise>
    		<a href="/loginJsp">Login</a> <br/>
    		<a href="/registerJsp">Register</a>
    	</c:otherwise>
    </c:choose>
    <hr/>
    <br/>
    <jsp:include page="/WEB-INF/jsp/shopping-cart.jsp"/>
    <hr/>
    <br/>
     <c:if test="${sessionScope.success_message}">
        <div>
            <fmt:message key="message.register.success"/>: <c:out value="${sessionScope.user_name}"/>
            <c:set var="success_message" value="false" scope="session"/>
        </div>
    </c:if>

</body></html>