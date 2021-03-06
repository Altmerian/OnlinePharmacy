package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;

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
     * @return URL, from which locale was changed
     */
    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) {
        String locale = request.getParameter(Parameter.LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(Parameter.LOCALE,locale);
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
