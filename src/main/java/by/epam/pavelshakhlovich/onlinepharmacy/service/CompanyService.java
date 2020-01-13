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
}
