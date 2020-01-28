package by.epam.pavelshakhlovich.onlinepharmacy.service;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Country;

import java.util.List;

/**
 * Represents an interface of a service for company-related actions
 */

public interface CompanyService {

    /**
     * Insert company to the storage, e.g. database
     * @param company company name to add
     * @return true if insert to the storage was successful, and false if storage already contains such value
     */
    boolean addCompany(Company company) throws ServiceException;

    /**
     * Retrieves all possible dosage companies from dao layer
     */
    List<Company> getCompanyList() throws ServiceException;

    /**
     * Retrieves all countries from dao layer
     */
    List<Country> getCountries() throws ServiceException;

    /**
     * Attempts to add a new country.
     * @param country to add
     * @return true if adding succeeded, false if country already exists
     * @throws ServiceException if DaoException occurred
     */
    boolean addCountry(String country) throws ServiceException;
}
