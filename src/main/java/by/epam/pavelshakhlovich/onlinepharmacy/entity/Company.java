package by.epam.pavelshakhlovich.onlinepharmacy.entity;

import java.io.Serializable;

/**
 * Class {@code Company} represents any company that can be used in the pharmacy-app
 * such as manufacturer or vendor and etc
 */
public class Company implements Comparable<Company>, Serializable {
    private static final long serialVersionUID = -5492792504841786349L;
    private long id;
    private String name;
    private String country;
    private long countryId;
    private String website;

    public Company() {
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
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
        if (!name.equals(company.name)) {
            return false;
        }
        if (!country.equals(company.country)) {
            return false;
        }
        if (countryId != company.countryId) {
            return false;
        }
        return website != null ? website.equals(company.website) : company.website == null;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + Long.hashCode(countryId);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Company o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}

