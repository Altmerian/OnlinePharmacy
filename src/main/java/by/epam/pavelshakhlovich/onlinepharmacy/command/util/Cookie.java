package by.epam.pavelshakhlovich.onlinepharmacy.command.util;

/**
 * Application cookies represented by names and ages
 */
public enum Cookie {

    SHOPPING_CART("opSCC", 60 * 60 * 24 * 31); //1 month

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

