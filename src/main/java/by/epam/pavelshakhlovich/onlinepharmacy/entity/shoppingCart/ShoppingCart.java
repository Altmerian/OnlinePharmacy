package by.epam.pavelshakhlovich.onlinepharmacy.entity.shoppingCart;

import by.epam.pavelshakhlovich.onlinepharmacy.service.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1535770438453611801L;
    private Map<Integer, ShoppingCartItem> items = new HashMap<>();
    private int totalCount = 0;
    private static final Logger LOGGER = LogManager.getLogger();

    public void addItem(int itemId, int count) {
        validateShoppingCartSize(itemId);
        ShoppingCartItem shoppingCartItem = items.get(itemId);
        if (shoppingCartItem == null) {
            validateItemCount(count);
            shoppingCartItem = new ShoppingCartItem(itemId, count);
            items.put(itemId, shoppingCartItem);
        } else {
            validateItemCount(count + shoppingCartItem.getCount());
            shoppingCartItem.setCount(shoppingCartItem.getCount() + count);
        }
        refreshStatistics();
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

    private void validateItemCount(int count) {
        if (count > Constants.MAX_ITEM_COUNT_PER_SHOPPING_CART) {
            LOGGER.throwing(new IllegalArgumentException("Limit for product count reached: count=" + count));
        }
    }

    private void validateShoppingCartSize(int idProduct) {
        if (items.size() > Constants.MAX_ITEMS_PER_SHOPPING_CART ||
                (items.size() == Constants.MAX_ITEMS_PER_SHOPPING_CART && !items.containsKey(idProduct))) {
            LOGGER.throwing (new IllegalArgumentException("Limit for ShoppingCart size reached: size=" + items.size()));
        }
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

    public String getView(){
        StringBuilder r = new StringBuilder();
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            r.append(shoppingCartItem.getDrugId()).append("-&gt;").append(shoppingCartItem.getCount()).append("<br>");
        }
        return r.toString();
    }

}
