package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.OrderDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This implementation of {@see OrderDao} interface based on JDBC and MySQL.
 */

public class OrderDaoSQLImpl implements OrderDao {

    private static final String SELECT_USER_ORDERS = "SELECT id, date, amount, status" +
            " FROM orders" +
            " WHERE customer_id = ?" +
            " ORDER BY id DESC ";
    private static final String SELECT_ORDER_BY_ID = "SELECT o.id, o.customer_id, o.date, o.amount, o.status, " +
            "d.id AS drug_id, d.label, d.dosage, d.volume, d.volume_type, d.by_prescription, dro.quantity, dro.price FROM orders o " +
            "LEFT JOIN drugs_ordered dro ON o.id = dro.order_id " +
            "LEFT JOIN drugs d ON dro.drug_id = d.id " +
            "WHERE o.id = ? " +
            "ORDER BY d.label";
    private static final String SELECT_ALL_ORDERS_BY_STATUS = "SELECT o.id, o.customer_id, u.first_name, u.last_name, u.login, u.address, " +
            "o.date, o.amount, o.status FROM orders o " +
            "JOIN  users u ON o.customer_id = u.id " +
            "WHERE o.status IN(?,?,?,?) " +
            "ORDER BY o.date DESC " +
            "LIMIT ?,?";
    private static final String COUNT_ALL_ORDERS = "SELECT COUNT(o.id) AS count " +
            "FROM orders o " +
            "WHERE o.status IN(?,?,?,?)";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE id = ?";

    @Override
    public List<Order> selectOrdersByUserId(long userId) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_USER_ORDERS);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getLong(Parameter.ID));
                order.setDate(resultSet.getTimestamp(Parameter.DATE));
                order.setAmount(resultSet.getBigDecimal(Parameter.AMOUNT));
                order.setStatus(resultSet.getString(Parameter.STATUS));
                orderList.add(order);
            }
            return orderList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public Order selectById(long orderId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ORDER_BY_ID);
            preparedStatement.setLong(1, orderId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            Order order = new Order();
            order.setUserId(resultSet.getLong(Parameter.CUSTOMER_ID));
            order.setId(resultSet.getLong(Parameter.ID));
            order.setDate(resultSet.getTimestamp(Parameter.DATE));
            order.setAmount(resultSet.getBigDecimal(Parameter.AMOUNT));
            order.setStatus(resultSet.getString(Parameter.STATUS));
            do {
                Item item = new Item();
                item.setId(resultSet.getLong(Parameter.DRUG_ID));
                if (resultSet.wasNull()) {
                    break;
                }
                item.setLabel(resultSet.getString(Parameter.LABEL));
                item.setDosage(resultSet.getString(Parameter.DOSAGE));
                item.setVolume(resultSet.getInt(Parameter.VOLUME));
                item.setVolumeType(resultSet.getString(Parameter.VOLUME_TYPE));
                item.setByPrescription(resultSet.getBoolean(Parameter.BY_PRESCRIPTION));
                item.setPrice(resultSet.getBigDecimal(Parameter.PRICE));
                order.getItems().put(item, resultSet.getInt(Parameter.QUANTITY));
            } while (resultSet.next());
            return order;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Order> selectAllOrdersByStatus(List<String> statusList, int offset, int limit) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ALL_ORDERS_BY_STATUS);
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Order order = new Order();
                order.setUserId(resultSet.getLong(Parameter.CUSTOMER_ID));
                order.setId(resultSet.getLong(Parameter.ID));
                order.setDate(resultSet.getTimestamp(Parameter.DATE));
                order.setAmount(resultSet.getBigDecimal(Parameter.AMOUNT));
                order.setStatus(resultSet.getString(Parameter.STATUS));
                orderList.add(order);
            }
            return orderList;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public int countOrdersByStatus(List<String> statusList) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(COUNT_ALL_ORDERS);
            for (int i = 2; i < statusList.size() + 2; i++) {
                preparedStatement.setString(i, statusList.get(i - 2));
            }
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return 0;
            }
            resultSet.next();
            return resultSet.getInt(1);
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public boolean updateOrderStatus(String orderStatus, long orderId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, orderStatus);
            preparedStatement.setLong(2, orderId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public boolean create(Order order) throws DaoException {
        return false; //Todo
    }

    @Override
    public List<Order> selectAll(int offset, int limit) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Order entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
