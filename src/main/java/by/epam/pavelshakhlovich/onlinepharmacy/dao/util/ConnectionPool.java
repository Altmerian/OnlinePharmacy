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
    private static ConnectionPool instance = new ConnectionPool();
    private static AtomicBoolean isEmpty = new AtomicBoolean(true);
    private static ReentrantLock lock = new ReentrantLock();
    private static int poolSize = 5;
    private static BlockingQueue<Connection> connections;
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final Logger LOGGER = LogManager.getLogger();

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        if (isEmpty.get()) {
            ConnectionPool.initialize();
        }
        return instance;
    }

    private static void initialize() throws ConnectionPoolException {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("url");
        String user = resource.getString("user");
        String pass = resource.getString("password");
        String driver = resource.getString("driver");

        lock.lock();
        connections = new ArrayBlockingQueue<>(poolSize);
        try {
            Class.forName(driver);
            int currentConnectionSize = connections.size();
            for (int i = 0; i < poolSize - currentConnectionSize; i++) {
                connections.add(DriverManager.getConnection(url, user, pass));
            }
            isEmpty.set(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Initialization error", e));
        }
        lock.unlock();
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection con;
        try {
            con = connections.poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw LOGGER.throwing(Level.ERROR, new ConnectionPoolException(e.getMessage()));
        }
        return con;
    }

    public void releaseConnection(Connection con) throws ConnectionPoolException {
        boolean isReleased = connections.add(con);
        if (!isReleased) {
            LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Can't release connection"));
        }
    }

    public void closePool() throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.throwing(Level.ERROR, new ConnectionPoolException(e));
            }
        }
        isEmpty.set(true);
    }

    public int getPoolSize() {
        return poolSize;
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


