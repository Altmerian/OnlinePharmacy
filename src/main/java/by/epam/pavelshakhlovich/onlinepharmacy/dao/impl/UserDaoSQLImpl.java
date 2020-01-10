package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.UserDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of the {@code UserDao} interface for working with database.
 */
public class UserDaoSQLImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger();

    private final static String SELECT_ALL_USERS = "SELECT users_credentials.id, login, password_md5, role, " +
            "email, salt, first_name, last_name, address FROM users_credentials, users_data " +
            "WHERE users_credentials.id=user_id" +
            " ORDER BY id ASC" +
            " LIMIT ?,?";
    private final static String COUNT_ALL_USERS = "SELECT COUNT(id) FROM users_credentials";
    private final static String SELECT_USER_BY_LOGIN = "SELECT users_credentials.id, login, password_md5, role, " +
            "email, salt, first_name, last_name, address FROM users_credentials, users_data " +
            "WHERE users_credentials.id=user_id AND login = ?;";
    private final static String SELECT_USER_BY_EMAIL = "SELECT users_credentials.id, login, password_md5, role, " +
            "email, salt, first_name, last_name, address FROM users_credentials, users_data " +
            "WHERE users_credentials.id=user_id AND email = ?";
    private final static String SELECT_USER_BY_ID = "SELECT users_credentials.id, login, password_md5, role, " +
            "email, salt, first_name, last_name, address FROM users_credentials, users_data " +
            "WHERE users_credentials.id=user_id AND users_credentials.id = ?";
    private final static String INSERT_USER_CREDENTIALS = "INSERT INTO users_credentials (login, password_md5, role" +
            ", salt, email) VALUES (?, ?, 'user',?, ?);";
    private final static String INSERT_USER_DATA = "INSERT INTO users_data (user_id, first_name, last_name, address) " +
            "VALUES (last_insert_id(), ?, ?, ?);";
    private final static String UPDATE_USER = "UPDATE users_credentials, users_data SET password_md5 = ?,email = ?," +
            "first_name = ?, last_name = ?,address = ? WHERE users_credentials.id = ?";

    @Override
    public User selectUserByLogin(String login) throws DaoException {
        return selectUserBy(SELECT_USER_BY_LOGIN, login);
    }

    @Override
    public User selectUserById(long id) throws DaoException {
        return selectUserBy(SELECT_USER_BY_ID, String.valueOf(id));
    }

    @Override
    public User selectUserByEmail(String email) throws DaoException {
        return selectUserBy(SELECT_USER_BY_EMAIL, email);
    }

    @Override
    public List<User> selectAllUsers(int offset, int limit) throws DaoException {
        List<User> userList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ALL_USERS);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                User user = new User();
                setUserParameters(user, resultSet);
                userList.add(user);
            }
        } catch (ConnectionPoolException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't get connection from Connection Pool", e));
        } catch (SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException("Can't make prepared statement", e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
        return userList;
    }

    @Override
    public int countAllUsers() throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(COUNT_ALL_USERS);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (ConnectionPoolException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException("Can't get connection from Connection Pool", e));
        } catch (SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException("Can't make prepared statement", e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public boolean insertUser(User user) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(INSERT_USER_CREDENTIALS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getHashedPassword());
            preparedStatement.setString(3, user.getSalt());
            preparedStatement.setString(4, user.getEmail());
            int result1 = preparedStatement.executeUpdate();
            preparedStatement = cn.prepareStatement(INSERT_USER_DATA);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            int result2 = preparedStatement.executeUpdate();
            return (result1 > 0 && result2 > 0);
        } catch (ConnectionPoolException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException("Can't get connection from Connection Pool", e));
        } catch (SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e.getMessage(), e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public boolean updateUser(User user) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getHashedPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setLong(6, user.getId());
            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Request to database failed", e));
        } catch (ConnectionPoolException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't get connection from connection pool", e));
        } finally {
            closeResources(cn, preparedStatement);
        }
        return true;
    }

    private User selectUserBy(String queryString, String parameter) throws DaoException {
        User user = new User();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(queryString);
            preparedStatement.setString(1, parameter);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            setUserParameters(user, resultSet);
        } catch (ConnectionPoolException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't get connection from Connection Pool", e));
        } catch (SQLException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't make prepared statement", e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
        return user;
    }

    private void setUserParameters(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong(Parameter.ID));
        user.setLogin(resultSet.getString(Parameter.LOGIN));
        user.setHashedPassword(resultSet.getString(Parameter.PASSWORD_MD5));
        user.setRole(UserRole.valueOf(resultSet.getString(Parameter.ROLE).toUpperCase()));
        user.setEmail(resultSet.getString(Parameter.EMAIL));
        user.setSalt(resultSet.getString(Parameter.SALT));
        user.setFirstName(resultSet.getString(Parameter.FIRST_NAME));
        user.setLastName(resultSet.getString(Parameter.LAST_NAME));
        user.setAddress(resultSet.getString(Parameter.ADDRESS));
    }

    private void closeResources(Connection connection, PreparedStatement preparedStatement) throws DaoException {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't close prepared statement", e));
        }
        if (connection != null) {
            try {
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                LOGGER.throwing(Level.ERROR, new DaoException("Can't release connection to connection pool", e));
            }
        }
    }

    private void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws DaoException {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't close prepared statement", e));
        }
        if (connection != null) {
            try {
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (ConnectionPoolException e) {
                LOGGER.throwing(Level.ERROR, new DaoException("Can't release connection to connection pool", e));
            }
        }
    }

}
