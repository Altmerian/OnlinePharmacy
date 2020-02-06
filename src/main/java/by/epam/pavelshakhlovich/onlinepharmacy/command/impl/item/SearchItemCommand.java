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
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class {@code SearchItemCommand} is an all-users implementation of {@see Command}
 * for searching items.
 */
public class SearchItemCommand implements Command {
    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int limit = Integer.parseInt(request.getParameter(Parameter.LIMIT));
        String pageNumber = request.getParameter(Parameter.PAGE_NUMBER);
        int offset = 0;
        if (!pageNumber.isEmpty()) {
            offset = (Integer.parseInt(pageNumber) - 1) * limit;
        } else {
            request.setAttribute(Parameter.PAGE_NUMBER, 1);
        }
        String label = request.getParameter(Parameter.SEARCH);
        try {
            if (label == null || label.isEmpty()) {
                return new Path(false, request.getHeader(Parameter.REFERER));
            } else {
                int searchResult = itemService.countItemsByLabel(label);
                if (searchResult == 0) {
                    request.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
                } else {
                    List<Item> itemList = itemService.selectItemsByLabel(label, offset, limit);
                    request.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
                    request.setAttribute(Parameter.ITEMS, itemList);
                    request.setAttribute(Parameter.NUMBER_OF_ITEMS, searchResult);
                }
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.SEARCH_ITEM.getPath());
    }
}

