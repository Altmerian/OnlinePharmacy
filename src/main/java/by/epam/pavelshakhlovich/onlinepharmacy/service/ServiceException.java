package by.epam.pavelshakhlovich.onlinepharmacy.service;

/**
 * Represents an exception that may be thrown on the service layer
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = -8255686885649266537L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
