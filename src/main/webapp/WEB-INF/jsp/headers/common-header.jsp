<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html>
<head><title>Common header</title></head>
<body>
<a href="${pageContext.request.contextPath}/controller">
    <fmt:message key="link.home"/>
</a>
<span class="change-locale">

    <span>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="ru_RU"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}?${pageContext.request.queryString}"/>
        <input type="submit" value="<fmt:message key="button.name.ru"/>"/>
    </form></span>
    <span>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="en_US"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}?${pageContext.request.queryString}"/>
        <input type="submit" value="<fmt:message key="button.name.en"/>">
    </form></span>
</span>
<hr/>
</body>
</html>
