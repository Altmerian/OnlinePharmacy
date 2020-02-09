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
 * Class {@code ViewAllPrescriptions} is an implementation of {@see Command}
 * for viewing all prescriptions of the given doctor
 */
public class ViewAllPrescriptions implements Command {

    private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl();
    private static final ItemService itemService = new ItemServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = (User)request.getSession().getAttribute(Parameter.USER);
        long doctorId = user.getId();
        int limit = Integer.parseInt(request.getParameter(Parameter.LIMIT));
        int offset = (Integer.parseInt(request.getParameter(Parameter.PAGE_NUMBER)) - 1) * limit;
        try {
            Map<Long, User> userMap = new HashMap<>();
            Map<Long, Item> itemMap = new HashMap<>();
            List<Prescription> prescriptionList = prescriptionService.selectPrescriptionsByDoctorId(user, doctorId, limit, offset);
            for (Prescription prescription : prescriptionList) {
                long customerId = prescription.getUserId();
                User customer = userService.selectUserById(user, customerId);
                userMap.put(customerId, customer);
                long itemId = prescription.getDrugId();
                Item item = itemService.selectItemById(itemId);
                itemMap.put(itemId, item);
            }
            int numberOfPrescriptions = prescriptionService.countDoctorPrescriptions(doctorId);
            request.setAttribute(Parameter.PRESCRIPTIONS, prescriptionList);
            request.setAttribute(Parameter.NUMBER_OF_PRESCRIPTIONS, numberOfPrescriptions);
            request.setAttribute(Parameter.USERS, userMap);
            request.setAttribute(Parameter.ITEMS, itemMap);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.VIEW_ALL_PRESCRIPTIONS.getPath());
    }
}


