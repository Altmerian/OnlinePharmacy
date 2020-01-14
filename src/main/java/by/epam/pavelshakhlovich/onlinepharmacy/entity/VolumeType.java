package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents all possible volume types of drugs
 */
public enum VolumeType {
    ML("мл"),
    MG("мг"),
    G("г"),
    PCS("шт");

    private String title;

    VolumeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}