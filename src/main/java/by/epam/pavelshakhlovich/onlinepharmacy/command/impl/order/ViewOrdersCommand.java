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
import java.util.List;

/**
 * Class {@code ViewOrdersCommand} is an implementation of {@see Command}
 * for viewing different types of submitted orders for given user
 */
public class ViewOrdersCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();
    private static UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        User user = (User)request.getSession().getAttribute(Parameter.USER);
        String userIdString = request.getParameter(Parameter.USER_ID);
        long userId;
        if (userIdString == null || userIdString.isEmpty()) {
            userId = user.getId();
        } else {
            userId = Long.parseLong(request.getParameter(Parameter.USER_ID));
        }
        List<Order> orderList;
        try {
            orderList = orderService.selectOrdersByUserId(user, userId);
            request.setAttribute(Parameter.ORDERS, orderList);
            User orderOwner = userService.selectUserById(user, userId);
            request.setAttribute(Parameter.ORDER_OWNER, orderOwner);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.VIEW_ORDERS.getPath());
    }
}
