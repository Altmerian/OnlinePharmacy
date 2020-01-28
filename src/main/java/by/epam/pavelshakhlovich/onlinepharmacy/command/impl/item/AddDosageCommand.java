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
import javax.servlet.http.HttpSession;

/**
 * Class {@code AddDosageCommand} is an admin-only implementation of {@see Command}
 * for adding new dosage form into the data source
 */
public class AddDosageCommand implements Command {

    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String dosage = request.getParameter(Parameter.DOSAGE);
        HttpSession session = request.getSession();
        try {
            if (itemService.addDosage(dosage)) {
                session.setAttribute(Parameter.SUCCESS_DOSAGE_MESSAGE, Boolean.TRUE);
                session.setAttribute(Parameter.DOSAGE, dosage);
            } else {
                session.setAttribute(Parameter.ERROR_DOSAGE_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException("Failed to add new dosage to database", e));
        }
       return new Path(false, request.getHeader(Parameter.REFERER));
    }
}


