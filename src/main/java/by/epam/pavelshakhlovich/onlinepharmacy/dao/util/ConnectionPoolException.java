package by.epam.pavelshakhlovich.onlinepharmacy.dao.util;

public class ConnectionPoolException extends Exception {
    private static final long serialVersionUID = -5874393899859271788L;

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
