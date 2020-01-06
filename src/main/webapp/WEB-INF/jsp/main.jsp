<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctg" uri="customtags" %>

<html><head><title>Online Pharmacy</title></head>
<body>
    <ctg:header/>
    <c:choose>
    	<c:when test="${sessionScope.user != null}">
    		 Welcome, ${sessionScope.user} <br/>
    		 <a href="controller?command=logout">Logout</a>
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
</body></html>