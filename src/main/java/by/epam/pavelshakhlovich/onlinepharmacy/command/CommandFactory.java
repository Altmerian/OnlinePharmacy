package by.epam.pavelshakhlovich.onlinepharmacy.command;


import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.*;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * This class based on factory design pattern and provides all possible commands on demand.
 */
public class CommandFactory {

    private static HashMap<CommandName, Command> commandMap = new HashMap<>();
    private static final CommandFactory instance = new CommandFactory();
    private static final Logger LOGGER = LogManager.getLogger();

    private CommandFactory() {

        commandMap.put(CommandName.ADD_ITEM, new AddItemCommand());
        commandMap.put(CommandName.ADD_ITEM_TO_SHOPPING_CART, new AddItemToShoppingCart());
        commandMap.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commandMap.put(CommandName.CLEAR_SHOPPING_CART, new ClearShoppingCart());
        commandMap.put(CommandName.LOGIN, new LoginCommand());
        commandMap.put(CommandName.LOGOUT, new LogoutCommand());
        commandMap.put(CommandName.REGISTER, new RegisterCommand());
        commandMap.put(CommandName.VIEW_ADD_ITEM, new ViewAddItemCommand());
        commandMap.put(CommandName.UNKNOWN, new UnknownCommand());

    }

    public static CommandFactory getInstance() {
        return instance;
    }

    /**
     * Returns a command for corresponding command parameter in a http-request
     *
     * @param request http request from the servlet
     * @return corresponding action command or an empty command in case command is not specified
     * @throws CommandException if command parameter is invalid
     */
    public Command getCommand(HttpServletRequest request) throws CommandException {
        Command command = commandMap.get(CommandName.UNKNOWN);
        String commandRequest = request.getParameter(Parameter.COMMAND);
        if (commandRequest == null || commandRequest.isEmpty()) {
            return command;
        } else {
            CommandName commandName;
            try {
                commandName = CommandName.valueOf(commandRequest.replace("-", "_").toUpperCase());
                if (commandMap.containsKey(commandName)) {
                    return commandMap.get(commandName);
                } else {
                    throw LOGGER.throwing(Level.ERROR,
                            new CommandException("No such command in CommandFactory commands map"));
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("wrongAction", "Command not found or wrong!");
                throw LOGGER.throwing(Level.ERROR,
                        new CommandException("command parameter is invalid (can't find it in CommandName enum)", e));
            }
        }
    }

}
