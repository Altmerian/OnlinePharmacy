<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Login</title></head>
<body>
<ctg:header/>
<h4>Please, fill up the fields:</h4>
<div>
    <form action="controller" method="POST">
        <input type="hidden" name="command" value="register"/>
        <div>
            Login:   <input type="text" id="login" name="login" pattern="[a-zA-Z0-9]+" required> <br/>
        </div>

        <div class="form-group">
            E-mail:     <input type="email" id="email" name="email" required> <br/>
        </div>

        <div>
            Password:   <input type="password" id="password" name="password" pattern=".{4,}" required> <br/>
        </div>

        <div>
            First Name: <input type="text" id="first_name" name="first_name"> <br/>
        </div>

        <div>
            Last Name:  <input type="text" id="last_name" name="last_name"> <br/>
        </div>

        <div>
            Address:    <input type="text" id="address" name="address" value="${requestScope.address}"> <br/>
        </div>

        <div>
            <!-- Button -->
                <input type="submit" value="Register" />
        </div>
    </form>

     <c:if test="${sessionScope.success_message}">
        <div>
            <fmt:message key="message.register.success"/>: <c:out value="${sessionScope.user_name}"/>
            <c:set var="success_message" value="false" scope="session"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div>
            <fmt:message key="message.register.error"/>
            <c:set var="error_message" value="false" scope="session"/>
        </div>
    </c:if>
</div>
</body>
</html>