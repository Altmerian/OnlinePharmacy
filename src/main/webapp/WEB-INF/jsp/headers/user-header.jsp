<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<h4>User header</h4>
<div>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/controller?command=view-orders">
            <fmt:message key="link.orders.view.own"/></a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/controller?command=view-prescriptions" class="current">
            <fmt:message key="link.prescriptions"/></a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/controller?command=view-catalog&page_number=1&limit=10">
                <fmt:message key="link.view.catalog"/>
            </a>
        </li>
    </ul>
    <hr>
    <c:if test="${sessionScope.success_message}">
        <div>
            <fmt:message key="message.register.success"/>: <c:out value="${sessionScope.user_name}"/>
            <c:set var="success_message" value="false" scope="session"/>
        </div>
    </c:if>
    <fmt:message key="text.welcome"/>, ${sessionScope.user.login}
    <form action="controller" method="post" id=logout>
        <input type="hidden" name="command" value="logout"/>
    </form>
    <form action="controller" method="get" id="profile">
        <input type="hidden" name="command" value="view-user"/>
        <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
    </form>
    <div class="btn-group">
        <button type="submit" class="btn btn-link" form="profile"><fmt:message key="link.profile"/></button>
        <button type="submit" class="btn btn-link" form="logout"><fmt:message
                key="link.logout"/></button>
    </div>
    <br>
    <form role="search" action="controller" method="get">
        <input type="hidden" name="command" value="search-item">
        <input type="hidden" name="page_number" value="1"/>
        <input type="hidden" name="limit" value="10"/>
        <div class="form-group">
            <input type="text" name="search" class="form-control" style="width: 300px;"
                    placeholder="<fmt:message key="text.enter.drug.name"/> "/>
            <input class="btn btn-default" type="submit" value="<fmt:message key="text.search"/>"/>
        </div>
    </form>
    <form class="navbar-form navbar-right" action="controller" method="get">
        <input type="hidden" name="command" value="view-shopping-cart"/>
        <input class="btn btn-default" type="submit"
        value="<fmt:message key="link.shopping.cart"/>(${shopping_cart.totalCount})"/>
    </form>
</div>
<hr/>