package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.user;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;

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
     */
    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Path(true, JspPage.MAIN.getPath());
    }
}
