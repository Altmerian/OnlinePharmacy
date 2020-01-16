package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClearShoppingCart implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        SessionUtil.clearCurrentShoppingCart(request, response);
        response.sendRedirect(request.getHeader(Parameter.REFERER));
    }
}
