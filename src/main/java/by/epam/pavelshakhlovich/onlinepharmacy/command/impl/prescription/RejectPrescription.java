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
 * Class {@code RejectPrescription} is an implementation of {@see Command}
 * to reject prescription by the doctor
 */
public class RejectPrescription implements Command {
    private static PrescriptionService prescriptionService = new PrescriptionServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User doctor = (User) request.getSession().getAttribute(Parameter.USER);
        long prescriptionId = Long.parseLong(request.getParameter(Parameter.ID));
        try {
            prescriptionService.updatePrescriptionStatus(PrescriptionStatus.REJECTED.getTitle(), prescriptionId,
                    doctor.getId(), LocalDateTime.now());
        } catch (ServiceException e) {
            throw LOGGER.throwing(Level.ERROR, new CommandException(e));
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}
