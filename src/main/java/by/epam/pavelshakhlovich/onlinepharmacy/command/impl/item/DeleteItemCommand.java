package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.item;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code DeleteItemCommand} is an admin and manager only implementation of {@see Command}
 * for deleting given item from catalog
 */
public class DeleteItemCommand implements Command {

    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long id = Long.parseLong(request.getParameter(Parameter.ID));
        try {
            if (itemService.deleteItem(id)) {
                request.getSession().setAttribute(Parameter.SUCCESS_MESSAGE_DELETE, Boolean.TRUE);
            } else {
                request.getSession().setAttribute(Parameter.ERROR_MESSAGE_DELETE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
            return new Path(true, request.getHeader(Parameter.REFERER));
    }
}
