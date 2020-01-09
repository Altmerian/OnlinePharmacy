package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code ChangeLocaleCommand} is an implementation of {@see Command}
 * for changing locale for the session
 */
public class ChangeLocaleCommand implements Command {
    /**
     * Handles request to the servlet by changing the locale for the session
     * @param request request from the servlet, containing the desired locale
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale = request.getParameter(Parameter.LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(Parameter.LOCALE,locale);
        return((String) request.getAttribute(Parameter.FROM));
    }
}
