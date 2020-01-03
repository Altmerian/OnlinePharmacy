package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;

public class Country implements Serializable {
    private static final long serialVersionUID = -159247625672138922L;
    private long id;
    private String shortName;
    private String fullName;
    private String codeAlpha3;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        if (!shortName.equals(country.shortName)) {
            return false;
        }
        if (fullName != null ? !fullName.equals(country.fullName) : country.fullName != null) {
            return false;
        }
        return codeAlpha3 != null ? codeAlpha3.equals(country.codeAlpha3) : country.codeAlpha3 == null;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + shortName.hashCode();
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (codeAlpha3 != null ? codeAlpha3.hashCode() : 0);
        return result;
    }
}
