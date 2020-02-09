package by.epam.pavelshakhlovich.onlinepharmacy.dao.impl;

import by.epam.pavelshakhlovich.onlinepharmacy.command.util.Parameter;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.DaoException;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.PrescriptionDao;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPool;
import by.epam.pavelshakhlovich.onlinepharmacy.dao.util.ConnectionPoolException;
import by.epam.pavelshakhlovich.onlinepharmacy.entity.Prescription;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This implementation of {@see PrescriptionDao} interface based on JDBC and MySQL.
 */

public class PrescriptionDaoSQLImpl implements PrescriptionDao {
    private static final String INSERT_PRESCRIPTION = "INSERT INTO prescriptions (drug_id, customer_id) VALUES (?, ?)";
    private static final String SELECT_USER_PRESCRIPTIONS = "SELECT id, valid_until, drug_id, customer_id, doctor_id, status " +
            "FROM prescriptions " +
            "WHERE customer_id = ? " +
            "ORDER BY id DESC ";
    private static final String SELECT_DOCTORS_PRESCRIPTIONS = "SELECT id, valid_until, drug_id, customer_id, doctor_id, status " +
            "FROM prescriptions " +
            "WHERE doctor_id = ? " +
            "ORDER BY id DESC " +
            "LIMIT ?, ?";
    private static final String SELECT_PRESCRIPTION_BY_ID = "SELECT id, valid_until, drug_id, customer_id, doctor_id, status " +
            "FROM prescriptions " +
            "WHERE id = ?";
    private static final String SELECT_PRESCRIPTION_BY_USER_DRUG = "SELECT id, valid_until, drug_id, customer_id, doctor_id, status " +
            "FROM prescriptions " +
            "WHERE drug_id = ? AND customer_id = ?";
    private static final String SELECT_ALL_REQUESTED_PRESCRIPTIONS = "SELECT id, valid_until, drug_id, customer_id, " +
            "doctor_id, status FROM prescriptions p " +
            "WHERE p.status = 'requested' " +
            "ORDER BY p.valid_until " +
            "LIMIT ?, ?";
    private static final String COUNT_PRESCRIPTIONS = "SELECT COUNT(p.id) AS count " +
            "FROM prescriptions p " +
            "WHERE p.status IN(?, ?, ?, ?)";
    private static final String UPDATE_PRESCRIPTION_STATUS = "UPDATE prescriptions SET status = ?, valid_until = ? doctor_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM prescriptions WHERE id = ? ";

    @Override
    public boolean create(Prescription prescription) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(INSERT_PRESCRIPTION);
            preparedStatement.setLong(1, prescription.getDrugId());
            preparedStatement.setLong(2, prescription.getUserId());
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public List<Prescription> selectPrescriptionsByUserId(long userId) throws DaoException {
        List<Prescription> prescriptionList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_USER_PRESCRIPTIONS);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                setPrescriptionParameters(resultSet, prescription);
                prescriptionList.add(prescription);
            }
            return prescriptionList;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }
    @Override
    public Prescription selectPrescriptionsByDrugIdUserId(long drugId, long userId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_PRESCRIPTION_BY_USER_DRUG);
            preparedStatement.setLong(1, drugId);
            preparedStatement.setLong(2, userId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            Prescription prescription = new Prescription();
            setPrescriptionParameters(resultSet, prescription);
            return prescription;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public Prescription selectById(long prescriptionId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_PRESCRIPTION_BY_ID);
            preparedStatement.setLong(1, prescriptionId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            Prescription prescription = new Prescription();
            setPrescriptionParameters(resultSet, prescription);
            return prescription;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Prescription> selectPrescriptionsByDoctorId(long doctorId, int limit, int offset) throws DaoException {
        List<Prescription> prescriptionList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_DOCTORS_PRESCRIPTIONS);
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                setPrescriptionParameters(resultSet, prescription);
                prescriptionList.add(prescription);
            }
            return prescriptionList;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Prescription> selectAllPrescriptionsByStatus(List<String> statusList, int limit, int offset) throws DaoException {
        List<Prescription> prescriptionList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(SELECT_ALL_REQUESTED_PRESCRIPTIONS);
            for (int i = 0; i < statusList.size(); i++) {
                preparedStatement.setString(i + 1, statusList.get(i));
            }
            preparedStatement.setInt(5, offset);
            preparedStatement.setInt(6, limit);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            while (resultSet.next()) {
                Prescription prescription = new Prescription();
                setPrescriptionParameters(resultSet, prescription);
                prescriptionList.add(prescription);
            }
            return prescriptionList;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public int countPrescriptionsByStatus(List<String> statusList) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(COUNT_PRESCRIPTIONS);
            for (int i = 0; i < statusList.size(); i++) {
                preparedStatement.setString(i + 1, statusList.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return 0;
            }
            resultSet.next();
            return resultSet.getInt(1);
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement, resultSet);
        }
    }

    @Override
    public boolean updatePrescriptionStatus(String prescriptionStatus, long prescriptionId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            cn.setAutoCommit(false);
            preparedStatement = cn.prepareStatement(UPDATE_PRESCRIPTION_STATUS);
            preparedStatement.setString(1, prescriptionStatus);
            preparedStatement.setLong(2, prescriptionId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    @Override
    public boolean delete(long prescriptionId) throws DaoException {
        Connection cn = null;
        PreparedStatement preparedStatement = null;
        try {
            cn = ConnectionPool.getInstance().getConnection();
            preparedStatement = cn.prepareStatement(DELETE);
            preparedStatement.setLong(1, prescriptionId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (ConnectionPoolException | SQLException e) {
            throw LOGGER.throwing(Level.ERROR, new DaoException(e));
        } finally {
            closeResources(cn, preparedStatement);
        }
    }

    private void setPrescriptionParameters(ResultSet resultSet, Prescription prescription) throws SQLException {
        prescription.setId(resultSet.getLong(Parameter.ID));
        prescription.setValidUntil(resultSet.getTimestamp(Parameter.VALID_UNTIL).toLocalDateTime());
        prescription.setDrugId(resultSet.getLong(Parameter.DRUG_ID));
        prescription.setUserId(resultSet.getLong(Parameter.CUSTOMER_ID));
        prescription.setDoctorId(resultSet.getLong(Parameter.DOCTOR_ID));
        prescription.setStatus(resultSet.getString(Parameter.STATUS));
    }

    @Override
    public List<Prescription> selectAll(int offset, int limit) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Prescription entity) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
