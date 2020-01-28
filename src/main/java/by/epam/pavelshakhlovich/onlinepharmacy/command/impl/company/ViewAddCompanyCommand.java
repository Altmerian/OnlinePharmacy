package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.company;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Country;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.CompanyServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Class {@code ViewAddCompanyCommand} is an admin and manager only implementation of {@see Command}
 * for viewing page, where admin can add new companies to the catalog, it also requests requisites from database
 */
public class ViewAddCompanyCommand implements Command {
    private static final CompanyService companyService = new CompanyServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {
            List<Country> countries = companyService.getCountries();
            request.getSession().setAttribute(Parameter.COUNTRIES, countries);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.ADD_COMPANY.getPath());
    }
}
