<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Shopping Cart</title></head>
<body>
<header>
    <ctg:header/>
</header>
<h4><fmt:message key="title.shoppingCart"/>:</h4>
<hr/>
<c:choose>
    <c:when test="${shopping_cart != null and sessionScope.user != null}">
        <fmt:message key="text.totalCount"/> = ${shopping_cart.totalCount}<br>
        <fmt:message key="text.products"/>: <br>
        <c:forEach var="item" items="${shopping_cart.items}">
            ${item.drugId}-&gt;${item.count}<br>
        </c:forEach>
        <hr/>
        <a href="${pageContext.request.contextPath}/controller?command=clear_shopping_cart">Clear</a>
    </c:when>
    <c:otherwise>
        <fmt:message key="title.shoppingCart.isEmpty"/>
    </c:otherwise>
</c:choose>
<footer class="footer">
    <jsp:include page="/WEB-INF/jsp/footer.jsp" />
</footer>
</body>
</html>