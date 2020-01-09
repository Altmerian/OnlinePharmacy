<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<h4>User header</h4> <a href="/controller">Home</a>
<div class="change-locale">
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="ru_RU"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
        <input type="submit" value="<fmt:message key="locbutton.name.ru"/>"/>
    </form>
    <form action="/controller" method="post">
        <input type="hidden" name="command" value="change-locale"/>
        <input type="hidden" name="locale" value="en_US"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
        <input type="submit" value="<fmt:message key="locbutton.name.en"/>">
    </form>
</div>
<hr/>