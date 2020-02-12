package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.shoppingcart;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.JspPage;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.SessionUtil;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Item;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.PrescriptionStatus;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.model.ShoppingCart;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ItemService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.PrescriptionService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.ItemServiceImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code ViewShoppingCart} is a non-guest implementation of {@see Command}
 * for viewing shopping cart of logged in user
 */
public class ViewShoppingCart implements Command {

    private static final ItemService itemService = new ItemServiceImpl();
    private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameter.USER);
        ShoppingCart shoppingCart = SessionUtil.getCurrentShoppingCart(request);
        Map<Item, Integer> cartItems = new HashMap<>();
        Map<Long, Prescription> cartPrescriptions = new HashMap<>();
        session.setAttribute(Parameter.ORDER_AVAILABLE, Boolean.TRUE);
        try {
            for (Map.Entry<Long, Integer> item : shoppingCart.getItems().entrySet() ) {
                Item drug = itemService.selectItemById(item.getKey());
                cartItems.put(drug, item.getValue());
                if (drug.isByPrescription()) {
                    Prescription prescription = prescriptionService.selectPrescriptionByDrugId(drug.getId(), user);
                    if (prescription == null || !prescription.getStatus().equalsIgnoreCase("approved")) {
                        session.setAttribute(Parameter.ORDER_AVAILABLE, Boolean.FALSE);
                    } else if (prescription.getValidUntil().isBefore(LocalDateTime.now()) ) {
                        prescription.setStatus(PrescriptionStatus.OVERDUE.getTitle());
                        prescriptionService.updatePrescriptionStatus(
                                PrescriptionStatus.OVERDUE.getTitle(), prescription.getId(), prescription.getDoctorId(),
                                prescription.getValidUntil());
                        session.setAttribute(Parameter.ORDER_AVAILABLE, Boolean.FALSE);
                    }
                    cartPrescriptions.put(drug.getId(), prescription);
                }
            }
            session.setAttribute(Parameter.CART_ITEMS, cartItems);
            session.setAttribute(Parameter.PRESCRIPTIONS, cartPrescriptions);
        } catch ( ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(true, JspPage.SHOPPING_CART.getPath());
    }


}
