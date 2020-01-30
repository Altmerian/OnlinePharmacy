package by.epam.pavelshakhlovich.onlinepharmacy.service;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;

import java.util.List;

/**
 * Represents an interface of a service providing order-related actions
 */
public interface OrderService {

    /**
     * Returns a list of {@link Order} orders of a specified user
     *
     * @param user       is user that request orders
     * @param userId     id of the user, owning the requested orders
     * @return list of user's orders
     * @throws ServiceException if exception occurred on an underlying level
     */
    List<Order> selectOrdersByUserId(User user, long userId) throws ServiceException;

    /**
     * Retrieves an order with given id
     *
     * @param orderId id of the order
     * @param user    User that request this order
     * @return order or {@code null} if no such order
     * or special Order object with status variable 'ACCESS DENIED' if user don't have permission for viewing this order
     * @throws ServiceException if failed to retrieve data from dao layer
     */
    Order selectOrderById(long orderId, User user) throws ServiceException;

    /**
     * Selects a list of all orders with given status by all users
     *
     * @param statusList is a String list of statuses which orders could be
     * @param limit      parameters for pagination
     * @param offset     parameters for pagination
     * @return a list of orders
     * @throws ServiceException if exception occurred on an underlying level
     */
    List<Order> selectAllOrdersByStatus(List<String> statusList, int limit, int offset) throws ServiceException;

    /**
     * Selects a list of all orders with given status by all users
     *
     * @param statusList is a String list of statuses which orders could be
     * @return a list of orders
     * @throws ServiceException if exception occurred on an underlying level
     */
    int countOrdersByStatus(List<String> statusList) throws ServiceException;

    /**
     * Creates an order from shopping cart. Date of submission are saved as creation date of the order.
     * Also final prices and total amount saved to the order.
     * After submission the order can't be modified, it can only be canceled.
     *
     * @param order represents a shopping cart from which order is created
     * @return {@code true} if submitted successfully, {@code false} if order doesn't contain any items or failed to be submitted
     * @throws ServiceException if exception occurred on an underlying level
     */
    boolean submitOrder(Order order) throws ServiceException;

    /**
     * Update status of given order
     *
     * @param user        User that requested this update
     * @param orderStatus is orderStatus to update
     * @param orderId     is id of order
     * @return {@code true} if updated successfully, {@code false} if update failed
     * @throws ServiceException if exception occurred on an underlying level
     */
    boolean updateOrderStatus(User user, String orderStatus, long orderId) throws ServiceException;

    /**
     * Cancel a specified order and delete it from data source
     * @param orderId id of the order to cancel
     * @param user user that requests the command
     * @return {@code true} if operation was successful
     * @throws ServiceException  if exception occurred on an underlying level
     */
    boolean cancelOrder(User user, long orderId) throws ServiceException;

    /**
     * Returns last added {@link Order} by a specified user
     *
     * @param userId     id of the user, owning the requested order
     * @return added order
     * @throws ServiceException if exception occurred on an underlying level
     */
    Order getLastAddedOrder(long userId) throws ServiceException;
}
