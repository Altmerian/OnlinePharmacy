package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code RemoveItemFromCart} is an implementation of {@see Command}
 * for removing from the current shopping cart an earlier added item
 */
public class RemoveItemFromCart implements Command {

    private static OrderService orderService = new OrderServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long itemId = Long.parseLong(request.getParameter(Parameter.ITEM_ID));
        ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
        shoppingCart.removeItem(itemId);
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
