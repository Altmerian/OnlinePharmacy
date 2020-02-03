package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.OrderDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Order;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.*;

/**
 * This implementation of {@see OrderDao} interface based on JDBC and MySQL.
 */

public class OrderDaoSQLImpl implements OrderDao {
    private static final String INSERT_ORDER = "INSERT INTO orders (customer_id, amount) VALUES (?, ?)";
    private static final String INSERT_DRUGS = "INSERT INTO drugs_ordered (order_id, drug_id, quantity, price) " +
            "VALUES (?, ?, ?, ?)";
    private static final String INSERT_EVENT = "INSERT INTO orders_events (order_id, order_status) " +
            "SELECT id, status FROM orders WHERE ID = ?";
    private static final String SELECT_USER_ORDERS = "SELECT id, date, amount, status" +
            " FROM orders" +
            " WHERE customer_id = ?" +
            " ORDER BY id DESC ";
    private static final String SELECT_LAST_ADDED = "SELECT MAX(id) AS id, date, amount, status" +
            " FROM orders" +
            " WHERE customer_id = ?";

    private static final String SELECT_ORDER_BY_ID = "SELECT o.id, o.customer_id, o.date, o.amount, o.status, " +
            "d.id AS drug_id, d.label, dos.name AS dosage, d.volume, d.volume_type, d.by_prescription, dro.quantity, dro.price FROM orders o " +
            "LEFT JOIN drugs_ordered dro ON o.id = dro.order_id " +
            "LEFT JOIN drugs d ON dro.drug_id = d.id " +
            "LEFT JOIN dosages dos ON d.dosage_id = dos.id " +
            "WHERE o.id = ? " +
            "ORDER BY d.label";
    private static final String SELECT_ALL_ORDERS_BY_STATUS = "SELECT o.id, o.customer_id, u.first_name, u.last_name, u.login, u.address, " +
            "o.date, o.amount, o.status FROM orders o " +
            "JOIN  users u ON o.customer_id = u.id " +
            "WHERE o.status IN(?, ?, ?, ?) " +
            "ORDER BY o.date DESC " +
            "LIMIT ?, ?";
    private static final String COUNT_ORDERS = "SELECT COUNT(o.id) AS count " +
            "FROM orders o " +
            "WHERE o.status IN(?, ?, ?, ?)";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM orders WHERE id = ? ";
    private static final String SELECT_ORDER_EVENTS = "SELECT  date_time, order_status FROM orders_events " +
            "WHERE order_id = ? " +
            "ORDER BY date_time DESC";

    @Override
    public boolean create(Order order) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            cn.setAutoCommit(false);

