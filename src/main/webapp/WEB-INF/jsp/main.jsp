<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html>
	<head>
		<title>Online Pharmacy</title>
	</head>
	<body>
		<header>
            <ctg:header/>
		</header>
		<c:choose>
			<c:when test="${sessionScope.user != null}">
				<fmt:message key="text.welcome"/>, ${sessionScope.user.login} <br/>
				<a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="link.logout"/></a>
			</c:when>
			<c:otherwise>
				<a href="${pageContext.request.contextPath}/login"><fmt:message key="link.login"/></a>
				<br/>
				<a href="${pageContext.request.contextPath}/register"><fmt:message key="link.register"/></a>
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
	</body>
</html>