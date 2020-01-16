package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.VolumeType;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.CompanyServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class {@code ViewAddItemCommand} is an admin and manager only implementation of {@see Command}
 * for viewing page, where admin can add new items to the catalog, it also requests requisites from database
 */
public class ViewAddItemCommand implements Command {
    private static final ItemService itemService = new ItemServiceImpl();
    private static final CompanyService companyService = new CompanyServiceImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        try {
            List<Dosage> dosages = itemService.getDosages();
            List<String> volumeTypes = Arrays.stream(VolumeType.values())
                    .map(VolumeType::getTitle)
                    .collect(Collectors.toList());
            List<Company> companies = companyService.getCompanyList();
            request.getSession().setAttribute(Parameter.DOSAGES, dosages);
            request.getSession().setAttribute(Parameter.VOLUME_TYPES, volumeTypes);
            request.getSession().setAttribute(Parameter.COMPANIES, companies);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        response.sendRedirect(JspPage.ADD_ITEM.getPath());
    }
}