            preparedStatement = cn.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setBigDecimal(2, order.getAmount());
            int operation1 = preparedStatement.executeUpdate();
            long orderId;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = (generatedKeys.getLong(1));
                } else {
                    throw LOGGER.throwing(Level.ERROR, new DaoException("Transaction wasn't finished"));
                }
            }
            preparedStatement = cn.prepareStatement(INSERT_EVENT);
            preparedStatement.setLong(1, orderId);
            int operation2 = preparedStatement.executeUpdate();

            preparedStatement = cn.prepareStatement(INSERT_DRUGS);
            Map<Item, Integer> drugs = order.getItems();
            for (Map.Entry<Item, Integer> drug : drugs.entrySet()) {
                preparedStatement.setLong(1, orderId);
                preparedStatement.setLong(2, drug.getKey().getId());
                preparedStatement.setInt(3, drug.getValue());
                preparedStatement.setBigDecimal(4, drug.getKey().getPrice());
                preparedStatement.addBatch();
            }
            int operations3 = preparedStatement.executeBatch().length;
            boolean shouldCommit = operation1 == 1 && operation2 == 1 && operations3 == drugs.size();
            if (shouldCommit) {
                cn.commit();
            } else {
                rollback(cn);
                LOGGER.error("Transaction wasn't finished because of unexpected results.");
            }
            return shouldCommit;
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if (cn != null) {
                    rollback(cn);
                }
                throw LOGGER.throwing(Level.ERROR, new DaoException("Transaction wasn't finished", e));
            } finally {
                closeResources(cn, preparedStatement);
            }
        }
    }

    @Override
    public Order getLastAddedOrder(long userId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_LAST_ADDED);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            Order order = new Order();
            order.setUserId(userId);
            resultSet.next();
            setOrderParameters(resultSet, order);
            return order;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

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
                setOrderParameters(resultSet, order);
                orderList.add(order);
            }
            return orderList;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
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
            setOrderParameters(resultSet, order);
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
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Order> selectAllOrdersByStatus(List<String> statusList,  int limit, int offset) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ALL_ORDERS_BY_STATUS);
            for (int i = 0; i < statusList.size(); i++) {
                preparedStatement.setString(i + 1, statusList.get(i));
            }
            preparedStatement.setInt(5, offset);
            preparedStatement.setInt(6, limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Order order = new Order();
                User user = new User();
                user.setId(resultSet.getLong(Parameter.CUSTOMER_ID));
                user.setFirstName(resultSet.getString(Parameter.FIRST_NAME));
                user.setLastName(resultSet.getString(Parameter.LAST_NAME));
                user.setLogin(resultSet.getString(Parameter.LOGIN));
                user.setAddress(resultSet.getString(Parameter.ADDRESS));
                order.setUser(user);
                order.setUserId(resultSet.getLong(Parameter.CUSTOMER_ID));
                setOrderParameters(resultSet, order);
                orderList.add(order);
            }
            return orderList;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
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
            preparedStatement = cn.prepareStatement(COUNT_ORDERS);
            for (int i = 0; i < statusList.size(); i++) {
                preparedStatement.setString(i + 1, statusList.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return 0;
            }
            resultSet.next();
            return resultSet.getInt(1);
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
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
            cn.setAutoCommit(false);
            preparedStatement = cn.prepareStatement(UPDATE_ORDER_STATUS);
            preparedStatement.setString(1, orderStatus);
            preparedStatement.setLong(2, orderId);
            int operation1 = preparedStatement.executeUpdate();

            preparedStatement = cn.prepareStatement(INSERT_EVENT);
            preparedStatement.setLong(1, orderId);
            int operation2 = preparedStatement.executeUpdate();

            boolean shouldCommit = operation1 + operation2 == 2;
            if (shouldCommit) {
                cn.commit();
            } else {
                rollback(cn);
                LOGGER.error("Transaction wasn't finished because of unexpected results.");
            }
            return shouldCommit;
        } catch (ConnectionPoolException | SQLException e) {
            try {
                if (cn != null) {
                    rollback(cn);
                }
                throw LOGGER.throwing(Level.ERROR, new DaoException("Transaction wasn't finished", e));
            } finally {
                closeResources(cn, preparedStatement);
            }
        }
    }

    @Override
    public boolean delete(long orderId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(DELETE);
            preparedStatement.setLong(1, orderId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public Map<Timestamp, String> getOrderEvents(long orderId) throws DaoException {
        Map<Timestamp, String> orderEventList = new TreeMap<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ORDER_EVENTS);
            preparedStatement.setLong(1, orderId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Timestamp date = resultSet.getTimestamp(1);
                String status = resultSet.getString(2);
                orderEventList.put(date, status);
            }
            return orderEventList;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    private void setOrderParameters(ResultSet resultSet, Order order) throws SQLException {
        order.setId(resultSet.getLong(Parameter.ID));
        order.setDate(resultSet.getTimestamp(Parameter.DATE));
        order.setAmount(resultSet.getBigDecimal(Parameter.AMOUNT));
        order.setStatus(resultSet.getString(Parameter.STATUS));
    }

    private void rollback(Connection connection) throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException("Failed to roll back transaction", e));
        }
    }

    @Override
    public List<Order> selectAll(int offset, int limit) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Order entity) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
