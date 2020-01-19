<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="local"/>

<h4>Admin header</h4>
<div>
    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/сontroller?command=view-orders">
            <fmt:message key="link.orders.view.own"/></a>
        <span>
            <a href="${pageContext.request.contextPath}/сontroller?command=view-all-orders&limit=20&page_number=1">
            <fmt:message key="link.orders.view.all"/></a>
        </span>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/сontroller?command=view-prescriptions" class="current">
            <fmt:message key="link.prescriptions"/></a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/register"><fmt:message
                    key="link.register.new.user"/> </a>
        <span>
            <a href="${pageContext.request.contextPath}/controller?command=view-all-users&page_number=1&limit=20">
            <fmt:message key="link.manage.users"/> </a>
        </span>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/controller?command=view-catalog&page_number=1&limit=10">
                <fmt:message key="link.view.catalog"/>
            </a>
        </span>
            <a href="${pageContext.request.contextPath}/controller?command=view-add-item">
            <fmt:message key="button.item.add"/></a>
        </span>
        </li>
    </ul>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="logout"/>
        <button type="submit" class="btn btn-link"><fmt:message
                key="link.logout"/></button>
    </form>
    <form action="controller" method="get">
            <input type="hidden" name="command" value="view-user"/>
            <input type="hidden" name="user_id" value="${sessionScope.user.id}"/>
        </div>
        <button type="submit" class="btn btn-link"><fmt:message key="link.profile"/></button>
    </form>
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
    </form></span>
</div>
<hr/>