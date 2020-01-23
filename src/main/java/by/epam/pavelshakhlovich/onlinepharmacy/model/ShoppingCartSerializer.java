package by.epam.pavelshakhlovich.onlinepharmacy.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ShoppingCartSerializer {
    private static final Logger LOGGER = LogManager.getLogger();

    public static String shoppingCartToString(ShoppingCart shoppingCart) {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<Long, Integer> item : shoppingCart.getItems().entrySet()) {
            res.append(item.getKey()).append("-").append(item.getValue()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    public static ShoppingCart shoppingCartFromString(String cookieValue) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = cookieValue.split("\\|");
        for (String item : items) {
            String[] data = item.split("-");
            try {
                Long itemId = Long.parseLong(data[0]);
                int count = Integer.parseInt(data[1]);
                shoppingCart.addItem(itemId, count);
            } catch (RuntimeException e) {
                LOGGER.error("Wrong cookie format:", e);
            }
        }
        return shoppingCart;
    }
}
