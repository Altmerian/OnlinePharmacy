package by.epam.pavelshakhlovich.onlinepharmacy.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1535770438453611801L;
    public static final int MAX_ITEM_COUNT_PER_SHOPPING_CART = 999;
    public static final int MAX_ITEMS_PER_SHOPPING_CART = 20;
    private Map<Long, Integer> items = new HashMap<>();
    private int totalCount = 0;
    private static final Logger LOGGER = LogManager.getLogger();

    public Boolean addItem(Long itemId, int count) {
        if (isLimitShoppingCartSizeReached(itemId)) {
            return false;
        } else {
            Integer oldCount = items.get(itemId);
            if (oldCount == null) {
                count = validateItemCount(count);
                items.put(itemId, count);
            } else {
                validateItemCount(count + oldCount);
                items.put(itemId, oldCount + count);
            }
            refreshStatistics();
            return true;
        }
    }

    public void removeItem(Long itemId) {
            items.remove(itemId);
            refreshStatistics();
    }


    public Map<Long, Integer> getItems() {
        return items;
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

    private Boolean isLimitShoppingCartSizeReached(Long itemId) {
        if (items.size() > MAX_ITEMS_PER_SHOPPING_CART ||
                (items.size() == MAX_ITEMS_PER_SHOPPING_CART && !items.containsKey(itemId))) {
            LOGGER.error("Limit for ShoppingCart size reached: size=" + items.size());
            return true;
        }
        return false;
    }

    private void refreshStatistics() {
        totalCount = 0;
        for (Integer count : items.values()) {
            totalCount += count;
        }
    }

    @Override
    public String toString() {
        return String.format("ShoppingCart [items=%s, totalCount=%s]", items, totalCount);
    }
}
