<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Common header</title></head>
<body>
<a href="/controller"><fmt:message key="link.home"/></a>
<br/><br/>
<div class="change-locale">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="ru_RU"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}?${pageContext.request.queryString}"/>
        <input type="submit" value="<fmt:message key="button.name.ru"/>"/>
    </form>
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="locale" value="en_US"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}?${pageContext.request.queryString}"/>
        <input type="submit" value="<fmt:message key="button.name.en"/>">
    </form>
</div>
<hr/>
</body></html>
