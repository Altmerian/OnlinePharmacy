package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code ViewItemCommand} is an all-users implementation of {@see Command}
 * for viewing an item's info
 */
public class ViewItemCommand implements Command {

    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long itemId = Long.parseLong(request.getParameter(Parameter.ID));
        try {
            Item item = itemService.selectItemById(itemId);
            request.setAttribute(Parameter.ITEM, item);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return JspPage.VIEW_ITEM.getPath();
    }
}

