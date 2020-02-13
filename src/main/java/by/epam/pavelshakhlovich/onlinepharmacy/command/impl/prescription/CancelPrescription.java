package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.prescription;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.service.PrescriptionService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  Class {@code CancelPrescription} is an implementation of {@see Command}
 *  for cancel current prescription and delete it from data source
 */
public class CancelPrescription implements Command {

    private static PrescriptionService prescriptionService = new PrescriptionServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        long prescriptionId = Long.parseLong(request.getParameter(Parameter.PRESCRIPTION_ID));
        HttpSession session = request.getSession();
        try {
            if (prescriptionService.cancelPrescription(prescriptionId)) {
                session.setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                session.setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
