package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.ItemDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of the {@see ItemDao} interface.
 */
public class ItemDaoSQLImpl implements ItemDao {

    private static final String SELECT_DOSAGES = "SELECT id, name FROM dosages";
    private static final String SELECT_ITEM_BY_ID = "SELECT d.id, d.label, d.dosage_id, dos.name AS dosage, " +
            "d.volume, d.volume_type, d.manufacturer_id, m.name AS manufacturer_name, d.price, " +
            "d.by_prescription, d.description FROM drugs d " +
            "JOIN dosages dos ON d.dosage_id = dos.id " +
            "JOIN manufacturers m ON d.manufacturer_id = m.id" +
            " WHERE d.id = ?";
    private static final String SELECT_ITEM_BY_LABEL_DOSAGE_VOLUME = "SELECT d.id, d.label, d.dosage_id, " +
            "dos.name as dosage, d.volume, d.volume_type, d.manufacturer_id, m.name AS manufacturer_name, d.price, " +
            "d.by_prescription, d.description  FROM drugs d " +
            "JOIN dosages dos ON d.dosage_id = dos.id " +
            "JOIN manufacturers m ON d.manufacturer_id = m.id " +
            "WHERE d.label = ? AND d.dosage_id=? AND d.volume = ? AND d.volume_type=? AND d.manufacturer_id=?";
    private static final String INSERT_ITEM = "INSERT INTO drugs(label, dosage_id, volume, " +
            "volume_type, manufacturer_id, price, by_prescription, description) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ITEM = "UPDATE drugs " +
            "SET label = ?, dosage_id = ?,dosage = ?,volume = ?,volume_type = ?,manufacturer_id = ?,price = ? , " +
            "by_prescription = ?,description = ? " +
            "WHERE id = ?";
    private static final String DELETE_ITEM = " DELETE FROM drugs WHERE id = ?";
    private static final String SELECT_ALL_ITEMS = "SELECT d.id,d.label,d.dosage_id, dos.name AS dosage, " +
            "d.volume, d.volume_type, d.manufacturer_id, m.name AS manufacturer_name, d.price, d.by_prescription, " +
            "d.description From drugs d " +
            "LEFT JOIN dosages dos ON d.dosage_id = dos.id " +
            "LEFT JOIN manufacturers m ON d.manufacturer_id = m.id " +
            "ORDER BY d.label " +
            "LIMIT ?,?";
    private static final String SELECT_ITEMS_BY_LABEL = "SELECT d.id, d.label, d.dosage_id, ddf.name as dosage_form_name, " +
            "d.dosage, d.volume, d.volume_type, d.manufacturer_id, CONCAT(c.type,' \"',c.name,'\" (',c.country,')')" +
            " AS manufacturer_name,d.price,d.by_prescription,d.description,d.image_path \n" +
            "  From drugs d\n" +
            "  LEFT JOIN drugs_dosage_forms ddf ON d.dosage_form_id = ddf.id\n" +
            "  LEFT JOIN companies c ON d.manufacturer_id = c.id" +
            " WHERE d.label = ?" +
            " ORDER BY ddf.name " +
            " LIMIT ?,?";
    private static final String COUNT_ALL_ITEMS = "SELECT COUNT(*) AS number_of_items FROM drugs";
    private static final String COUNT_ITEMS_BY_LABEL = "SELECT COUNT(*) FROM drugs" +
            "  GROUP BY label" +
            "  HAVING label = ?";

    @Override
    public List<Dosage> getDosages() throws DaoException {
        List<Dosage> dosages = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_DOSAGES);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Dosage dosage = new Dosage();
                    dosage.setId(resultSet.getLong(1));
                    dosage.setName(resultSet.getString(2));
                    dosages.add(dosage);
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
        return dosages;
    }

    @Override
    public Item selectById(long id) throws DaoException {
        Item item = new Item();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ITEM_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            setItemParameters(item, resultSet);
            return item;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public Item selectItemByLabelDosageVolume(String label, long dosageId, double volume,
                                              String volumeType, long manufacturerId) throws DaoException {
        Item item = new Item();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ITEM_BY_LABEL_DOSAGE_VOLUME);
            preparedStatement.setString(1, label);
            preparedStatement.setLong(2, dosageId);
            preparedStatement.setDouble(3, volume);
            preparedStatement.setString(4, volumeType);
            preparedStatement.setLong(5, manufacturerId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            setItemParameters(item, resultSet);
            return item;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Item> selectAll(int offset, int limit) throws DaoException {
        List<Item> itemList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ALL_ITEMS);
            preparedStatement.setInt(1,offset);
            preparedStatement.setInt(2,limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Item item = new Item();
                setItemParameters(item, resultSet);
                itemList.add(item);
            }
            return itemList;

        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public int countAllItems() throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(COUNT_ALL_ITEMS);
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
    public List<Item> selectItemsByLabel(String label, int offset, int limit) throws DaoException {
        List<Item> itemList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ITEMS_BY_LABEL);
            preparedStatement.setString(1, label);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Item item = new Item();
                setItemParameters(item, resultSet);
                itemList.add(item);
            }
            return itemList;

        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public int countItemsByLabel(String label) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(COUNT_ITEMS_BY_LABEL);
            preparedStatement.setString(1, label);
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
    public boolean create(Item item) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(INSERT_ITEM);
            preparedStatement.setString(1, item.getLabel());
            preparedStatement.setLong(2, item.getDosageId());
            preparedStatement.setDouble(3, item.getVolume());
            preparedStatement.setString(4, item.getVolumeType());
            preparedStatement.setLong(5, item.getManufacturerId());
            preparedStatement.setBigDecimal(6, item.getPrice());
            preparedStatement.setBoolean(7, item.isByPrescription());
            preparedStatement.setString(8, item.getDescription());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public boolean update(Item item) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(UPDATE_ITEM);
            preparedStatement.setString(1, item.getLabel());
            preparedStatement.setLong(2, item.getDosageId());
            preparedStatement.setDouble(3, item.getVolume());
            preparedStatement.setString(4, item.getVolumeType());
            preparedStatement.setLong(5, item.getManufacturerId());
            preparedStatement.setBigDecimal(6, item.getPrice());
            preparedStatement.setBoolean(7, item.isByPrescription());
            preparedStatement.setString(8, item.getDescription());
            preparedStatement.setLong(9, item.getId());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(DELETE_ITEM);
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    private void setItemParameters(Item item, ResultSet resultSet) throws SQLException {
        item.setId(resultSet.getLong(Parameter.ID));
        item.setLabel(resultSet.getString(Parameter.LABEL));
        item.setDosageId(resultSet.getLong(Parameter.DOSAGE_ID));
        item.setDosage(resultSet.getString(Parameter.DOSAGE));
        item.setVolume(resultSet.getInt(Parameter.VOLUME));
        item.setVolumeType(resultSet.getString(Parameter.VOLUME_TYPE));
        item.setManufacturerId(resultSet.getLong(Parameter.MANUFACTURER_ID));
        item.setManufacturerName(resultSet.getString(Parameter.MANUFACTURER_NAME));
        item.setPrice(resultSet.getBigDecimal(Parameter.PRICE));
        item.setByPrescription(resultSet.getBoolean(Parameter.BY_PRESCRIPTION));
        item.setDescription(resultSet.getString(Parameter.DESCRIPTION));
    }

}
