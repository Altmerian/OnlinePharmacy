package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtils;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.shoppingCart.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * Class {@code AddItemCommand} is an implementation of {@see Command}
 * for adding a chosen item to shopping cart.
 */
public class AddItemToShoppingCart implements Command {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(request);
        Random r = new Random();
        shoppingCart.addItem(r.nextInt(2), r.nextInt(1) + 1);
        return JspPage.MAIN.getPath();
    }
}