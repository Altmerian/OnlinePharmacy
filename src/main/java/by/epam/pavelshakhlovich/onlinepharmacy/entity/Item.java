package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.math.BigDecimal;

/**
 * Class {@code Item} represents an item in the pharmacy's catalog.
 */
public class Item {

    private long id;
    private String label;
    private String dosageFormName;
    private String dosage;
    private int volume;
    private String volumeType;
    private String manufacturerName;
    private BigDecimal price;
    private boolean byPrescription;
    private String description;

    public Item() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDosageFormName() {
        return dosageFormName;
    }

    public void setDosageFormName(String dosageFormName) {
        this.dosageFormName = dosageFormName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(String volumeType) {
        this.volumeType = volumeType;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isByPrescription() {
        return byPrescription;
    }

    public void setByPrescription(boolean byPrescription) {
        this.byPrescription = byPrescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }

        Item item = (Item) o;

        if (id != item.id) {
            return false;
        }
        if (volume != item.volume) {
            return false;
        }
        if (byPrescription != item.byPrescription) {
            return false;
        }
        if (!label.equals(item.label)) {
            return false;
        }
        if (!dosageFormName.equals(item.dosageFormName)) {
            return false;
        }
        if (!dosage.equals(item.dosage)) {
            return false;
        }
        if (!volumeType.equals(item.volumeType)) {
            return false;
        }
        if (manufacturerName != null ? !manufacturerName.equals(item.manufacturerName) : item.manufacturerName != null) {
            return false;
        }
        if (price != null ? !price.equals(item.price) : item.price != null) {
            return false;
        }
        return description != null ? description.equals(item.description) : item.description == null;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + label.hashCode();
        result = 31 * result + dosageFormName.hashCode();
        result = 31 * result + dosage.hashCode();
        result = 31 * result + volume;
        result = 31 * result + volumeType.hashCode();
        result = 31 * result + (manufacturerName != null ? manufacturerName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (byPrescription ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", dosageFormName='" + dosageFormName + '\'' +
                ", dosage='" + dosage + '\'' +
                ", volume=" + volume +
                ", volumeType='" + volumeType + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", price=" + price +
                ", byPrescription=" + byPrescription +
                ", description='" + description + '\'' +
                '}';
    }
}
