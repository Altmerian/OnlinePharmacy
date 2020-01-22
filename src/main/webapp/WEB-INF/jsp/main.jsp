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
    <main class="main">
        <h5>HERE YOU WILL SEE NEWS AND OTHER INFORMATION<h5>
    </main>
    <footer class="footer">
		<jsp:include page="/WEB-INF/jsp/footer.jsp" />
	</footer>
</body>
</html>