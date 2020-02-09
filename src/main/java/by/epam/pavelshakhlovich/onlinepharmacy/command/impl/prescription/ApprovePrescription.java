package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.prescription;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.PrescriptionStatus;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.service.PrescriptionService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 *  Class {@code ApprovePrescription} is an implementation of {@see Command}
 *  to approve prescription by the doctor
 */
public class ApprovePrescription implements Command {
    private final static long PRESCRIPTION_TERM = 1; //days
    private static PrescriptionService prescriptionService = new PrescriptionServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User doctor = (User) request.getSession().getAttribute(Parameter.USER);
        long prescriptionId = Long.parseLong(request.getParameter(Parameter.ID));
        try {
            if(prescriptionService.updatePrescriptionStatus(PrescriptionStatus.APPROVED.getTitle(), prescriptionId,
                    doctor.getId(), LocalDateTime.now().plusDays(PRESCRIPTION_TERM))){
                request.getSession().setAttribute(Parameter.SUCCESS_MESSAGE, Boolean.TRUE);
            } else {
                request.getSession().setAttribute(Parameter.ERROR_MESSAGE, Boolean.TRUE);
            }
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
