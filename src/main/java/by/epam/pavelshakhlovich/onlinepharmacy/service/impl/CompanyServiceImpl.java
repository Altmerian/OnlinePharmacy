package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.CompanyDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.CompanyDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;

import java.util.List;

/**
 * An implementation of the {@see CompanyService} interface
 */

public class CompanyServiceImpl implements CompanyService {

    private static final CompanyDao companyDao = new CompanyDaoSQLImpl();

    @Override
    public List<Company> getCompanyList() throws ServiceException {
        try {
            return companyDao.getCompanyList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean insertCompany(Company company) throws ServiceException {
        try {
            if (companyDao.getCompanyList().contains(company)) {
                return false;
            } else {
                return companyDao.create(company);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}

