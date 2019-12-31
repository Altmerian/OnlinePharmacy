package by.epam.pavelshakhlovich.onlinepharmacy.service.util;

public final class Constants {

    public static final int MAX_ITEM_COUNT_PER_SHOPPING_CART = 100;
    public static final int MAX_ITEMS_PER_SHOPPING_CART = 20;

    public enum Cookie {
        //1 month ttl
        SHOPPING_CART("ofSCC", 60 * 60 * 24 * 31);

        private final String name;
        private final int ttl;

        Cookie(String name, int ttl) {
            this.name = name;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public int getTtl() {
            return ttl;
        }
    }
}
