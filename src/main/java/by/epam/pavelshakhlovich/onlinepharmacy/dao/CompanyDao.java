package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Country;

import java.util.List;

/**
 * Represents an interface for retrieving company-related data.
 */
public interface CompanyDao extends BaseDao<Company> {
    /**
     * Retrieves all possible companies from data source
     *
     * @return list of companies
     * @throws DaoException
     */
    List<Company> getCompanyList() throws DaoException;

    /**
     * Retrieves company by name and country name from data source
     * @param name company name
     * @param countryId company's country ID
     * @return Company with such parameters if exists
     * @throws DaoException
     */
    Company getCompanyByNameAndCountry(String name, long countryId) throws DaoException;

    /**
     * Add a new country to the storage, e.g. into database
     *
     * @param country that should be stored in data source
     * @return true if country was added and false if country already exists
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean addCountry(String country) throws DaoException;

    /**
     * Retrieves all possible countries from data source
     */
    List<Country> getCountries() throws DaoException;

    /**
     * Retrieves country by name from data source
     *
     * @param country country name
     * @return dosage with given name
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    Country getCountryByName(String country) throws DaoException;
}

