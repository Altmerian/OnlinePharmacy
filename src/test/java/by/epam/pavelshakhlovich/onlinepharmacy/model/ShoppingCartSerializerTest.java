package by.epam.pavelshakhlovich.onlinepharmacy.model;

import org.testng.annotations.*;

import static org.testng.Assert.*;

public class ShoppingCartSerializerTest {
    ShoppingCart shoppingCart = new ShoppingCart();


    @BeforeClass
    public void setUp() {
        shoppingCart.addItem(1L, 2);
        shoppingCart.addItem(2L, 3);
    }

    @AfterClass
    public void tearDown() {
        shoppingCart = null;
    }

    @Test
    public void testShoppingCartToString() {
        String expected = "1-2|2-3";
        assertEquals(ShoppingCartSerializer.shoppingCartToString(shoppingCart), expected);
    }

    @Test
    public void testShoppingCartFromString() {
        ShoppingCart expected = ShoppingCartSerializer.shoppingCartFromString("1-2|2-3");
        assertEquals(shoppingCart, expected);
    }
}