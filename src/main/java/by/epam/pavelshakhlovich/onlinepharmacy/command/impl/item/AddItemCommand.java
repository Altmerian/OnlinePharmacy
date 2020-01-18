package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.item;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Class {@code AddItemCommand} is an admin-only implementation of {@see Command}
 * for adding new item to catalog
 */
public class AddItemCommand implements Command {

    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Item item = new Item();
        item.setLabel(request.getParameter(Parameter.LABEL));
        item.setDosageId(Long.parseLong(request.getParameter(Parameter.DOSAGE_ID)));
        item.setVolume(Integer.parseInt(request.getParameter(Parameter.VOLUME)));
        item.setVolumeType(request.getParameter(Parameter.VOLUME_TYPE));
        item.setManufacturerId(Long.parseLong(request.getParameter(Parameter.MANUFACTURER_ID)));
        item.setPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameter(Parameter.PRICE))));
        item.setByPrescription(Boolean.parseBoolean(request.getParameter(Parameter.BY_PRESCRIPTION)));
        item.setDescription(request.getParameter(Parameter.DESCRIPTION));

        HttpSession session = request.getSession();
        try {
            if (itemService.addItem(item)) {
                session.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
                session.setAttribute(Parameter.ITEM, itemService.selectItemByLabelDosageVolume(item.getLabel(),
                        item.getDosageId(), item.getVolume(), item.getVolumeType(), item.getManufacturerId()));
            } else {
                session.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException("Failed to add new item to database", e));
        }
       return new Path(false, request.getHeader(Parameter.REFERER));
    }
}


