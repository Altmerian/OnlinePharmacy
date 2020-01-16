package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Class {@code AddItemCommand} is an implementation of {@see Command}
 * for adding a chosen item to shopping cart.
 */
public class AddItemToShoppingCart implements Command {


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, IOException {
        if (request.getSession().getAttribute(Parameter.USER) != null) {
            ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
            Random r = new Random();
            shoppingCart.addItem(r.nextInt(2), r.nextInt(1) + 1);
            response.sendRedirect(request.getHeader(Parameter.REFERER));
        } else {
            response.sendRedirect(request.getHeader(JspPage.LOGIN.getPath()));
        }
    }
}