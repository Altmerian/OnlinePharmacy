package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.item;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Company;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Dosage;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.VolumeType;
import by.epam.pavelshakhlovich.onlinepharmacy.service.CompanyService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.CompanyServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class {@code ViewEditItemCommand} is an admin and manager only implementation of {@see Command}
 * for viewing page, where admin can edit item from the catalog, it also requests requisites from data source
 */
public class ViewEditItemCommand implements Command {

    private static final ItemService itemService = new ItemServiceImpl();
    private static final CompanyService companyService = new CompanyServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long drugId = Long.parseLong(request.getParameter(Parameter.ID));
        try {
            Item item = itemService.selectItemById(drugId);
            List<Dosage> dosageForms = itemService.getDosages();
            List<String> volumeTypes = Arrays.stream(VolumeType.values())
                    .map(VolumeType::getTitle)
                    .collect(Collectors.toList());
            List<Company> companies = companyService.getCompanyList();
            request.setAttribute(Parameter.DOSAGES, dosageForms);
            request.setAttribute(Parameter.VOLUME_TYPES, volumeTypes);
            request.setAttribute(Parameter.COMPANIES, companies);
            request.setAttribute(Parameter.ITEM, item);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.EDIT_ITEM.getPath());
    }
}
