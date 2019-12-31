<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><head><title>Shopping Cart</title></head>
<body>
    <h3>Shopping Cart</h3>
    <hr/>
    Total count = ${current_shopping_cart.totalCount}<br>
    Products: <br>${current_shopping_cart.view}
    <hr/>
    <a href="controller?command=clear_shopping_cart">Clear</a>
</body></html>