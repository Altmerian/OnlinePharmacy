package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.OrderStatus;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Class {@code PayOrderCommand} is an implementation of {@see Command}
 *  to pay current order
 */
public class PayOrderCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long orderId = Long.parseLong(request.getParameter(Parameter.ORDER_ID));
        try {
            if(orderService.updateOrderStatus(OrderStatus.PAYMENT_CONFIRMATION.getStatus(), orderId)){
                request.getSession().setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                request.getSession().setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
