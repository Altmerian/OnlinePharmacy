package by.epam.pavelshakhlovich.onlinepharmacy.model;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ShoppingCartTest {
    ShoppingCart shoppingCart;

    @BeforeMethod
    public void setUp() {
        shoppingCart = new ShoppingCart();
        shoppingCart.getItems().put(1L, 2);
        shoppingCart.getItems().put(2L, 1);
        shoppingCart.refreshStatistics();
    }

    @AfterMethod
    public void tearDown() {
        shoppingCart = null;
    }

    @Test
    public void testAddItemPositive() {
        assertTrue(shoppingCart.addItem(3L, 4));
    }

    @Test
    public void testAddItemNegative() {
        assertTrue(shoppingCart.addItem(3L, 1000));
    }

    @Test
    public void testGetItems() {
        Map<Long, Integer> expected = new HashMap<>();
        expected.put(1L, 2);
        expected.put(2L, 1);
        assertEquals(shoppingCart.getItems(), expected);
    }

    @Test
    public void testGetTotalCount() {
        int expected = 3;
        assertEquals(shoppingCart.getTotalCount(), expected);
    }
}