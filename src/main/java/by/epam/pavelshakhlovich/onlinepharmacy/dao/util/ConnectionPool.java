package by.epam.pavelshakhlovich.onlinepharmacy.dao.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private static AtomicBoolean isEmpty = new AtomicBoolean(true);
    private static ReentrantLock lock = new ReentrantLock();
    private static final int POOL_SIZE = 10;
    private static BlockingQueue<ProxyConnection> connections;
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static ResourceBundle resource = ResourceBundle.getBundle("database");
    private static final Logger LOGGER = LogManager.getLogger();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (isEmpty.compareAndSet(true, false)) {
            ConnectionPool.initialize();
        }
        return INSTANCE;
    }

    private static void initialize() throws ConnectionPoolException {
        String url = resource.getString("url");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        String driver = resource.getString("driver");
        try {
            lock.lock();
            connections = new ArrayBlockingQueue<>(POOL_SIZE);
            try {
                Class.forName(driver);
                int currentConnectionSize = connections.size();
                for (int i = 0; i < POOL_SIZE - currentConnectionSize; i++) {
                    connections.add(new ProxyConnection(DriverManager.getConnection(url, user, pass)));
                }
            } catch (ClassNotFoundException | SQLException e) {
                isEmpty.set(true);
                throw LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Initialization error", e));
            }
        }
        finally {
            lock.unlock();
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = connections.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Can't get connection", e));
        }
        return connection;
    }

    public void releaseConnection(Connection con) throws ConnectionPoolException {
        boolean isReleased =false;
        if (con instanceof ProxyConnection) {
            try {
                isReleased = connections.offer((ProxyConnection)con, 5 , TimeUnit.SECONDS);
            } catch (InterruptedException e) {
               throw LOGGER.throwing(Level.ERROR, new ConnectionPoolException(e));
            }
        }
        if (!isReleased) {
            LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Can't release connection"));
        }
    }

    public void closePool() throws ConnectionPoolException {
        for (ProxyConnection connection : connections) {
            try {
                connection.reallyClose();
            } catch (SQLException e) {
                LOGGER.throwing(Level.ERROR, new ConnectionPoolException(e));
            }
        }
        isEmpty.set(true);
    }

    public int getPoolSize() {
        return POOL_SIZE;
    }

    private static Properties readProperties() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(DATABASE_PROPERTIES);
            properties.load(input);
        } catch (IOException e) {
            LOGGER.throwing(Level.ERROR, e);
        }
        return properties;
    }
}


