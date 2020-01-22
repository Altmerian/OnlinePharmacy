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
    <c:set var="count" value="0" scope="page"/>
    <c:set var="total_quantity" value="0" scope="page"/>
    <c:set var="total_amount" value="0" scope="page"/>
    <div class="container-fluid">
        <c:if test="${sessionScope.success_message}">
            <div class="alert alert-success">
                <span>
                    <fmt:message key="message.quantity.change.success"/>
                 </span>
                <c:set var="success_message" value="false" scope="session"/>
            </div>
        </c:if>
        <c:if test="${sessionScope.error_message}">
            <div class="alert alert-danger">
                <fmt:message key="message.quantity.change.error"/>
                <c:set var="error_message" value="false" scope="session"/>
            </div>
        </c:if>
        <table class="table">
            <thead>
            <tr>
                <th>
                    #
                </th>
                <th>
                    <fmt:message key="text.drug"/>
                </th>
                <th>
                    <fmt:message key="text.quantity"/>
                </th>
                <th>
                    <fmt:message key="text.price"/>
                </th>
                <th>
                    <fmt:message key="text.byPrescription"/>
                </th>
                <th></th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${sessionScope.shopping_cart.items}">
                <c:set var="count" value="${count+1}"/>
                <c:set var="total_quantity" value="${total_quantity + entry.value}"/>
                <c:set var="total_amount" value="${total_amount + entry.value * entry.key.price}"/>
                <tr>
                    <td>
                            ${count}
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/Controller?command=view-item&id=${entry.key.id}">
                            <b>${entry.key.label}</b> ${entry.key.dosage}, ${entry.key.volume} ${entry.key.volumeType}
                        </a>
                    </td>
                    <td>
                        <form class="form-inline" action="Controller" method="post">
                            <input type="hidden" name="command" value="add-item-to-order"/>
                            <input type="hidden" name="change_quantity" value="true"/>
                            <input type="hidden" name="old_quantity" value="${entry.value}"/>
                            <input type="hidden" name="item_id" value="${entry.key.id}"/>
                            <div class="form-group">
                                <input type="number" class="form-control" min="1" max="999" name="quantity"
                                       value="${entry.value}"/>
                            </div>
                            <button type="submit" class="btn btn-warning" value=""><fmt:message
                                    key="local.button.change"/></button>
                        </form>

                    </td>
                    <td>
                            ${entry.key.price}
                    </td>
                    <td>
                        <c:if test="${entry.key.byPrescription}">
                            <span class="glyphicon glyphicon-ok"></span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${entry.key.imagePath != null}">
                            <img src="${entry.key.imagePath}"/>
                        </c:if>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="remove-item-from-cart"/>
                            <input type="hidden" name="item_id" value="${entry.key.id}"/>
                            <input type="hidden" name="order_id" value="${sessionScope.shopping_cart.id}"/>
                            <input type="submit" class="btn btn-danger" value="<fmt:message key="button.delete"/> "/>
                        </form>
                    </td>
                </c:forEach>
            </tr>
            </tbody>
            <tfoot>
                <td></td>
                <th>
                    <fmt:message key="text.total"/>
                </th>
                <th>
                    ${total_quantity}
                </th>
                <th>
                    ${total_amount}
                </th>
                <th></th>
                <th></th>
                <th></th>
            </tfoot>
        </table>
        <c:if test="${count != 0}">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="submit-order"/>
            <input type="hidden" name="order_id" value="${sessionScope.shopping_cart.id}"/>
            <div class="col-sm-offset-4 col-sm-4">
                <input type="submit" style="padding: 10px" class="btn btn-success btn-lg btn-block btn-huge"
                       value="<fmt:message key="button.order.submit"/> "/>
            </div>
        </form>
        </c:if>

    </div>
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