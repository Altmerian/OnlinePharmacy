package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Class {@code CreateOrderCommand} is an implementation of {@see Command}
 * for creating an order from current shopping cart
 */
public class CreateOrderCommand implements Command {

    private static OrderService orderService = new OrderServiceImpl();
    private static ItemService itemService = new ItemServiceImpl();

    @Override
    @SuppressWarnings("unchecked")
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Order order = new Order();
        User user = (User) session.getAttribute(Parameter.USER);
        order.setUser(user);
        order.setUserId(user.getId());
        double amount = Double.parseDouble(request.getParameter(Parameter.TOTAL_AMOUNT));
        order.setAmount(BigDecimal.valueOf(amount));
        Map<Item, Integer> cartItems;
        Object cartItemsAttribute = session.getAttribute(Parameter.CART_ITEMS);
        if (cartItemsAttribute instanceof Map) {
            cartItems = (Map<Item, Integer>) cartItemsAttribute;
        } else {
            throw LOGGER.throwing(Level.ERROR, new CommandException("Illegal 'CART_ITEMS' argument"));
        }
        order.setItems(cartItems);
        try {
            if (orderService.submitOrder(order)) {
                SessionUtil.clearCurrentShoppingCart(request, response);
                Order addedOrder = orderService.getLastAddedOrder(user.getId());
                return new Path(true, "/controller?command=view-order&id=" + addedOrder.getId());
            } else {
                return new Path(false, request.getHeader(Parameter.REFERER));
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
    }
}
