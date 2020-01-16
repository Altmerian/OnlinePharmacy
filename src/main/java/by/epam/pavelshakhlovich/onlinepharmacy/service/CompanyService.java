package by.epam.pavelshakhlovich.onlinepharmacy.service;


import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;

import java.util.List;

/**
 * Represents an interface of a service for company-related actions
 */

public interface CompanyService {

    /**
     * Retrieves all possible dosage companies from dao layer
     */
    List<Company> getCompanyList() throws ServiceException;

    /**
     * Insert company to the storage, e.g. database
     * @param company new company
     * @return true if insert to the storage was successful, and false if storage already contains such value
     */
    boolean insertCompany(Company company) throws ServiceException;

}
