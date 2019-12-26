package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible prescription's status.
 */
public enum PrescriptionStatus {
    REQUESTED("requested"),
    ACTUAL("actual"),
    REJECTED("rejected by doctor"),
    OVERDUE("overdue"),
    CANCELED("already used");

    private String status;

    PrescriptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
