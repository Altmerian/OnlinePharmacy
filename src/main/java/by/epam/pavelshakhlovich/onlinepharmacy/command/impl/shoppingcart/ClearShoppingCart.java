package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Deletes all items from shopping cart
 */
public class ClearShoppingCart implements Command {
    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) {
        SessionUtil.clearCurrentShoppingCart(request, response);
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
