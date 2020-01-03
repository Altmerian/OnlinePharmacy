package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;

/**
 * Class {@code Dosage} represents a type of possible dosage forms for drugs in the pharmacy's catalog.
 */
public class Dosage implements Serializable {
    private static final long serialVersionUID = -2952836315041024924L;
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Dosage that = (Dosage) obj;
        if (id != that.id) {
            return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
