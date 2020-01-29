package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible order's status.
 */
public enum OrderStatus {
    IN_PROCESS("В работе"),
    PAYMENT_CONFIRMATION("Подтверждение оплаты"),
    PAID("Оплачен"),
    COMPLETED("Выполнен");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
