package by.epam.pavelshakhlovich.onlinepharmacy.controller;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandFactory;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/controller",
        initParams = {
                @WebInitParam(name = "user", value = "Pavel"),
                @WebInitParam(name = "login", value = "pavel"),
                @WebInitParam(name = "password", value = "shadow")
        })
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute(Parameter.USER, getInitParameter("user"));
        request.setAttribute(Parameter.LOGIN, getInitParameter("login"));
        request.setAttribute(Parameter.PASSWORD, getInitParameter("password"));
        String page = null;
        try {
            Command command = CommandFactory.getInstance().getCommand(request);
            LOGGER.debug("executing " + command);
            page = command.execute(request, response);
        } catch (CommandException e) {
            LOGGER.error("Command execution failed", e);
            response.sendError(500);
        }
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = (JspPage.LOGIN.getPath());
            request.getSession().setAttribute("nullPage", "Page not found. Business logic error.");
            response.sendRedirect(page);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        LOGGER.info(getClass().getSimpleName() + " has been destroyed.");
    }
}
