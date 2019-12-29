package by.epam.pavelshakhlovich.onlinepharmacy.command;

/**
 * Represents a custom exception that may occur in any class that
 * implements {@see Command} interface

 */
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
