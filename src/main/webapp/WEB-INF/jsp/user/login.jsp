<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Login</title></head>
<body>
    <header>
        <ctg:header/>
    </header>
    <c:if test="${sessionScope.error_message}">
        <div>
            <fmt:message key="message.login.required"/>
            <c:set var="error_message" value="false" scope="session"/>
        </div>
    </c:if>
    <h4><fmt:message key="message.login"/></h4>
    <form name="loginForm" method="POST" action="/controller">
        <input type="hidden" name="command" value="login" />
        <fmt:message key="text.username"/>:<br/>
        <input type="text" name="login" value=""/>
        <br/><fmt:message key="text.password"/>:<br/>
        <input type="password" name="password" value=""/>
        <br/><br/>
        <c:if test="${sessionScope.login_failed}">
            <div>
                <fmt:message key="message.login.error"/>
            </div>
        </c:if>
        <br/>
        <input type="submit" value="<fmt:message key="button.name.login"/>"/>
    </form><hr/>
    <footer class="footer">
        <jsp:include page="/WEB-INF/jsp/footer.jsp" />
    </footer>
</body>
</html>