package by.epam.pavelshakhlovich.onlinepharmacy.util;

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

