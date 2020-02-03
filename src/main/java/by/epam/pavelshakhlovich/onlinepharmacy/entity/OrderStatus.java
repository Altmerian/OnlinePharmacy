package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible order's status.
 */
public enum OrderStatus {
    IN_PROCESS("in_process"),
    PAYMENT_CONFIRMATION("payment_confirmation"),
    PAID("paid"),
    COMPLETED("completed");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return status;
    }
}
