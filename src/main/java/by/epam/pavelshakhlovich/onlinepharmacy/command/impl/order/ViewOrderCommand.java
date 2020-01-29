package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Class {@code ViewOrderCommand} is a non-guest implementation of {@see Command}
 * for viewing given order
 */

public class ViewOrderCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long orderId = Long.parseLong(request.getParameter(Parameter.ID));
        User user = (User) request.getSession().getAttribute(Parameter.USER);
        //todo

        try {
            Order order = orderService.selectOrderById(orderId, user);
            if (order == null) {
                request.getSession().setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
                return new Path(true,"");

            } else {
                request.setAttribute(Parameter.ORDER, order);
                return new Path(true, JspPage.VIEW_ORDER.getPath());
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
    }
}
