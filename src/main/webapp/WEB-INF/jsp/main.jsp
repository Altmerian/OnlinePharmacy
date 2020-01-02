<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html><head><title>Welcome</title></head>
<body>
    <h3>Welcome</h3>
    <hr/>
    <c:choose>
    	<c:when test="${user != null}">
    		 ${user}, hello!
    	</c:when>
    	<c:otherwise>
    		<c:redirect url="/WEB-INF/jsp/login.jsp"/>
    	</c:otherwise>
    </c:choose>
    <hr/>
    <br/>
    <jsp:include page="/WEB-INF/jsp/shopping-cart.jsp"/>
    <hr/>
    <br/>
    <a href="controller?command=logout">Logout</a>
</body></html>