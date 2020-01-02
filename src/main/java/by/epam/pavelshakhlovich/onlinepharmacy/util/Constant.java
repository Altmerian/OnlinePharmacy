package by.epam.pavelshakhlovich.onlinepharmacy.util;

public final class Constant {

    public static final int MAX_ITEM_COUNT_PER_SHOPPING_CART = 100;
    public static final int MAX_ITEMS_PER_SHOPPING_CART = 20;

    public enum Cookie {
        //1 month ttl
        SHOPPING_CART("opSCC", 60 * 60 * 24 * 31);

        private final String name;
        private final int age;

        Cookie(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
