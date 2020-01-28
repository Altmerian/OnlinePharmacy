package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;

/**
 * Class {@code Country} represents any country that can be used for a manufacturer identity
 */
public class Country implements Serializable { //probably might be removed
    private static final long serialVersionUID = -159247625672138922L;
    private long id;
    private String name;
    private String codeAlpha3;

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

    public String getCodeAlpha3() {
        return codeAlpha3;
    }

    public void setCodeAlpha3(String codeAlpha3) {
        this.codeAlpha3 = codeAlpha3;
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
        if (!name.equals(country.name)) {
            return false;
        }
        return codeAlpha3 != null ? codeAlpha3.equals(country.codeAlpha3) : country.codeAlpha3 == null;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + (codeAlpha3 != null ? codeAlpha3.hashCode() : 0);
        return result;
    }
}
