package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.CompanyDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Country;
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
    private static final String SELECT_COMPANY_BY_NAME_AND_COUNTRY = "SELECT m.id, m.name, c.id AS country_id, " +
            "website FROM manufacturers m " +
            "JOIN countries c ON m.country_id = c.id " +
            "WHERE m.name = ? AND c.id = ?";
    private static final String INSERT_COMPANY = "INSERT INTO manufacturers (name, country_id, website) " +
            "VALUES(?, ?, ?)";
    private static final String SELECT_COUNTRIES = "SELECT id, name FROM countries";
    private static final String SELECT_COUNTRY_BY_NAME = "SELECT c.id, c.name FROM countries c WHERE c.name = ?";
    private static final String INSERT_COUNTRY = "INSERT INTO countries (name) VALUES(?)";

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
    public Company getCompanyByNameAndCountry(String name, long countryId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_COMPANY_BY_NAME_AND_COUNTRY);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, countryId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            Company company = new Company();
            company.setName(resultSet.getString(Parameter.NAME));
            company.setCountry(resultSet.getString(Parameter.COUNTRY));
            return company;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public boolean create(Company company) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(INSERT_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setLong(2, company.getCountryId());
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
    public boolean update(Company company) throws DaoException {
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

    @Override
    public boolean addCountry(String country) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(INSERT_COUNTRY);
            preparedStatement.setString(1, country);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public List<Country> getCountries() throws DaoException {
        List<Country> countries = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_COUNTRIES);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Country country = new Country();
                    country.setId(resultSet.getLong(1));
                    country.setName(resultSet.getString(2));
                    countries.add(country);
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
        return countries;
    }

    @Override
    public Country getCountryByName(String countryName) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_COUNTRY_BY_NAME);
            preparedStatement.setString(1, countryName);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            Country country = new Country();
            country.setId(resultSet.getLong(Parameter.ID));
            country.setName(resultSet.getString(Parameter.NAME));
            return country;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }
}
