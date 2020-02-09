package by.epam.pavelshakhlovich.onlinepharmacy.command.impl.prescription;

import by.epam.pavelshakhlovich.onlinepharmacy.command.Command;
import by.epam.pavelshakhlovich.onlinepharmacy.command.CommandException;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Path;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.PrescriptionStatus;
import by.epam.pavelshakhlovich.onlinepharmacy.service.PrescriptionService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import by.epam.pavelshakhlovich.onlinepharmacy.service.impl.PrescriptionServiceImpl;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class {@code RequestPrescription} is an implementation of {@see Command}
 * for requesting prescription to the doctor
 */
public class RequestPrescription implements Command {

    private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl();

    @Override
    public Path execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Prescription prescription;
        if (!request.getParameter(Parameter.PRESCRIPTION_ID).isEmpty()) {
            long prescriptionId = Long.parseLong(request.getParameter(Parameter.PRESCRIPTION_ID));
            try {
                prescriptionService.updatePrescriptionStatus(PrescriptionStatus.REQUESTED.getTitle(), prescriptionId, 0, null);
            } catch (ServiceException e) {
                throw LOGGER.throwing(Level.ERROR, new CommandException(e));
            }
        } else {
            prescription = new Prescription();
            prescription.setDrugId(Long.parseLong(request.getParameter(Parameter.DRUG_ID)));
            prescription.setUserId(Long.parseLong(request.getParameter(Parameter.USER_ID)));
            HttpSession session = request.getSession();
            try {
                if (prescriptionService.createPrescription(prescription)) {
                    session.setAttribute(Parameter.SUCCESS_REQUEST_MESSAGE, Boolean.TRUE);
                } else {
                    session.setAttribute(Parameter.ERROR_REQUEST_MESSAGE, Boolean.TRUE);
                }
            } catch (ServiceException e) {
                throw LOGGER.throwing(Level.ERROR, new CommandException("Failed to add new prescription to database", e));
            }
        }
        return new Path(false, request.getHeader(Parameter.REFERER));
    }
}


