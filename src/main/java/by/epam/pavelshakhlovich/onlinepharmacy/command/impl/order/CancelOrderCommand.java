package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order;

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
import javax.servlet.http.HttpSession;

/**
 *  Class {@code CancelOrderCommand} is an implementation of {@see Command}
 *  for cancel current order and delete it from data source
 */
public class CancelOrderCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long orderId = Long.parseLong(request.getParameter(Parameter.ORDER_ID));
        HttpSession session = request.getSession();
        try {
            if (orderService.cancelOrder(orderId)) {
                session.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
                return new Path(false, "/controller?command=view-orders");
            } else {
                session.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
                return new Path(false, request.getHeader(Parameter.REFERER));
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
    }
}
