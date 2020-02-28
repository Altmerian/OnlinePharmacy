package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.ItemDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ProxyConnection;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ItemDaoSQLImplTest {
    @Mock
    private ProxyConnection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private static final Logger LOGGER = LogManager.getLogger();
    private ItemDao itemDao;
    private Item item1;
    private Item item2;
    ArrayList<Item> items = new ArrayList<>();
    private int itemsCount;

    @BeforeClass
    public void setUp() throws ConnectionPoolException, SQLException {
        itemDao = new ItemDaoSQLImpl();
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.isBeforeFirst()).thenReturn(true);
        item1 = new Item();
        item1.setId(1L);
        item1.setLabel("Лекарство1");
        item1.setDosage("таблетки 0.2 мг");
        item1.setDosageId(1L);
        item1.setVolume(10);
        item1.setVolumeType("шт.");
        item1.setManufacturerId(3L);
        item1.setManufacturerName("Белмедпрепараты");
        item1.setPrice(BigDecimal.valueOf(1.25));
        item1.setByPrescription(Boolean.FALSE);
        item2 = new Item();
        item2.setId(2L);
        item1.setLabel("Лекарство2");
        item1.setDosage("мазь 1%");
        item1.setDosageId(2L);
        item1.setVolume(40);
        item1.setVolumeType("г");
        item1.setManufacturerId(4L);
        items.addAll(List.of(item1, item2));
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        setMock(connectionPool);
        ConnectionPool.isEmpty.compareAndSet(true, false);
        when(connectionPool.getConnection()).thenReturn(mockConnection);
    }

    private void setMock(ConnectionPool mock) {
        try {
            Field instance = ConnectionPool.class.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw LOGGER.throwing(Level.ERROR, new RuntimeException(e));
        }
    }

    @AfterClass
    public void tearDown() throws Exception {
        Field instance = ConnectionPool.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(instance, new ConnectionPool());
        itemDao = null;
        item1 = null;
        item2 = null;
        items.clear();
        mockResultSet.close();
        mockPreparedStatement.close();
        mockConnection.close();
        ConnectionPool.isEmpty.compareAndSet(false, true);
    }

    @Test
    public void testCreate() throws DaoException {
        for (Item item: items) {
            assertTrue(itemDao.create(item));
            itemsCount++;
        }
    }

    @Test (dependsOnMethods = "testCreate")
    public void testCountAllItems() throws SQLException, DaoException {
        when(mockResultSet.getInt(1)).thenReturn(itemsCount);
        assertEquals(itemDao.countAllItems(), items.size());
    }

    @Test
    public void testSelectById() throws DaoException, SQLException {
        when(mockResultSet.getLong(Parameter.ID)).thenReturn(1L);
        when(mockResultSet.getString(Parameter.LABEL)).thenReturn(item1.getLabel());
        when(mockResultSet.getLong(Parameter.DOSAGE_ID)).thenReturn(item1.getDosageId());
        when(mockResultSet.getString(Parameter.DOSAGE)).thenReturn(item1.getDosage());
        when(mockResultSet.getInt(Parameter.VOLUME)).thenReturn(item1.getVolume());
        when(mockResultSet.getString(Parameter.VOLUME_TYPE)).thenReturn(item1.getVolumeType());
        when(mockResultSet.getLong(Parameter.MANUFACTURER_ID)).thenReturn(item1.getManufacturerId());
        when(mockResultSet.getString(Parameter.MANUFACTURER_NAME)).thenReturn(item1.getManufacturerName());
        when(mockResultSet.getBigDecimal(Parameter.PRICE)).thenReturn(item1.getPrice());
        when(mockResultSet.getBoolean(Parameter.BY_PRESCRIPTION)).thenReturn(item1.isByPrescription());
        when(mockResultSet.getString(Parameter.DESCRIPTION)).thenReturn(item1.getDescription());
        assertEquals(itemDao.selectById(1L), item1);
    }
}