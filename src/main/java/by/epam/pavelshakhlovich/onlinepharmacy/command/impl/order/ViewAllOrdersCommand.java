package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.OrderStatus;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code ViewAllOrdersCommand} is an implementation of {@see Command}
 * for viewing different types of submitted orders for all users by admin or manager
 */
public class ViewAllOrdersCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();
    private static final String EMPTY = "";

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<String> orderStatusList = new ArrayList<>();
        int limit = Integer.parseInt(request.getParameter(Parameter.LIMIT));
        int offset = (Integer.parseInt(request.getParameter(Parameter.PAGE_NUMBER)) - 1) * limit;
        for (OrderStatus orderStatus : OrderStatus.values()) {
            orderStatusList.add(orderStatus.getStatus());
        }
        try {
            List<Order> orderList = orderService.selectAllOrdersByStatus(orderStatusList, limit, offset);
            int numberOfOrders = orderService.countOrdersByStatus(orderStatusList);
            request.setAttribute(Parameter.ORDERS, orderList);
            request.setAttribute(Parameter.NUMBER_OF_ORDERS, numberOfOrders);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.VIEW_ALL_ORDERS.getPath());


    }
}
