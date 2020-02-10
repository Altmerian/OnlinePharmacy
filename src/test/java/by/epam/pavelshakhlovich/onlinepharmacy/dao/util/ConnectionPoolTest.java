package by.epam.pavelshakhlovich.onlinepharmacy.dao.util;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class ConnectionPoolTest {

    @BeforeClass
    public void setUp() throws ConnectionPoolException {
        ConnectionPool.initialize();
    }

    @AfterClass
    public void tearDown() throws ConnectionPoolException {
        ConnectionPool.getInstance().closePool();
    }

    @Test
    public void testGetInstance() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }

    @Test
    public void testGetConnection() throws ConnectionPoolException {
        ProxyConnection connection = (ProxyConnection) ConnectionPool.getInstance().getConnection();
        assertNotNull(connection);
    }

    @Test
    public void testGetPoolSize() throws ConnectionPoolException {
        int actualPoolSize = ConnectionPool.getInstance().getPoolSize();
        int expectedPoolSize = ConnectionPool.POOL_SIZE;
        assertEquals(actualPoolSize, expectedPoolSize);
    }
}