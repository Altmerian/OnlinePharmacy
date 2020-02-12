package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.prescription;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.PrescriptionStatus;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class {@code ViewPrescriptions} is an implementation of {@see Command}
 * for viewing prescriptions owning by given user
 */
public class ViewPrescriptions implements Command {

    private static PrescriptionService prescriptionService = new PrescriptionServiceImpl();
    private static UserService userService = new UserServiceImpl();
    private static final ItemService itemService = new ItemServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        User user = (User) request.getSession().getAttribute(Parameter.USER);
        String userIdString = request.getParameter(Parameter.USER_ID);
        long userId;
        if (userIdString == null || userIdString.isEmpty()) {
            userId = user.getId();
        } else {
            userId = Long.parseLong(request.getParameter(Parameter.USER_ID));
        }
        List<Prescription> prescriptionList;
        try {
            prescriptionList = prescriptionService.selectPrescriptionsByUserId(user, userId);
            Map<Long, Item> itemMap = new HashMap<>();
            if (prescriptionList != null && !prescriptionList.isEmpty()) {
                for (Prescription prescription : prescriptionList) {
                    if (prescription.getValidUntil().isBefore(LocalDateTime.now())
                            && prescription.getStatus().equalsIgnoreCase("approved")) {
                        prescription.setStatus("overdue");
                        prescriptionService.updatePrescriptionStatus(
                                PrescriptionStatus.OVERDUE.getTitle(), prescription.getId(), prescription.getDoctorId(),
                                prescription.getValidUntil());
                    }
                    long itemId = prescription.getDrugId();
                    Item item = itemService.selectItemById(itemId);
                    itemMap.put(itemId, item);
                }
                request.setAttribute(Parameter.ITEMS, itemMap);
            }
            User prescriptionOwner = userService.selectUserById(user, userId);
            request.setAttribute(Parameter.PRESCRIPTION_OWNER, prescriptionOwner);
            request.setAttribute(Parameter.PRESCRIPTIONS, prescriptionList);
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.VIEW_PRESCRIPTIONS.getPath());
    }
}
