package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an interface for retrieving Prescription-related data.
 */
public interface PrescriptionDao extends BaseDao<Prescription> {

    /**
     * Retrieves a list of all user's prescriptions
     *
     * @param userId  id of the user
     * @return list of user's prescriptions or {@code null} if user has none
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<Prescription> selectPrescriptionsByUserId(long userId) throws DaoException;

    /**
     * Selects prescription by user and drug ID
     *
     * @param userId  id of the user
     * @param drugId  id of the drug
     * @return user's prescription for given drug or {@code null} if user has none
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    Prescription selectPrescriptionsByDrugIdUserId(long drugId, long userId) throws DaoException;

    /**
     * Retrieves a list of all prescriptions approved by given doctor
     *
     * @param doctorId  id of the doctor
     * @param limit parameters for pagination
     * @param offset parameters for pagination
     * @return list of doctor's prescriptions or {@code null} if doctor has not written any
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<Prescription> selectPrescriptionsByDoctorId(long doctorId, int limit, int offset) throws DaoException;

    /**
     * Counts all prescriptions written by given doctor
     *
     * @param doctorId  id of the doctor
     * @return a quantity of prescriptions written by given doctor
     * @throws DaoException  if failed to retrieve data from the storage due to technical problems
     */
    int countDoctorPrescriptions(long doctorId) throws DaoException;

    /**
     * Selects a list of all prescriptions with "requested" status by all users
     *
     * @param limit parameters for pagination
     * @param offset parameters for pagination
     * @return a list of requested Prescriptions
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<Prescription> selectAllRequestedPrescriptions( int limit, int offset) throws DaoException;

    /**
     * Counts all Prescriptions with "requested" status by all users
     *
     * @return a quantity of prescriptions with given statuses
     * @throws DaoException  if failed to retrieve data from the storage due to technical problems
     */
    int countRequestedPrescriptions() throws DaoException;

    /**
     * Update status of given Prescription
     *
     * @param prescriptionStatus is PrescriptionStatus to update
     * @param prescriptionId     is id of prescription
     * @param doctorId           is id of the doctor
     * @param validUntil         is a term of using
     * @return {@code true} if updated successfully, {@code false} if update failed
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean updatePrescriptionStatus(String prescriptionStatus, long prescriptionId, long doctorId, LocalDateTime validUntil) throws DaoException;

}

