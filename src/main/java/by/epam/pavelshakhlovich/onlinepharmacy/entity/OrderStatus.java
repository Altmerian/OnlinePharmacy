package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible order's status.
 */
public enum OrderStatus {
    OPENED("opened"),
    IN_PROCESS("in_process"),
    PAYMENT_CONFIRMATION("payment_confirmation"),
    PAID("paid"),
    COMPLETED("completed"),
    CANCELED ("canceled");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
