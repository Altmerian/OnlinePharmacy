package by.epam.pavelshakhlovich.onlinepharmacy.dao.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test(groups = {"Connection required"})
public class ConnectionPoolTest {

    @BeforeClass
    public void setUp() throws ConnectionPoolException {
        ConnectionPool.initialize();
        ConnectionPool.isEmpty.compareAndSet(true, false);
    }

    @AfterClass
    public void tearDown() throws ConnectionPoolException {
        if (!ConnectionPool.isEmpty.get()) {
            ConnectionPool.getInstance().closePool();
        }
    }


    public void testGetInstance() throws ConnectionPoolException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }

    public void testGetConnection() throws ConnectionPoolException {
        ProxyConnection connection = (ProxyConnection) ConnectionPool.getInstance().getConnection();
        assertNotNull(connection);
    }

    public void testGetPoolSize() throws ConnectionPoolException {
        int actualPoolSize = ConnectionPool.getInstance().getPoolSize();
        int expectedPoolSize = ConnectionPool.POOL_SIZE;
        assertEquals(actualPoolSize, expectedPoolSize);
    }
}