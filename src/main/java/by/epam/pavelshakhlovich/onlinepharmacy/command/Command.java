package by.epam.pavelshakhlovich.onlinepharmacy.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code Command} is an interface representing a command, handled by a servlet
 */
public interface Command {
    Logger LOGGER = LogManager.getLogger();
    /**
     * Performs necessary actions, retrieves information from the service layers and sets
     * it to the request if necessary, also redirect or forward request
     * @param request request from the servlet
     * @param response response from the servlet
     * @throws CommandException
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
