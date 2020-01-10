<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<html><head><title>Shopping Cart</title></head>
<body>
    <h4><fmt:message key="title.shoppingCart"/>:</h4>
    <hr/>
    <c:choose>
    	<c:when test="${current_shopping_cart != null and sessionScope.user != null}">
    		<fmt:message key="text.totalCount"/> = ${current_shopping_cart.totalCount}<br>
    		<fmt:message key="text.products"/>: <br>
    		<c:forEach var="item" items="${current_shopping_cart.items}">
    			${item.drugId}-&gt;${item.count}<br>
    		</c:forEach>
    		<hr/>
            <a href="controller?command=clear_shopping_cart">Clear</a>
    	</c:when>
    	<c:otherwise>
    		<fmt:message key="title.shoppingCart.isEmpty"/>
    	</c:otherwise>
    </c:choose>
</body>
</html>