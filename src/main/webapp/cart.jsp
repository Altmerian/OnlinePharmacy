<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="by.epam.pavelshakhlovich.onlinepharmacy.entity.Cart"%>
<html>
<head><title>Cart JSP</title></head>
<body>
<h1>User cart</h1>
<% Cart cart = (Cart) session.getAttribute("cart"); %>
<p> Name <%= cart.getName() %> </p>
<p> Count <%= cart.getQuantity() %> </p>
</body>
</html>