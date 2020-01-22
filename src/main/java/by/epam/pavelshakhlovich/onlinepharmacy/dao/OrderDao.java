package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;

import java.util.List;

/**
 * Represents an interface for retrieving order-related data.
 */
public interface OrderDao extends BaseDao<Order> {

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
     * Selects a list of all orders with given status by all users
     *
     * @param statusList is a String list of statuses which orders could be
     * @return a list of orders
     * @throws DaoException  if failed to retrieve data from the storage due to technical problems
     */
    int countOrdersByStatus(List<String> statusList) throws DaoException;

    /**
     * Removes all items with a specified id from given order
     *
     * @param itemId  id of the item to remove
     * @param orderId id of the order
     * @return {@code true} if removed successfully, {@code false} if delete failed
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean deleteItemFromOrder(long itemId, long orderId) throws DaoException;

    /**
     * Adds an item with a specified id of a specified quantity to a given order
     *
     * @param itemId   id of the item to add
     * @param quantity quantity of items
     * @param orderId  id of the order
     * @return {@code true} if inserted successfully, {@code false} if insert failed
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean insertItemToOrder(long itemId, int quantity, long orderId) throws DaoException;

    /**
     * Update status of given order
     *
     * @param orderStatus is orderStatus to update
     * @param orderId     is id of order
     * @return {@code true} if updated successfully, {@code false} if update failed
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean updateOrderStatus(String orderStatus, long orderId) throws DaoException;
}

