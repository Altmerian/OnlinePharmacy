package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code LoginCommand} is an authorized-only implementation of {@see Command}
 * for logging out any user
 */
public class LogoutCommand implements Command {

    /**
     * Handles request to the servlet by trying to logout a user
     *
     * @param request request from the servlet, containing user's login and password
     * @return path to index.jsp, and set remove user attribute from session
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        session.invalidate();
        return JspPage.MAIN.getPath();
    }
}
