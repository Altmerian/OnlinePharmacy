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
import by.epam.pavelshakhlovich.onlinepharmacy.service.UserService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Map;


/**
 * Class {@code ViewOrderCommand} is a non-guest implementation of {@see Command}
 * for viewing given order
 */

public class ViewOrderCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();
    private static UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long orderId = Long.parseLong(request.getParameter(Parameter.ID));
        User user = (User) request.getSession().getAttribute(Parameter.USER);
        Map<Timestamp, String> orderEvents;
        try {
            orderEvents = orderService.getOrderEvents(orderId);
            Order order = orderService.selectOrderById(orderId, user);
            if (order == null) {
                return new Path(false, request.getHeader(Parameter.REFERER));
            } else {
                User orderOwner = userService.selectUserById(user, order.getUserId());
                request.setAttribute(Parameter.ORDER_EVENTS, orderEvents);
                request.setAttribute(Parameter.ORDER, order);
                request.setAttribute(Parameter.ORDER_OWNER, orderOwner);
                return new Path(true, JspPage.VIEW_ORDER.getPath());
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
    }
}
