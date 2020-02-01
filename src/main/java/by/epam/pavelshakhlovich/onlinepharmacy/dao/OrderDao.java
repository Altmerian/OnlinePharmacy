package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Represents an interface for retrieving order-related data.
 */
public interface OrderDao extends BaseDao<Order> {

    /**
     * Retrieves a list of all user's orders
     *
     * @param userId     id of the user, owning the requested order
     * @return added order
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    Order getLastAddedOrder(long userId) throws DaoException;

    /**
     * Retrieves a list of all user's orders
     *
     * @param userId     id of the user
     * @return list of user's orders or {@code null} if user has none
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<Order> selectOrdersByUserId(long userId) throws DaoException;

    /**
     * Selects a list of all orders with given status by all users
     *
     * @param statusList is a String list of statuses which orders could be
     * @param limit parameters for pagination
     * @param offset parameters for pagination
     * @return a list of orders
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<Order> selectAllOrdersByStatus(List<String> statusList, int limit, int offset) throws DaoException;

    /**
     * Counts all orders with given status by all users
     *
     * @param statusList is a String list of statuses which orders could be
     * @return a list of orders
     * @throws DaoException  if failed to retrieve data from the storage due to technical problems
     */
    int countOrdersByStatus(List<String> statusList) throws DaoException;

    /**
     * Update status of given order
     *
     * @param orderStatus is orderStatus to update
     * @param orderId     is id of order
     * @return {@code true} if updated successfully, {@code false} if update failed
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean updateOrderStatus(String orderStatus, long orderId) throws DaoException;

    /**
     * Selects all order's status changes
     * @param orderId id of the order to search
     * @return {@code true} if operation was successful
     * @throws DaoException  if exception occurred on an underlying level
     */
    Map<Timestamp, String> getOrderEvents(long orderId) throws DaoException;
}

