package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code AddItemCommand} is an implementation of {@see Command}
 * for adding a chosen item to shopping cart.
 */
public class AddItemToCart implements Command {

    private static OrderService orderService = new OrderServiceImpl(); //todo

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        if (request.getSession().getAttribute(Parameter.USER) != null) {
            ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
            long itemId = Long.parseLong(request.getParameter(Parameter.ITEM_ID));
            int quantity = Integer.parseInt(request.getParameter(Parameter.QUANTITY));
            try {
                if (orderService.addItemToCart(itemId, quantity)) {
                    session.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
                } else {
                    session.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
                }
            } catch (ServiceException e) {
                throw LOGGER.throwing(Level.ERROR, new CommandException(e.getMessage()));
            }
            return new Path(false, request.getHeader(Parameter.REFERER));
        } else {
            request.getSession().setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            return new Path(false, JspPage.LOGIN.getPath());
        }
    }
}

