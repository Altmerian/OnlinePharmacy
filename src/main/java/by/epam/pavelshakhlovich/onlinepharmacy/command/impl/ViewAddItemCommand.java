package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Class {@code ViewAddItemCommand} is an admin and manager only implementation of {@see Command}
 * for viewing page, where admin can add new items to the catalog, it also requests requisites from database
 */
public class ViewAddItemCommand implements Command {
    private static final ItemService itemService = new ItemServiceImpl();
    private static CompanyService companyService = CompanyServiceFactory.getInstance().getService(CompanyServiceName.COMPANY_SERVICE);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            List<Dosage> dosageForms = itemService.getDosages();
            List<String> volumeTypes = itemService.getVolumeTypes();
            List<Company> companies = companyService.getCompanyList();
            request.setAttribute(Parameter.DOSAGE_FORMS.getName(), dosageForms);
            request.setAttribute(Parameter.VOLUME_TYPES.getName(),volumeTypes);
            request.setAttribute(Parameter.COMPANIES.getName(),companies);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        try {
            request.getRequestDispatcher(JspPage.ADD_ITEM.getPath()).forward(request,response);
        } catch (ServletException | IOException e) {
            throw new CommandException(e);
        }
    }
}
