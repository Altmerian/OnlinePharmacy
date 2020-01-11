package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.UserService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code LoginCommand} is a guest-only implementation of {@see Command}
 * for signing in a user with given credentials. It also put a Shopping cart to session scope
 * if login was successful.
 */
public class LoginCommand implements Command {
    private static UserService userService = new UserServiceImpl();

    /**
     * Handles request to the servlet by trying to log in a user with given credentials
     *
     * @param request request from the servlet, containing user's login and password
     * @return path to the same page, and set login parameters to the current session
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page;
        User user;
        String login = request.getParameter(Parameter.LOGIN);
        String password = request.getParameter(Parameter.PASSWORD);

        try {
            user = userService.loginUser(login, password);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException("Can't get user from UserService layer", e));
        }

        HttpSession session = request.getSession();
        if (user != null) {
            session.setAttribute(Parameter.USER, user);
            session.setAttribute(Parameter.LOGIN_FAILED, Boolean.FALSE);
            page = JspPage.MAIN.getPath();
        } else {
            session.setAttribute(Parameter.LOGIN_FAILED, Boolean.TRUE);
            request.setAttribute("errorLoginPassMessage","Incorrect login or password.");
            page = JspPage.LOGIN.getPath();
        }
        return page;
    }
}
