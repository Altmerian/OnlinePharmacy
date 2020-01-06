package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code UnknownCommand} will be used when {@see CommandFactory} can't return
 * properly command on demand.
 */
public class UnknownCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return JspPage.MAIN.getPath();
    }
}
