package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents all possible user's roles
 */
public enum UserRole {
    GUEST("Guest"),
    USER("Customer"),
    MANAGER("Pharmacist"),
    DOCTOR("Doctor"),
    ADMIN("Administrator");
    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}