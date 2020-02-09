package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.prescription;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.PrescriptionService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.UserService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.PrescriptionServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class {@code ViewRequestedPrescriptions} is an implementation of {@see Command}
 * for viewing requested prescriptions by the doctor
 */
public class ViewRequestedPrescriptions implements Command {

    private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl();
    private static final ItemService itemService = new ItemServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int limit = Integer.parseInt(request.getParameter(Parameter.LIMIT));
        int offset = (Integer.parseInt(request.getParameter(Parameter.PAGE_NUMBER)) - 1) * limit;
        try {
            Map<Long, User> userMap = new HashMap<>();
            Map<Long, Item> itemMap = new HashMap<>();
            List<Prescription> prescriptionList = prescriptionService.selectAllRequestedPrescriptions(limit, offset);
            for (Prescription prescription : prescriptionList) {
                long userId = prescription.getUserId();
                long itemId = prescription.getDrugId();
                User user = userService.selectUserById((User) request.getSession().getAttribute(Parameter.USER), userId);
                userMap.put(userId, user);
                Item item = itemService.selectItemById(itemId);
                itemMap.put(itemId, item);
            }
            int numberOfPrescriptions = prescriptionService.countRequestedPrescriptions();
            request.setAttribute(Parameter.PRESCRIPTIONS, prescriptionList);
            request.setAttribute(Parameter.NUMBER_OF_PRESCRIPTIONS, numberOfPrescriptions);
            request.setAttribute(Parameter.USERS, userMap);
            request.setAttribute(Parameter.ITEMS, itemMap);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.REQUESTED_PRESCRIPTIONS.getPath());
    }
}


