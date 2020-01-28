package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents base DAO interface for common operations with data storage.
 */
public interface BaseDao<T> {
    Logger LOGGER = LogManager.getLogger();

    /**
     * Retrieves a entity with given id.
     *
     * @param id entity's id
     * @return Entity with corresponding id and other parameters or {@code null} if such entity doesn't exist
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    T selectById(long id) throws DaoException;

    /**
     * Returns a list of all entities from db
     *
     * @param offset index of the first element on the page
     * @param limit  is a number of entities on the page
     * @return null if no entities were found
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<T> selectAll(int offset, int limit) throws DaoException;

    /**
     * Add a new entity to the storage, e.g. into database
     *
     * @param entity is entity bean that should be stored in database
     * @return true if entity was added and false if entity with such parameters already exists
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean create(T entity) throws DaoException;

    /**
     * Updates entity data in the storage, e.g. into database
     *
     * @param entity is entity bean that should be stored in database
     * @return true if entity was successfully upgraded
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean update(T entity) throws DaoException;

    /**
     * Removes entity data from the storage, e.g. from database
     *
     * @param id is entity ID that should be deleted from database
     * @return true if entity was successfully upgraded
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean delete(long id) throws DaoException;

    /**
     * Closes all following resources
     *
     * @param connection        connection to data base
     * @param preparedStatement statement
     */
    default void closeResources(Connection connection, PreparedStatement preparedStatement) {
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

    /**
     * Closes all following resources
     *
     * @param connection        connection to data base
     * @param preparedStatement statement
     * @param resultSet         query results
     */
    default void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            LOGGER.throwing(Level.ERROR, new DaoException("Can't close prepared statement and result set", e));
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
