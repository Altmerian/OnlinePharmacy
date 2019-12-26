package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible order's status.
 */
public enum OrderStatus {
    OPENED("opened"),
    PROCESSING("in process"),
    PAID("paid"),
    COMPLETED("delivered"),
    CANCELED ("canceled");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
