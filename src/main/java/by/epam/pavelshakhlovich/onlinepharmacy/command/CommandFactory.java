package by.epam.pavelshakhlovich.onlinepharmacy.command;


import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.company.AddCompanyCommand;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.ChangeLocaleCommand;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.UnknownCommand;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.company.AddCountryCommand;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.company.ViewAddCompanyCommand;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.item.*;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.order.*;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart.AddItemToCart;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart.ClearShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart.RemoveItemFromCart;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart.ViewShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.command.impl.user.*;
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
        commandMap.put(CommandName.ADD_ITEM_TO_SHOPPING_CART, new AddItemToCart());
        commandMap.put(CommandName.ADD_COMPANY, new AddCompanyCommand());
        commandMap.put(CommandName.ADD_COUNTRY, new AddCountryCommand());
        commandMap.put(CommandName.ADD_DOSAGE, new AddDosageCommand());
        commandMap.put(CommandName.CANCEL_ORDER, new CancelOrderCommand());
        commandMap.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commandMap.put(CommandName.CLEAR_SHOPPING_CART, new ClearShoppingCart());
        commandMap.put(CommandName.CONFIRM_DELIVERY, new ConfirmDeliveryCommand());
        commandMap.put(CommandName.CONFIRM_PAYMENT, new ConfirmPaymentCommand());
        commandMap.put(CommandName.DELETE_ITEM, new DeleteItemCommand());
        commandMap.put(CommandName.EDIT_ITEM, new EditItemCommand());
        commandMap.put(CommandName.EDIT_USER, new EditUserCommand());
        commandMap.put(CommandName.LOGIN, new LoginCommand());
        commandMap.put(CommandName.LOGOUT, new LogoutCommand());
        commandMap.put(CommandName.PAY_ORDER, new PayOrderCommand());
        commandMap.put(CommandName.REGISTER, new RegisterCommand());
        commandMap.put(CommandName.REMOVE_ITEM_FROM_SHOPPING_CART, new RemoveItemFromCart());
        commandMap.put(CommandName.SEARCH_ITEM, new SearchItemCommand());
        commandMap.put(CommandName.SUBMIT_ORDER, new CreateOrderCommand());
        commandMap.put(CommandName.VIEW_ADD_ITEM, new ViewAddItemCommand());
        commandMap.put(CommandName.VIEW_ADD_COMPANY, new ViewAddCompanyCommand());
        commandMap.put(CommandName.VIEW_EDIT_ITEM, new ViewEditItemCommand());
        commandMap.put(CommandName.VIEW_EDIT_USER, new ViewEditUserCommand());
        commandMap.put(CommandName.VIEW_ITEM, new ViewItemCommand());
        commandMap.put(CommandName.VIEW_CATALOG, new ViewCatalogCommand());
        commandMap.put(CommandName.VIEW_ORDER, new ViewOrderCommand());
        commandMap.put(CommandName.VIEW_ORDERS, new ViewOrdersCommand());
        commandMap.put(CommandName.VIEW_ALL_ORDERS, new ViewAllOrdersCommand());
        commandMap.put(CommandName.VIEW_SHOPPING_CART, new ViewShoppingCart());
        commandMap.put(CommandName.VIEW_ALL_USERS, new ViewAllUsersCommand());
        commandMap.put(CommandName.VIEW_USER, new ViewUserCommand());
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
