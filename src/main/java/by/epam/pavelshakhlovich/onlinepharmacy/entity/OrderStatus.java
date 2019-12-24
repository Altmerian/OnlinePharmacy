package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible order's status.
 */
public enum OrderStatus {
    CART("opened"),
    PROCESSING("in process"),
    SHIPPING("to delivery"),
    COMPLETED("completed");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public String getName() {
        return name().toLowerCase();
    }
}
