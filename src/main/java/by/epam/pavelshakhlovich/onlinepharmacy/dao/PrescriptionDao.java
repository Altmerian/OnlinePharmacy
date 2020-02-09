package by.epam.pavelshakhlovich.onlinepharmacy.dao;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;

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
     * Selects a list of all prescriptions with given status by all users
     *
     * @param statusList is a String list of statuses which prescriptions could be
     * @param limit parameters for pagination
     * @param offset parameters for pagination
     * @return a list of Prescriptions
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    List<Prescription> selectAllPrescriptionsByStatus(List<String> statusList, int limit, int offset) throws DaoException;

    /**
     * Counts all Prescriptions with given status by all users
     *
     * @param statusList is a String list of statuses which prescriptions could be
     * @return a quantity of prescriptions with given statuses
     * @throws DaoException  if failed to retrieve data from the storage due to technical problems
     */
    int countPrescriptionsByStatus(List<String> statusList) throws DaoException;

    /**
     * Update status of given Prescription
     *
     * @param prescriptionStatus is PrescriptionStatus to update
     * @param prescriptionId     is id of prescription
     * @return {@code true} if updated successfully, {@code false} if update failed
     * @throws DaoException if failed to retrieve data from the storage due to technical problems
     */
    boolean updatePrescriptionStatus(String prescriptionStatus, long prescriptionId) throws DaoException;

}

