package by.epam.pavelshakhlovich.onlinepharmacy.service;

import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.User;

import java.util.List;

/**
 * Represents an interface of a service providing prescription-related actions
 */
public interface PrescriptionService {

    /**
     * Returns a list of {@link Prescription} prescriptions of a specified user
     *
     * @param user   is user that request Prescriptions
     * @param userId id of the user, owning the requested prescriptions
     * @return list of user's prescriptions
     * @throws ServiceException if exception occurred on an underlying level
     */
    List<Prescription> selectPrescriptionsByUserId(User user, long userId) throws ServiceException;

    /**
     * Retrieves an prescription with given id
     *
     * @param prescriptionId id of the prescription
     * @param user           User that request this prescription
     * @return prescription or {@code null} if there is no such prescription
     * @throws ServiceException if failed to retrieve data from dao layer
     */
    Prescription selectPrescriptionById(long prescriptionId, User user) throws ServiceException;

    /**
     * Retrieves an prescription with given id
     *
     * @param drugId id of the drug which requires a prescription
     * @param user           User that request this prescription
     * @return prescription or {@code null} if there is no such prescription
     * @throws ServiceException if failed to retrieve data from dao layer
     */
    Prescription selectPrescriptionByDrugId(long drugId, User user) throws ServiceException;

    /**
     * Returns a list of {@link Prescription} prescriptions approved by a specified doctor
     *
     * @param user   is user that request a list
     * @param doctorId id of the doctor, approved the requested prescriptions
     * @return list of doctor's prescriptions
     * @throws ServiceException if exception occurred on an underlying level
     */
    List<Prescription> selectPrescriptionsByDoctorId(User user, long doctorId, int limit, int offset) throws ServiceException;

    /**
     * Selects a list of all Prescriptions with given status by all users
     *
     * @param statusList is a String list of statuses which prescriptions could be
     * @param limit      parameters for pagination
     * @param offset     parameters for pagination
     * @return a list of prescriptions
     * @throws ServiceException if exception occurred on an underlying level
     */
    List<Prescription> selectAllPrescriptionsByStatus(List<String> statusList, int limit, int offset) throws ServiceException;

    /**
     * Counts all prescriptions with given status by all users
     *
     * @param statusList is a String list of statuses which prescriptions could be
     * @return a quantity of prescriptions
     * @throws ServiceException if exception occurred on an underlying level
     */
    int countPrescriptionsByStatus(List<String> statusList) throws ServiceException;

    /**
     * Creates a Prescription with "requested" status and .
     * After submission the Prescription can't be modified, it can only be canceled.
     *
     * @param prescription represents prescription data
     * @return {@code true} if submitted successfully, {@code false} if prescription failed to be submitted
     * @throws ServiceException if exception occurred on an underlying level
     */
    boolean createPrescription(Prescription prescription) throws ServiceException;

    /**
     * Update status of given prescription
     *
     * @param prescriptionStatus is prescriptionStatus to update
     * @param prescriptionId     is id of prescription
     * @return {@code true} if updated successfully, {@code false} if update failed
     * @throws ServiceException if exception occurred on an underlying level
     */
    boolean updatePrescriptionStatus(String prescriptionStatus, long prescriptionId) throws ServiceException;

    /**
     * Cancel a specified prescription and delete it from data source
     *
     * @param prescriptionId id of the prescription to cancel
     * @return {@code true} if operation was successful
     * @throws ServiceException if exception occurred on an underlying level
     */
    boolean cancelPrescription(long prescriptionId) throws ServiceException;

}
