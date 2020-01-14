package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class {@code Item} represents an item in the pharmacy's catalog.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 3946646628310050691L;
    private long id;
    private String label;
    private long dosageId;
    private String dosage;
    private int volume;
    private String volumeType;
    private long manufacturerId;
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

    public long getDosageId() {
        return dosageId;
    }

    public void setDosageId(long dosageId) {
        this.dosageId = dosageId;
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

    public long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(long manufacturerId) {
        this.manufacturerId = manufacturerId;
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
        if (byPrescription != item.byPrescription) {
            return false;
        }
        if (!label.equals(item.label)) {
            return false;
        }
        if (dosageId != item.dosageId) {
            return false;
        }
        if (!dosage.equals(item.dosage)) {
            return false;
        }
        if (volume != item.volume) {
            return false;
        }
        if (!volumeType.equals(item.volumeType)) {
            return false;
        }
        if (manufacturerId != item.manufacturerId) {
            return false;
        }
        if (!manufacturerName.equals(item.manufacturerName)) {
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
        result = 31 * result + Long.hashCode(dosageId);
        result = 31 * result + dosage.hashCode();
        result = 31 * result + volume;
        result = 31 * result + volumeType.hashCode();
        result = 31 * result + Long.hashCode(manufacturerId);
        result = 31 * result + manufacturerName.hashCode();
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
                ", dosageId='" + dosageId +
                ", dosage='" + dosage + '\'' +
                ", volume=" + volume +
                ", volumeType='" + volumeType + '\'' +
                ", manufacturerId='" + manufacturerId +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", price=" + price +
                ", byPrescription=" + byPrescription +
                ", description='" + description + '\'' +
                '}';
    }
}
