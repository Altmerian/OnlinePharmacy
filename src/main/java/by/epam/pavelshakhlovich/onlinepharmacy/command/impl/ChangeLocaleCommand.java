package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = request.getParameter(Parameter.LOCALE);
        HttpSession session = request.getSession();
        session.setAttribute(Parameter.LOCALE,locale);
        response.sendRedirect(request.getHeader(Parameter.REFERER));
    }
}
