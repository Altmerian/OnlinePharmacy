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

import java.time.LocalDateTime;
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
    public Prescription selectPrescriptionByDrugId(long drugId, User user) throws ServiceException {
        try {
            Prescription prescription = prescriptionDao.selectPrescriptionsByDrugIdUserId(drugId, user.getId());
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
    public List<Prescription> selectPrescriptionsByDoctorId(User user, long doctorId, int limit, int offset) throws ServiceException {
        try {
            List<Prescription> prescriptionList = prescriptionDao.selectPrescriptionsByDoctorId(doctorId, limit, offset);
            if (prescriptionList == null) {
                return null;
            } else if (user.getId() == doctorId
                    || user.getRole() == UserRole.ADMIN || user.getRole() != UserRole.MANAGER) {
                return prescriptionList;
            }
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
        return null;
    }

    @Override
    public int countDoctorPrescriptions(long doctorId) throws ServiceException {
        try {
            return prescriptionDao.countDoctorPrescriptions(doctorId);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }


    @Override
    public List<Prescription> selectAllRequestedPrescriptions(int limit, int offset) throws ServiceException {
        try {
            return prescriptionDao.selectAllRequestedPrescriptions(limit, offset);
        } catch (DaoException e) {
            throw LOGGER.throwing(Level.ERROR, new ServiceException(e));
        }
    }

    @Override
    public int countRequestedPrescriptions() throws ServiceException {
        try {
            return prescriptionDao.countRequestedPrescriptions();
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
    public boolean updatePrescriptionStatus(String prescriptionStatus, long prescriptionId, long doctorId, LocalDateTime validUntil) throws ServiceException {
        try {
            Prescription prescription = prescriptionDao.selectById(prescriptionId);
            if (prescription.getStatus().equalsIgnoreCase(prescriptionStatus)) {
                return false;
            }
            return prescriptionDao.updatePrescriptionStatus(prescriptionStatus, prescriptionId, doctorId, validUntil);
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
