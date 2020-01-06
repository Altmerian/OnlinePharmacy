package by.epam.pavelshakhlovich.onlinepharmacy.dao;

/**
 * This exception is thrown in case of problems with accessing data
 */
public class DaoException extends Exception {
    private static final long serialVersionUID = -6790636180304062785L;

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
