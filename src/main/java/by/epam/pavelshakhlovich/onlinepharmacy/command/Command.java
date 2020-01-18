package by.epam.pavelshakhlovich.onlinepharmacy.command;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException, ServletException;

    default void rememberLastRequest(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String url = req.getRequestURL() + "?" + req.getQueryString();
        session.setAttribute(Parameter.CURRENT_PAGE, url);
    }
}
