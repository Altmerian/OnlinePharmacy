package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.user;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.UserService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code ViewUserCommand} is an admin and manager only implementation of {@see Command}
 * for searching users.
 */
public class ViewUserCommand implements Command {

    private static UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String email = request.getParameter(Parameter.EMAIL);
        String login = request.getParameter(Parameter.LOGIN);
        String stringUserId = request.getParameter(Parameter.USER_ID);
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Parameter.USER);
        User requestedUser = null;
        try {
            if (stringUserId != null) {
                long userId = Long.parseLong(stringUserId);
                requestedUser = userService.selectUserById(currentUser, userId);
            } else if (email != null) {
                requestedUser = userService.selectUserByEmail(currentUser, email);
            } else if (login != null) {
                requestedUser = userService.selectUserByLogin(currentUser, login);
            }
            if (requestedUser != null){
                request.setAttribute(Parameter.USER, requestedUser);
                return new Path(true, JspPage.VIEW_USER.getPath());
            } else {
                return new Path(false, request.getHeader(Parameter.REFERER));
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
    }
}
