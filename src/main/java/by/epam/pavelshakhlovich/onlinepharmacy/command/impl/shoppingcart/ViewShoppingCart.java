package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code ViewShoppingCart} is a non-guest implementation of {@see Command}
 * for viewing shopping cart of logged in user
 */
public class ViewShoppingCart implements Command {

    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
        Map<Item, Integer> cartItems = new HashMap<>();
        try {
            for (Map.Entry<Long, Integer> item : shoppingCart.getItems().entrySet() ) {
                cartItems.put(itemService.selectItemById(item.getKey()), item.getValue());
            }
            session.setAttribute(Parameter.CART_ITEMS, cartItems);
        } catch ( ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.SHOPPING_CART.getPath());
    }
}
