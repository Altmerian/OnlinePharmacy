package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.OrderDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.OrderDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;
import by.epam.pavelshakhlovich.onlinepharmacy.service.OrderService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * A implementation of the {@link OrderService} interface
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static OrderDao orderDao = new OrderDaoSQLImpl();

    @Override
    public List<Order> selectOrdersByUserId(User user, long userId, boolean isCanceled) throws ServiceException {
        if (user.getId() == userId || user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER) {
            try {
                return orderDao.selectOrdersByUserId(userId);
            } catch (DaoException e) {
                throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
            }
        } else {
            return null;
        }
    }

    @Override
    public Order selectOrderById(long orderId, User user) throws ServiceException {
        try {
            Order order = orderDao.selectById(orderId);
            if (order == null) {
                return null;
            } else if (user.getId() != order.getUserId()
                    || user.getRole() == UserRole.ADMIN || user.getRole() != UserRole.MANAGER) {
                return order;
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
        return null;
    }

    @Override
    public List<Order> selectAllOrdersByStatus(List<String> statusList, int limit, int offset) throws ServiceException {
        try {
            return orderDao.selectAllOrdersByStatus(statusList, limit, offset);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public int countOrdersByStatus(List<String> statusList) throws ServiceException {
        try {
            return orderDao.countOrdersByStatus(statusList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean createOrder(Order order) throws ServiceException {
        try {
            return orderDao.create(order);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean updateOrderStatus(User user, String orderStatus, long orderId) throws ServiceException {
        if (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER) {
            try {
                Order order = orderDao.selectById(orderId);
                if (order.getStatus().equalsIgnoreCase(orderStatus)) {
                    return false;
                }
                return orderDao.updateOrderStatus(orderStatus, orderId);
            } catch (DaoException e) {
                throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
            }
        }
        return false;
    }


    @Override
    public boolean addItemToCart(long itemId, int quantity) throws ServiceException {
        //todo
        return false;
    }

    @Override
    public boolean removeItemFromCart(long itemId, boolean removeAll) throws ServiceException {
        //todo
        return false;
    }

}
