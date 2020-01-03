package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;

/**
 * Class {@code Company} represents any company that can be used in the pharmacy-app
 * such as manufacturer or vendor and etc
 */
public class Company implements Comparable<Company>, Serializable {
    private static final long serialVersionUID = -5492792504841786349L;
    private long id;
    private String type;
    private String shortName;
    private String fullName;
    private String country;
    private String website;

    public Company() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (id != company.id) {
            return false;
        }
        if (!type.equals(company.type)) {
            return false;
        }
        if (!shortName.equals(company.shortName)) {
            return false;
        }
        if (!fullName.equals(company.fullName)) {
            return false;
        }
        if (!country.equals(company.country)) {
            return false;
        }
        return website != null ? website.equals(company.website) : company.website == null;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + type.hashCode();
        result = 31 * result + shortName.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Company o) {
        return this.shortName.compareTo(o.shortName);
    }
}
