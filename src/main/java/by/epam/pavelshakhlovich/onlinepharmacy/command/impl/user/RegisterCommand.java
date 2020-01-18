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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class {@code RegisterCommand} is a guest-only implementation of {@see Command}
 * for registration of a new user
 */
public class RegisterCommand implements Command {
    private static UserService userService = new UserServiceImpl();
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        boolean result;
        User user = new User();
        user.setLogin(request.getParameter(Parameter.LOGIN));
        user.setPassword(request.getParameter(Parameter.PASSWORD));
        user.setEmail(request.getParameter(Parameter.EMAIL));
        user.setFirstName(request.getParameter(Parameter.FIRST_NAME));
        user.setLastName(request.getParameter(Parameter.LAST_NAME));
        user.setAddress(request.getParameter(Parameter.ADDRESS));
        try {
            result = userService.registerUser(user);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        HttpSession session = request.getSession();
        if (result) {
            try {
                User registeredUser = userService.loginUser(user.getLogin(), user.getPassword());
                User currentUser = (User) session.getAttribute(Parameter.USER);
                if (currentUser == null) {
                    session.setAttribute(Parameter.USER, registeredUser);
                    session.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
                    session.setAttribute(Parameter.USER_NAME, registeredUser.getLogin());
                }
            } catch (ServiceException e) {
                LOGGER.throwing(Level.ERROR, new CommandException(e));
            }
            return new Path(false, JspPage.MAIN.getPath());
        } else {
            session.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            return new Path(false, JspPage.REGISTER.getPath());
        }
    }
}
