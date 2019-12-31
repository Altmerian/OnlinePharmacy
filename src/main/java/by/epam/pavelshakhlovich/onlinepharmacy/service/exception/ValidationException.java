package by.epam.pavelshakhlovich.onlinepharmacy.service.exception;

import java.io.Serializable;

public class ValidationException extends IllegalArgumentException implements Serializable {
    private static final long serialVersionUID = -6843925636139273536L;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

