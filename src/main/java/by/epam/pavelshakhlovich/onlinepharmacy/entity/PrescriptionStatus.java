package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents possible prescription's status.
 */
public enum PrescriptionStatus {
    REQUESTED("requested"),
    APPROVED("approved"),
    OVERDUE("overdue"),
    REJECTED("rejected");

    private String title;

    PrescriptionStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
