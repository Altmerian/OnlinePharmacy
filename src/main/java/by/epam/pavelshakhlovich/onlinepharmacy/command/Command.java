package by.epam.pavelshakhlovich.onlinepharmacy.command;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code Command} is an interface representing a command, handled by a servlet
 */
public interface Command {
    Logger LOGGER = LogManager.getLogger();
    /**
     * Performs necessary actions, retrieves information from the service layers and sets
     * it to the request if necessary, also transfer information about redirect or forward request
     * and url inside Path object
     * @param request request from the servlet
     * @param response response from the servlet
     */
    Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException, ServletException;
}
