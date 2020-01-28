package by.epam.pavelshakhlovich.onlinepharmacy.command.util;


import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtil {
    private SessionUtil() {
    }

    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest request) {
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(Parameter.SHOPPING_CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            setCurrentShoppingCart(request, shoppingCart);
        }
        return shoppingCart;
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest request) {
        return request.getSession().getAttribute(Parameter.SHOPPING_CART) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest request, ShoppingCart shoppingCart) {
        request.getSession().setAttribute(Parameter.SHOPPING_CART, shoppingCart);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(Parameter.SHOPPING_CART);
        WebUtil.setCookie(Cookie.SHOPPING_CART.getName() + "-" + request.getSession().getAttribute(Parameter.USER_NAME),
                null, 0, response);

    }

    public static javax.servlet.http.Cookie findShoppingCartCookie(HttpServletRequest request) {
        String cookieName = Cookie.SHOPPING_CART.getName() + "-" + request.getSession().getAttribute(Parameter.USER_NAME);
        return WebUtil.findCookie(request, cookieName);
    }

    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletRequest request, HttpServletResponse response) {
        String cookieName = Cookie.SHOPPING_CART.getName() + "-" + request.getSession().getAttribute(Parameter.USER_NAME);
        WebUtil.setCookie(cookieName, cookieValue, Cookie.SHOPPING_CART.getAge(), response);
    }
}
