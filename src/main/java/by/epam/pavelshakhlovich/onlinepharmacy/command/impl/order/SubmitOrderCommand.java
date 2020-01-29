package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
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
import javax.servlet.http.HttpSession;

/**
 * Class {@code SubmitOrderCommand} is an implementation of {@see Command}
 * for submitting the current order from shopping cart
 */
public class SubmitOrderCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Order order = new Order();
        User user = (User) session.getAttribute(Parameter.USER);
        order.setUser(user);
        order.setUserId(user.getId());
        //todo
        try {
            if (orderService.submitOrder(order)) {
                request.getSession().setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                request.getSession().setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, "/controller?command=view-order&order_id=" + order.getId());
    }
}
