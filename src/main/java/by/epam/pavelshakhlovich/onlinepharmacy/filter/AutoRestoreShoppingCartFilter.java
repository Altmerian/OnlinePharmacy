package by.epam.pavelshakhlovich.onlinepharmacy.filter;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtils;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCartSerializer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "AutoRestoreShoppingCartFilter", urlPatterns = "/*")
public class AutoRestoreShoppingCartFilter implements Filter {
    private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "shopping_card_deserialization_done";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null) {
            if (!SessionUtils.isCurrentShoppingCartCreated(request)) {
                Cookie cookie = SessionUtils.findShoppingCartCookie(request);
                if (cookie != null) {
                    ShoppingCart shoppingCart = ShoppingCartSerializer.shoppingCartFromString(cookie.getValue());
                    SessionUtils.setCurrentShoppingCart(request, shoppingCart);
                }
            }
            request.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
        } else {
            ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(request);
            String cookieValue = ShoppingCartSerializer.shoppingCartToString(shoppingCart);
            SessionUtils.updateCurrentShoppingCartCookie(cookieValue, response);
        }
        chain.doFilter(req, resp);
    }

}
