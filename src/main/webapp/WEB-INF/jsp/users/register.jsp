<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctg" uri="customtags" %>

<html><head><title>Login</title></head>
<body>
<ctg:header/>
<h3>Please, fill up the fields:</h3>
<div>
    <c:if test="${sessionScope.success_message}">
        <div>
            Registration successful, signed up as: <c:out value="${sessionScope.user_name}"/>
            <c:set var="success_message" value="false" scope="session"/>
            <c:remove var="user_id" scope="session"/>
        </div>
    </c:if>
    <c:if test="${sessionScope.error_message}">
        <div>
            Login or email already exists
            <c:set var="error_message" value="false" scope="session"/>
        </div>
    </c:if>

    <form action="Controller" method="POST">
        <input type="hidden" name="command" value="register"/>
        <div>
            Username: <input type="text" id="username" name="username" pattern="[a-zA-Z0-9]+" required>
        </div>

        <div class="form-group">
            E-mail: <input type="email" id="email" name="email" required>
        </div>

        <div>
            Password: <input type="password" id="password" name="password" pattern=".{4,}" required>
        </div>

        <div>
            First Name: <input type="text" id="first_name" name="first_name">
        </div>

        <div>
            Last Name: <input type="text" id="last_name" name="last_name">
        </div>

        <div>
            Address: <input type="text" id="address" name="address" value="${requestScope.address}">
        </div>

        <div>
            <!-- Button -->
                <input type="submit" value="Register" />
        </div>
    </form>
</div>
</body>
</html>