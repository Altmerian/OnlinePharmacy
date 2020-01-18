package by.epam.pavelshakhlovich.onlinepharmacy.command.impl;


import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class {@code UnknownCommand} will be used when {@see CommandFactory} can't return
 * properly command on demand.
 */
public class UnknownCommand implements Command {
    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) {
        return new Path(true, JspPage.MAIN.getPath());
    }
}
