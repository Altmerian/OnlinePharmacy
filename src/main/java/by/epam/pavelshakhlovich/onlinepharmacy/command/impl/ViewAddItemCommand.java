package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.CompanyServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class {@code ViewAddItemCommand} is an admin and manager only implementation of {@see Command}
 * for viewing page, where admin can add new items to the catalog, it also requests requisites from database
 */
public class ViewAddItemCommand implements Command {
    private static final ItemService itemService = new ItemServiceImpl();
    private static final CompanyService companyService = new CompanyServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            List<Dosage> dosages = itemService.getDosages();
            List<String> volumeTypes = itemService.getVolumeTypes();
            List<Company> companies = companyService.getCompanyList();
            request.setAttribute(Parameter.DOSAGES, dosages);
            request.setAttribute(Parameter.VOLUME_TYPES, volumeTypes);
            request.setAttribute(Parameter.COMPANIES, companies);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return JspPage.ADD_ITEM.getPath();
    }
}
