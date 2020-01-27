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
 * Class {@code RemoveItemFromOrderCommand} is animplementation of {@see Command}
 * for removing from the current order an earlier added item
 */
public class RemoveItemFromCart implements Command { //todo

    private static OrderService orderService = new OrderServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long itemId = Long.parseLong(request.getParameter(Parameter.ITEM_ID));
        int quantity = Integer.parseInt(request.getParameter(Parameter.QUANTITY));
        ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
        shoppingCart.removeItem(itemId, quantity);
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
