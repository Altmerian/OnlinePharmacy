<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html><head><title>Shopping Cart</title></head>
<body>
    <h3>Shopping Cart</h3>
    <hr/>
    <c:choose>
    	<c:when test="${current_shopping_cart != null}">
    		Total count = ${current_shopping_cart.totalCount}<br>
    		Products: <br>
    		<c:forEach var="item" items="${current_shopping_cart.items}">
    			${item.drugId}-&gt;${item.count}<br>
    		</c:forEach>
    	</c:when>
    	<c:otherwise>
    		Shopping cart is empty
    	</c:otherwise>
    </c:choose>
    <hr/>
    <a href="controller?command=clear_shopping_cart">Clear</a>
</body></html>