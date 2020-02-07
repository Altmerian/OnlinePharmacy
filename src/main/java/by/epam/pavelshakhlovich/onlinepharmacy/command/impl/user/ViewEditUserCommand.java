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

/**
 * Class {@code ViewEditUserCommand} is an implementation of {@see Command}
 * for viewing page, where user profile can be edited
 */
public class ViewEditUserCommand implements Command {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long userId = Long.parseLong(request.getParameter(Parameter.USER_ID));
        User user = (User) request.getSession().getAttribute(Parameter.USER);
        try {
            User editedUser = userService.selectUserById(user, userId);
            if (editedUser != null) {

                request.setAttribute(Parameter.USER, editedUser);
                return new Path(true, JspPage.EDIT_USER.getPath());
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
