package by.epam.pavelshakhlovich.onlinepharmacy.dao.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String CONNECTION_URI = "jdbc:mysql://localhost:3306/onlinepharmacy";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "shadow";
    private static final String CLASS_DRIVER = "com.mysql.jdbc.Driver";
    private static ConnectionPool instance = new ConnectionPool();
    private static AtomicBoolean isEmpty = new AtomicBoolean(true);
    private static ReentrantLock lock = new ReentrantLock();
    private static int poolSize = 5;
    private static BlockingQueue<Connection> connections;
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
        lock.lock();
        connections = new ArrayBlockingQueue<>(poolSize);
        try {
            Class.forName(CLASS_DRIVER);
            int currentConnectionSize = connections.size();
            for (int i = 0; i < poolSize - currentConnectionSize; i++) {
                connections.add(DriverManager.getConnection(CONNECTION_URI, USERNAME, PASSWORD));
            }
            isEmpty.set(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Initialize error", e));
        }
        lock.unlock();
    }

    public int getPoolSize() {
        return poolSize;
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

    public boolean releaseConnection(Connection con) throws ConnectionPoolException {
        boolean isReleased = connections.add(con);
        if (!isReleased) {
            LOGGER.throwing(Level.ERROR, new ConnectionPoolException("Can't release connection"));
            return false;
        } else {
            return true;
        }
    }

    public boolean closePool() throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.throwing(Level.ERROR,new ConnectionPoolException(e));
            }
        }
        isEmpty.set(true);
        return true;
    }
}


