package by.epam.pavelshakhlovich.onlinepharmacy.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1535770438453611801L;
    public static final int MAX_ITEM_COUNT_PER_SHOPPING_CART = 100;
    public static final int MAX_ITEMS_PER_SHOPPING_CART = 20;
    private Map<Integer, ShoppingCartItem> items = new HashMap<>();
    private int totalCount = 0;
    private static final Logger LOGGER = LogManager.getLogger();

    public Boolean addItem(int itemId, int count) {
        if (isLimitShoppingCartSizeReached(itemId)) {
            return false;
        } else {
            ShoppingCartItem shoppingCartItem = items.get(itemId);
            if (shoppingCartItem == null) {
                count = validateItemCount(count);
                shoppingCartItem = new ShoppingCartItem(itemId, count);
                items.put(itemId, shoppingCartItem);
            } else {
                validateItemCount(count + shoppingCartItem.getCount());
                shoppingCartItem.setCount(shoppingCartItem.getCount() + count);
            }
            refreshStatistics();
            return true;
        }
    }

    public void removeItem(Integer idProduct, int count) {
        ShoppingCartItem shoppingCartItem = items.get(idProduct);
        if (shoppingCartItem != null) {
            if (shoppingCartItem.getCount() > count) {
                shoppingCartItem.setCount(shoppingCartItem.getCount() - count);
            } else {
                items.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    public Collection<ShoppingCartItem> getItems() {
        return items.values();
    }

    public int getTotalCount() {
        return totalCount;
    }

    private int validateItemCount(int count) {
        if (count > MAX_ITEM_COUNT_PER_SHOPPING_CART) {
            LOGGER.error("Limit for product count reached, item count was set as max default=" +
                    MAX_ITEM_COUNT_PER_SHOPPING_CART);
            return MAX_ITEM_COUNT_PER_SHOPPING_CART;
        }
        return count;
    }

    private Boolean isLimitShoppingCartSizeReached(int idProduct) {
        if (items.size() > MAX_ITEMS_PER_SHOPPING_CART ||
                (items.size() == MAX_ITEMS_PER_SHOPPING_CART && !items.containsKey(idProduct))) {
            LOGGER.error ("Limit for ShoppingCart size reached: size=" + items.size());
            return true;
        }
        return false;
    }

    private void refreshStatistics() {
        totalCount = 0;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
        }
    }

    @Override
    public String toString() {
        return String.format("ShoppingCart [items=%s, totalCount=%s]", items, totalCount);
    }
}
