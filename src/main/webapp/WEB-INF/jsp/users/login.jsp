<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Login</title></head>
<body>
    <ctg:header/>
    <h4><fmt:message key="message.login"/></h4>
    <form name="loginForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="login" />
        <fmt:message key="text.username"/>:<br/>
        <input type="text" name="login" value=""/>
        <br/><fmt:message key="text.password"/>:<br/>
        <input type="password" name="password" value=""/>
        <br/><br/>
        ${errorLoginPassMessage}
        <br/>
        <input type="submit" value="<fmt:message key="button.name.login"/>"/>
    </form><hr/>
</body>
</html>