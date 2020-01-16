package by.epam.pavelshakhlovich.onlinepharmacy.filter;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCartSerializer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "AutoRestoreShoppingCartFilter", urlPatterns = "/*")
public class AutoRestoreShoppingCartFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getSession().getAttribute(Parameter.USER) != null){
            ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
            String cookieValue = ShoppingCartSerializer.shoppingCartToString(shoppingCart);
            SessionUtil.updateCurrentShoppingCartCookie(cookieValue, request, response);
        }
        chain.doFilter(req, resp);
    }

}
