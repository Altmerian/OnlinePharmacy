package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.CompanyDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of the {@see CompanyDao} interface.
 */
public class CompanyDaoSQLImpl implements CompanyDao {

    private static final String SELECT_COMPANIES = "SELECT m.id, m.name, c.name " +
            "AS country, website FROM manufacturers m, countries c WHERE m.country_id = c.id" +
            " ORDER BY m.name";
    private static final String INSERT_COMPANY = "INSERT INTO manufacturers(name, country, website) " +
            "VALUES(?, ?, ?)";

    @Override
    public List<Company> getCompanyList() throws DaoException {
        List<Company> companies = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_COMPANIES);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Company company = new Company();
                    company.setId(resultSet.getLong(Parameter.ID));
                    company.setName(resultSet.getString(Parameter.NAME));
                    company.setCountry(resultSet.getString(Parameter.COUNTRY));
                    company.setWebsite(resultSet.getString(Parameter.WEBSITE));
                    companies.add(company);
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
        return companies;
    }

    @Override
    public boolean create(Company company) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(INSERT_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getCountry());
            preparedStatement.setString(3, company.getWebsite());
            return preparedStatement.executeUpdate() > 0;
        } catch (ConnectionPoolException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException("Can't get connection from Connection Pool", e));
        } catch (SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e.getMessage(), e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public List<Company> selectAll(int offset, int limit) throws DaoException {
        throw LOGGER.throwing(Level.ERROR, new UnsupportedOperationException());
    }

    @Override
    public boolean update(Company entity) throws DaoException {
        throw LOGGER.throwing(Level.ERROR, new UnsupportedOperationException());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        throw LOGGER.throwing(Level.ERROR, new UnsupportedOperationException());
    }

    @Override
    public Company selectById(long id) throws DaoException {
        throw LOGGER.throwing(Level.ERROR, new UnsupportedOperationException());
    }
}
