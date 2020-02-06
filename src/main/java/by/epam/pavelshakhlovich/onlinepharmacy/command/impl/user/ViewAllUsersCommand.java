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
import java.util.List;

/**
 * Class {@code ViewUsersCommand} is an implementation of {@see Command}
 * for viewing all users or just filtered by login, email or id
 */
public class ViewAllUsersCommand implements Command {

    private static UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int limit = Integer.parseInt(request.getParameter(Parameter.LIMIT));
        int offset = (Integer.parseInt(request.getParameter(Parameter.PAGE_NUMBER)) - 1) * limit;
        try {
            int count = userService.countAllUsers();
            if (userService.countAllUsers() > 0) {
                List<User> userList = userService.selectAllUsers(offset, limit);
                request.setAttribute(Parameter.USERS, userList);
                request.setAttribute(Parameter.NUMBER_OF_USERS, count);
                request.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                request.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
            return new Path(true, JspPage.VIEW_ALL_USERS.getPath());
    }
}
