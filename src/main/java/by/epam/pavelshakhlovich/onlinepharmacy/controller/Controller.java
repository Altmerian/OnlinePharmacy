package by.epam.pavelshakhlovich.onlinepharmacy.controller;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandFactory;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.info(getClass().getSimpleName() + " has been initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Command command = CommandFactory.getInstance().getCommand(request);
            LOGGER.debug("executing " + command);
            Path path = command.execute(request, response);
            if (path.getUrl() != null) {
                if (path.isForward()) {
                    request.getRequestDispatcher(path.getUrl()).forward(request, response);
                } else {
                    response.sendRedirect(path.getUrl());
                }
            } else {
                LOGGER.error("Page not found.");
                response.sendError(404, "Page not found.");
            }
        } catch (CommandException e) {
            LOGGER.throwing(Level.ERROR, new CommandException("Command execution failed", e));
            response.sendError(500, "Command execution failed");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().closePool();
            LOGGER.info("Connection pool has been closed.");
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool hasn't been closed");
        }
        LOGGER.info(getClass().getSimpleName() + " has been destroyed.");
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.throwing(Level.ERROR, e);
            }
        });
    }
}
