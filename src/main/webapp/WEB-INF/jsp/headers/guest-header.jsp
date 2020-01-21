<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<h4>Guest header</h4>
<form class="navbar-form navbar-right" action="controller" method="post" role="search">
    <div class="form-group">
        <input type="hidden" name="command" value="login"/>
        <input type="hidden" name="from" value="${pageContext.request.requestURI}"/>
        <input type="text" class="form-control" placeholder="<fmt:message key="text.username"/>"
               name="login">
    </div>
    <div class="form-group">
        <input type="password" class="form-control" placeholder="<fmt:message key="text.password"/>"
               name="password">
    </div>
    <input class="btn btn-default" type="submit"
           value="<fmt:message key="button.name.login"/>">
</form>
<form class="navbar-form navbar-right" action="${pageContext.request.contextPath}/register">
    <button type="submit" class="btn btn-link"><fmt:message
            key="link.register"/></button>
</form>
<hr/>
