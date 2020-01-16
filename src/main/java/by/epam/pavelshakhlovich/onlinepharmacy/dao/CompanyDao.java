package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;

import java.util.List;

/**
 * Represents an interface for retrieving company-related data.
 */
public interface CompanyDao extends BaseDao<Company> {
    /**
     * Retrieves all possible companies from database
     *
     * @return list of companies
     * @throws DaoException
     */
    List<Company> getCompanyList() throws DaoException;
}

