package by.epam.pavelshakhlovich.onlinepharmacy.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShoppingCartSerializer {
    private static final Logger LOGGER = LogManager.getLogger();

    public static String shoppingCartToString(ShoppingCart shoppingCart) {
        StringBuilder res = new StringBuilder();
        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
            res.append(shoppingCartItem.getDrugId()).append("-").append(shoppingCartItem.getCount()).append("|");
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
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                shoppingCart.addItem(idProduct, count);
            } catch (RuntimeException e) {
                LOGGER.error("Wrong cookie format:", e);
            }
        }
        return shoppingCart;
    }
}
