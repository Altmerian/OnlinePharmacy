package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.CompanyDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.CompanyDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * An implementation of the {@see CompanyService} interface
 */

public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CompanyDao companyDao = new CompanyDaoSQLImpl();

    @Override
    public List<Company> getCompanyList() throws ServiceException {
        try {
            return companyDao.getCompanyList();
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean addCompany(Company company) throws ServiceException {
        try {
            if (companyDao.getCompanyByNameAndCountry(company.getName(), company.getCountryId()) != null) {
                return false;
            } else {
                return companyDao.create(company);
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }
}

