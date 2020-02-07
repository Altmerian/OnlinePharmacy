package by.epam.pavelshakhlovich.onlinepharmacy.service.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.PrescriptionDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.impl.PrescriptionDaoSQLImpl;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.UserRole;
import by.epam.pavelshakhlovich.onlinepharmacy.service.PrescriptionService;
import by.epam.pavelshakhlovich.onlinepharmacy.service.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * A implementation of the {@link PrescriptionService} interface
 */
public class PrescriptionServiceImpl implements PrescriptionService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static PrescriptionDao prescriptionDao = new PrescriptionDaoSQLImpl();

    @Override
    public List<Prescription> selectPrescriptionsByUserId(User user, long userId) throws ServiceException {
        if (user.getId() == userId || user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER) {
            try {
                return prescriptionDao.selectPrescriptionsByUserId(userId);
            } catch (DaoException e) {
                throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
            }
        } else {
            return null;
        }
    }

    @Override
    public Prescription selectPrescriptionById(long prescriptionId, User user) throws ServiceException {
        try {
            Prescription prescription = prescriptionDao.selectById(prescriptionId);
            if (prescription == null) {
                return null;
            } else if (user.getId() != prescription.getUserId()
                    || user.getRole() == UserRole.ADMIN || user.getRole() != UserRole.MANAGER) {
                return prescription;
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
        return null;
    }

    @Override
    public List<Prescription> selectAllPrescriptionsByStatus(List<String> statusList, int limit, int offset) throws ServiceException {
        try {
            return prescriptionDao.selectAllPrescriptionsByStatus(statusList, limit, offset);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public int countPrescriptionsByStatus(List<String> statusList) throws ServiceException {
        try {
            return prescriptionDao.countPrescriptionsByStatus(statusList);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean createPrescription(Prescription prescription) throws ServiceException {
        try {
            return prescriptionDao.create(prescription);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean updatePrescriptionStatus(String prescriptionStatus, long prescriptionId) throws ServiceException {
        try {
            Prescription prescription = prescriptionDao.selectById(prescriptionId);
            if (prescription.getStatus().equalsIgnoreCase(prescriptionStatus)) {
                return false;
            }
            return prescriptionDao.updatePrescriptionStatus(prescriptionStatus, prescriptionId);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public boolean cancelPrescription(long prescriptionId) throws ServiceException {
        try {
            return prescriptionDao.delete(prescriptionId);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

}
