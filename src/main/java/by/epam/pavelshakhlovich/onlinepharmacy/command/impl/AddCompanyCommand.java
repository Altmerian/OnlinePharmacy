package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.CompanyServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code AddCompanyCommand} is an admin-only implementation of {@see Command}
 * for adding new company into the data source
 */
public class AddCompanyCommand implements Command {

    private static final CompanyService companyService = new CompanyServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Company company = new Company();
        company.setName(request.getParameter(Parameter.MANUFACTURER_NAME));
        company.setCountryId(Long.parseLong(request.getParameter(Parameter.COUNTRY_ID)));
        company.setWebsite(request.getParameter(Parameter.WEBSITE));
        HttpSession session = request.getSession();
        try {
            if (companyService.addCompany(company)) {
                session.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
                session.setAttribute(Parameter.COMPANY, company);
            } else {
                session.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException("Failed to add new company to database", e));
        }
       return new Path(false, request.getHeader(Parameter.REFERER));
    }
}


