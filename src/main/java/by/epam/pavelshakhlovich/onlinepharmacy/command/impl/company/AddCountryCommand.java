package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.company;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.CompanyServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code AddCountryCommand} is an admin-only implementation of {@see Command}
 * for adding new country into the data source
 */
public class AddCountryCommand implements Command {

    private static final CompanyService companyService = new CompanyServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String country = request.getParameter(Parameter.COUNTRY);
        HttpSession session = request.getSession();
        try {
            if (companyService.addCountry(country)) {
                session.setAttribute(Parameter.SUCCESS_COUNTRY_MESSAGE, Boolean.TRUE);
                session.setAttribute(Parameter.COUNTRY, country);
            } else {
                session.setAttribute(Parameter.ERROR_COUNTRY_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException("Failed to add new dosage to database", e));
        }
       return new Path(false, request.getHeader(Parameter.REFERER));
    }
}


