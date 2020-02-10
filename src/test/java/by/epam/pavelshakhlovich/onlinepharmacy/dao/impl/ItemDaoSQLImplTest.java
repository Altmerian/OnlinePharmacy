package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ProxyConnection;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


@PrepareForTest(ConnectionPool.class)
public class ItemDaoSQLImplTest {

    @InjectMocks
    private ItemDaoSQLImpl itemDao;

    @Mock
    private ConnectionPool mockConnectionPool;

    @Mock
    private ProxyConnection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private Item item1;
    private Item item2;

    @BeforeMethod
    public void setUp() throws ConnectionPoolException, SQLException {
        itemDao = new ItemDaoSQLImpl();
        PowerMockito.mockStatic(ConnectionPool.class);
        MockitoAnnotations.initMocks(this);
        ConnectionPool.isEmpty.set(false);
        Mockito.when(ConnectionPool.getInstance().getConnection()).thenReturn(mockConnection);
        Mockito.when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        itemDao = new ItemDaoSQLImpl();
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
    }

    @AfterMethod
    public void tearDown() throws ConnectionPoolException, SQLException {
        itemDao = null;
        item1 = null;
        item2 = null;
        mockResultSet.close();
        mockPreparedStatement.close();
        mockConnection.close();
        mockConnectionPool.closePool();
    }

    @Test( enabled=false )
    public void testSelectById() throws DaoException, SQLException {
        when(mockResultSet.isBeforeFirst()).thenReturn(true);
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