package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.item;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class {@code ViewCatalogComman} is an registered users-only implementation of {@see Command}
 * for viewing catalog page, where user can buy items
 */
public class ViewCatalogCommand implements Command {

    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int limit = Integer.parseInt(request.getParameter(Parameter.LIMIT));
        int offset = (Integer.parseInt(request.getParameter(Parameter.PAGE_NUMBER)) - 1) * limit;
        try {
            List<Item> itemList = itemService.selectAllItems(offset, limit);
            request.setAttribute(Parameter.ITEMS, itemList);
            request.setAttribute(Parameter.NUMBER_OF_ITEMS, itemService.countAllItems());
        } catch ( ServiceException e) {
            throw new CommandException(e);
        }
       return new Path(true, JspPage.VIEW_CATALOG.getPath());
    }
}
