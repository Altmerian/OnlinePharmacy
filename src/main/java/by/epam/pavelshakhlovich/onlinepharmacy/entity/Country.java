package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;

/**
 * Class {@code Country} represents any country that can be used for a manufacturer identity
 */
public class Country implements Serializable { //probably might be removed
    private static final long serialVersionUID = -159247625672138922L;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }

        Country country = (Country) o;

        if (id != country.id) {
            return false;
        }

        return !name.equals(country.name);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        return result;
    }
}
