package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;

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
        boolean removeAll = Boolean.parseBoolean(request.getParameter(Parameter.REMOVE_ALL));
        try {
            orderService.removeItemFromCart(itemId, removeAll);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e.getMessage()));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
