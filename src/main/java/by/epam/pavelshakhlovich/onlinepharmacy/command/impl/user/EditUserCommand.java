package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.user;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
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
 * Class {@code EditUserCommand} is an admin and manager only implementation of {@see Command}
 * for editing given user profile
 */
public class EditUserCommand implements Command {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long userId = Long.parseLong(request.getParameter(Parameter.USER_ID));
        User user = (User) request.getSession().getAttribute(Parameter.USER);
        try {
            User editedUser = userService.selectUserById(user, userId);
            editedUser.setPassword(request.getParameter(Parameter.PASSWORD));
            editedUser.setEmail(request.getParameter(Parameter.EMAIL));
            editedUser.setFirstName(request.getParameter(Parameter.FIRST_NAME));
            editedUser.setLastName(request.getParameter(Parameter.LAST_NAME));
            editedUser.setAddress(request.getParameter(Parameter.ADDRESS));
            if (userService.updateUser(editedUser)){
                request.getSession().setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                request.getSession().setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
