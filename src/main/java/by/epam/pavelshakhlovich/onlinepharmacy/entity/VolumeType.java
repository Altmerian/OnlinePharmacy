package by.epam.pavelshakhlovich.onlinepharmacy.entity;

/**
 * Represents all possible volume types of drugs
 */
public enum VolumeType {
    PCS("шт"),
    ML("мл"),
    MG("мг"),
    G("г");

    private String title;

    VolumeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}