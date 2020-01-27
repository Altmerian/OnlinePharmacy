package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;

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
}

