package by.epam.pavelshakhlovich.onlinepharmacy.command.util;


import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.util.Constant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtils {
    private SessionUtils() {
    }

    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest request) {
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(Parameter.CURRENT_SHOPPING_CART);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            setCurrentShoppingCart(request, shoppingCart);
        }
        return shoppingCart;
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest request) {
        return request.getSession().getAttribute(Parameter.CURRENT_SHOPPING_CART) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest request, ShoppingCart shoppingCart) {
        request.getSession().setAttribute(Parameter.CURRENT_SHOPPING_CART, shoppingCart);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(Parameter.CURRENT_SHOPPING_CART);
        WebUtils.setCookie(Constant.Cookie.SHOPPING_CART.getName(), null, 0, response);
    }

    public static Cookie findShoppingCartCookie(HttpServletRequest request) {
        return WebUtils.findCookie(request, Constant.Cookie.SHOPPING_CART.getName());
    }

    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse response) {
        WebUtils.setCookie(Constant.Cookie.SHOPPING_CART.getName(), cookieValue,
                Constant.Cookie.SHOPPING_CART.getAge(), response);
    }
}
